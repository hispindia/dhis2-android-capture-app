package org.dhis2.data.forms.dataentry.fields.coordinate;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.dhis2.App;
import org.dhis2.Bindings.DoubleExtensionsKt;
import org.dhis2.Bindings.StringExtensionsKt;
import org.dhis2.R;
import org.dhis2.databinding.FormCoordinatesAccentBinding;
import org.dhis2.databinding.FormCoordinatesBinding;
import org.dhis2.uicomponents.map.geometry.LngLatValidatorKt;
import org.dhis2.uicomponents.map.views.MapSelectorActivity;
import org.dhis2.usescases.general.ActivityGlobalAbstract;
import org.dhis2.utils.ActivityResultObservable;
import org.dhis2.utils.ActivityResultObserver;
import org.dhis2.utils.ColorUtils;
import org.dhis2.utils.Constants;
import org.dhis2.utils.customviews.CustomDialog;
import org.dhis2.utils.customviews.FieldLayout;
import org.hisp.dhis.android.core.arch.helpers.GeometryHelper;
import org.hisp.dhis.android.core.common.FeatureType;
import org.hisp.dhis.android.core.common.Geometry;
import org.hisp.dhis.android.core.maintenance.D2Error;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.List;

import kotlin.Unit;

import static android.app.Activity.RESULT_OK;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.text.TextUtils.isEmpty;
import static org.dhis2.Bindings.ViewExtensionsKt.closeKeyboard;
import static org.dhis2.usescases.eventsWithoutRegistration.eventInitial.EventInitialPresenter.ACCESS_LOCATION_PERMISSION_REQUEST;
import static org.dhis2.utils.Constants.RQ_MAP_LOCATION_VIEW;

public class CoordinatesView extends FieldLayout implements View.OnClickListener, View.OnFocusChangeListener, ActivityResultObserver {

    private ViewDataBinding binding;
    private TextInputEditText latitude;
    private TextInputEditText longitude;
    private TextInputLayout latitudeInputLayout;
    private TextInputLayout longitudeInputLayout;
    private ImageButton location1;
    private OnCurrentLocationClick currentLocationListener;
    private TextView errorView;
    private View clearButton;
    private View polygonInputLayout;
    private TextInputEditText polygon;
    private FeatureType featureType;
    private Geometry currentGeometry;
    private TextView labelText;
    private CoordinateViewModel viewModel;

    public CoordinatesView(Context context) {
        super(context);
        if (!isInEditMode())
            init(context);
        else
            initEditor();
    }

    public CoordinatesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            init(context);
        else
            initEditor();
    }

    public CoordinatesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            init(context);
        else
            initEditor();
    }

    public void initEditor() {
        LayoutInflater.from(getContext()).inflate(R.layout.form_coordinates, this, true);
    }

    public void init(Context context) {
        super.init(context);
    }

    private void setLayout() {
        if (isBgTransparent)
            binding = DataBindingUtil.inflate(inflater, R.layout.form_coordinates, this, true);
        else
            binding = DataBindingUtil.inflate(inflater, R.layout.form_coordinates_accent, this, true);

        polygonInputLayout = findViewById(R.id.polygonInputLayuout);
        polygon = findViewById(R.id.polygonEditText);
        latitudeInputLayout = findViewById(R.id.latitudeInputLayout);
        longitudeInputLayout = findViewById(R.id.longInputLayout);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        location1 = findViewById(R.id.location1);

        errorView = findViewById(R.id.errorMessage);
        clearButton = findViewById(R.id.clearButton);
        labelText = findViewById(R.id.label);

        latitude.setOnEditorActionListener((v, actionId, event) -> {
            if (validateCoordinates()) {
                Double latitudeValue = isEmpty(latitude.getText().toString()) ? null : DoubleExtensionsKt.truncate(getLatitude());
                Double longitudeValue = isEmpty(longitude.getText().toString()) ? null : DoubleExtensionsKt.truncate(getLongitude());
                if (latitudeValue != null || longitudeValue != null) {
                    if (LngLatValidatorKt.isLatitudeValid(latitudeValue)) {
                        currentLocationListener.onCurrentLocationClick(GeometryHelper.createPointGeometry(longitudeValue, latitudeValue));
                    } else {
                        setError(getContext().getString(R.string.coordinates_error));
                    }
                }
            } else {
                longitude.requestFocus();
                longitude.performClick();
            }
            return true;
        });

        longitude.setOnEditorActionListener((v, actionId, event) -> {
            if (validateCoordinates()) {
                Double latitudeValue = isEmpty(latitude.getText().toString()) ? null : DoubleExtensionsKt.truncate(getLatitude());
                Double longitudeValue = isEmpty(longitude.getText().toString()) ? null : DoubleExtensionsKt.truncate(getLongitude());
                if (latitudeValue != null || longitudeValue != null) {
                    if (LngLatValidatorKt.areLngLatCorrect(longitudeValue, latitudeValue)) {
                        currentLocationListener.onCurrentLocationClick(GeometryHelper.createPointGeometry(longitudeValue, latitudeValue));
                    } else {
                        setError(getContext().getString(R.string.coordinates_error));
                    }
                }
            } else {
                latitude.requestFocus();
                latitude.performClick();
            }
            return true;

        });

        polygon.setFocusable(false);
        polygon.setClickable(false);

        latitude.setFocusable(true);
        latitude.setClickable(true);

        longitude.setFocusable(true);
        longitude.setClickable(true);

        ImageButton position = findViewById(R.id.location1);
        ImageButton map = findViewById(R.id.location2);

        position.setOnClickListener(this);
        map.setOnClickListener(this);
        clearButton.setOnClickListener(this);

        longitude.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (!longitude.getText().toString().isEmpty()) {
                    Double lon = DoubleExtensionsKt.truncate(getLongitude());
                    longitude.setText(lon.toString());
                    if (!latitude.getText().toString().isEmpty()) {
                        Double lat = getLatitude();
                        if (!LngLatValidatorKt.areLngLatCorrect(lon, lat)) {
                            setError(getContext().getString(R.string.coordinates_error));
                        } else {
                            setError(null);
                        }
                    }
                }
            } else {
                activate();
            }
        });

        latitude.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (!latitude.getText().toString().isEmpty()) {
                    Double lat = DoubleExtensionsKt.truncate(getLatitude());
                    latitude.setText(lat.toString());
                    if (!longitude.getText().toString().isEmpty()) {
                        Double lon = getLongitude();
                        if (!LngLatValidatorKt.areLngLatCorrect(lon, lat)) {
                            setError(getContext().getString(R.string.coordinates_error));
                        } else {
                            setError(null);
                        }
                    }
                }
            } else {
                activate();
            }
        });
    }

    private boolean validateCoordinates() {

        return (!isEmpty(latitude.getText()) && !isEmpty(longitude.getText())) ||
                (isEmpty(latitude.getText()) && isEmpty(longitude.getText()));
    }

    public void setCurrentLocationListener(OnCurrentLocationClick currentLocationListener) {
        this.currentLocationListener = currentLocationListener;
    }

    public void setLabel(String label) {
        this.label = label;
        if (binding instanceof FormCoordinatesBinding)
            ((FormCoordinatesBinding) binding).setLabel(label);
        else
            ((FormCoordinatesAccentBinding) binding).setLabel(label);
    }

    public void setDescription(String description) {
        if (binding instanceof FormCoordinatesBinding)
            ((FormCoordinatesBinding) binding).setDescription(description);
        else
            ((FormCoordinatesAccentBinding) binding).setDescription(description);

        ImageView descriptionIcon = findViewById(R.id.descriptionLabel);
        descriptionIcon.setOnClickListener(v ->
                new CustomDialog(
                        getContext(),
                        label,
                        description != null ? description : getContext().getString(R.string.empty_description),
                        getContext().getString(R.string.action_close),
                        null,
                        Constants.DESCRIPTION_DIALOG,
                        null
                ).show());
    }

    public void setFeatureType(FeatureType featureType) {
        this.featureType = featureType;
        latitudeInputLayout.setVisibility(featureType == FeatureType.POINT ? View.VISIBLE : View.GONE);
        longitudeInputLayout.setVisibility(featureType == FeatureType.POINT ? View.VISIBLE : View.GONE);
        polygonInputLayout.setVisibility(featureType != FeatureType.POINT ? View.VISIBLE : View.GONE);
        location1.setVisibility(featureType == FeatureType.POINT ? View.VISIBLE : View.GONE);
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

    public void setInitialValue(String initialValue) {
        if (featureType == null)
            throw new NullPointerException("use setFeatureType before setting an initial value");
        setCoordinatesValue(
                Geometry.builder()
                        .coordinates(initialValue)
                        .type(featureType)
                        .build());
        updateDeleteVisibility(clearButton);

    }

    public void setWarning(String msg) {
        if (!isEmpty(msg)) {
            errorView.setTextColor(ContextCompat.getColor(getContext(), R.color.warning_color));
            errorView.setText(msg);
            errorView.setVisibility(VISIBLE);
        } else
            errorView.setVisibility(GONE);
    }

    public void setError(String msg) {
        if (!isEmpty(msg)) {
            errorView.setTextColor(ContextCompat.getColor(getContext(), R.color.error_color));
            errorView.setText(msg);
            errorView.setVisibility(VISIBLE);
            clearValueData();
        } else
            errorView.setVisibility(GONE);
    }

    @Override
    public void onClick(View view) {
        activate();
        switch (view.getId()) {
            case R.id.location1:
                getLocation();
                break;
            case R.id.location2:
                onMapPositionClick();
                break;
            case R.id.clearButton:
                clearValueData();
                updateLocation(null);
                if (errorView.getVisibility() == VISIBLE) {
                    setError(null);
                }
                break;
        }
    }

    public void getLocation() {
        ((App) getContext().getApplicationContext()).appComponent()
                .locationProvider().getLastKnownLocation(
                location -> {
                    double longitude = DoubleExtensionsKt.truncate(location.getLongitude());
                    double latitude = DoubleExtensionsKt.truncate(location.getLatitude());
                    updateLocation(GeometryHelper.createPointGeometry(longitude, latitude));
                    return Unit.INSTANCE;
                },
                () -> {
                    ActivityCompat.requestPermissions((ActivityGlobalAbstract) getContext(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            ACCESS_LOCATION_PERMISSION_REQUEST);
                    return Unit.INSTANCE;
                },
                () -> {
                    ((ActivityGlobalAbstract) this.getContext()).requestEnableLocation();
                    return Unit.INSTANCE;
                });
    }

    public void setIsBgTransparent(boolean isBgTransparent) {
        this.isBgTransparent = isBgTransparent;
        setLayout();
    }

    public void setEditable(Boolean editable) {
        latitude.setEnabled(editable);
        longitude.setEnabled(editable);
        clearButton.setEnabled(editable);
        findViewById(R.id.location1).setEnabled(editable);
        findViewById(R.id.location2).setEnabled(editable);

        latitude.setTextColor(
                !isBgTransparent ? ColorUtils.getPrimaryColor(getContext(), ColorUtils.ColorType.ACCENT) :
                        ContextCompat.getColor(getContext(), R.color.textPrimary)
        );

        longitude.setTextColor(
                !isBgTransparent ? ColorUtils.getPrimaryColor(getContext(), ColorUtils.ColorType.ACCENT) :
                        ContextCompat.getColor(getContext(), R.color.textPrimary)
        );

        setEditable(editable,
                labelText,
                latitudeInputLayout,
                longitudeInputLayout,
                findViewById(R.id.formLabel),
                findViewById(R.id.location1),
                findViewById(R.id.location2),
                clearButton,
                findViewById(R.id.descriptionLabel)
        );
        updateDeleteVisibility(clearButton);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            activate();
            latitude.performClick();
        } else {
            viewModel.onDeactivate();
        }
    }

    @Override
    public void dispatchSetActivated(boolean activated) {
        super.dispatchSetActivated(activated);
        if (activated) {
            labelText.setTextColor(ColorUtils.getPrimaryColor(getContext(), ColorUtils.ColorType.PRIMARY));
        } else {
            labelText.setTextColor(ResourcesCompat.getColor(getResources(), R.color.textPrimary, null));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK && requestCode == Constants.RQ_MAP_LOCATION_VIEW) {
            if (data.getExtras() != null) {
                FeatureType locationType = FeatureType.valueOf(data.getStringExtra(MapSelectorActivity.LOCATION_TYPE_EXTRA));
                String dataExtra = data.getStringExtra(MapSelectorActivity.DATA_EXTRA);
                Geometry geometry;
                if (locationType == FeatureType.POINT) {
                    Type type = new TypeToken<List<Double>>() {
                    }.getType();
                    geometry = GeometryHelper.createPointGeometry(new Gson().fromJson(dataExtra, type));
                } else if (locationType == FeatureType.POLYGON) {
                    Type type = new TypeToken<List<List<List<Double>>>>() {
                    }.getType();
                    geometry = GeometryHelper.createPolygonGeometry(new Gson().fromJson(dataExtra, type));
                } else {
                    Type type = new TypeToken<List<List<List<List<Double>>>>>() {
                    }.getType();
                    geometry = GeometryHelper.createMultiPolygonGeometry(new Gson().fromJson(dataExtra, type));
                }
                updateLocation(geometry);
            }
        }
    }

    public void onMapPositionClick() {
        subscribe();
        ((FragmentActivity) getContext()).startActivityForResult(MapSelectorActivity.Companion.create(
                (FragmentActivity) getContext(),
                getFeatureType(),
                currentCoordinates()),
                RQ_MAP_LOCATION_VIEW);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        if (requestCode == ACCESS_LOCATION_PERMISSION_REQUEST) {
            if (grantResults[0] == PERMISSION_GRANTED) {
                getLocation();
            }
        }
    }

    public interface OnCurrentLocationClick {
        void onCurrentLocationClick(Geometry geometry);
    }

    @SuppressLint("MissingPermission")
    public void updateLocation(Geometry geometry) {

        setCoordinatesValue(geometry);

        this.currentGeometry = geometry;
        if (currentLocationListener != null)
            currentLocationListener.onCurrentLocationClick(geometry);
        invalidate();
    }

    private void setCoordinatesValue(Geometry geometry) {
        if (geometry != null && geometry.type() != null) {
            if (geometry.type() == FeatureType.POINT) {
                try {
                    List<Double> list = GeometryHelper.getPoint(geometry);
                    this.latitude.setText(String.valueOf(list.get(1)));
                    this.longitude.setText(String.valueOf(list.get(0)));
                } catch (D2Error d2Error) {
                    d2Error.printStackTrace();
                }

            } else if (geometry.type() == FeatureType.POLYGON) {
                this.polygon.setText(getContext().getString(R.string.polygon_captured));
            } else if (geometry.type() == FeatureType.MULTI_POLYGON) {
                this.polygon.setText(getContext().getString(R.string.polygon_captured));
            }
        }
        updateDeleteVisibility(clearButton);
    }

    public void clearValueData() {
        this.latitude.setText(null);
        this.longitude.setText(null);
    }

    public Double getLatitude() {
        String latString = StringExtensionsKt.parseToDouble(latitude.getText().toString());
        return Double.valueOf(latString);
    }

    public Double getLongitude() {
        String lonString = StringExtensionsKt.parseToDouble(longitude.getText().toString());
        return Double.valueOf(lonString);
    }

    public String currentCoordinates() {
        return currentGeometry != null ? currentGeometry.coordinates() : null;
    }

    @Override
    protected boolean hasValue() {
        return latitude.getText() != null && !latitude.getText().toString().isEmpty() &&
                longitude.getText() != null && !longitude.getText().toString().isEmpty();
    }

    @Override
    protected boolean isEditable() {
        return latitude.isEnabled() && longitude.isEnabled();
    }

    public void setViewModel(CoordinateViewModel viewModel) {
        this.viewModel = viewModel;

        if (binding == null) {
            setIsBgTransparent(viewModel.isBackgroundTransparent());
        }

        setCurrentLocationListener(geometry -> {
            closeKeyboard(binding.getRoot());
            viewModel.onCurrentLocationClick(geometry);
            clearBackground(viewModel.isSearchMode());
            viewModel.onDeactivate();
        });
        setActivationListener(viewModel::onActivate);

        setFeatureType(viewModel.featureType());
        setLabel(viewModel.getFormattedLabel());
        setDescription(viewModel.description());

        setInitialValue(viewModel.value());
        setWarning(viewModel.warning());
        setError(viewModel.error());
        setEditable(viewModel.editable());
    }

    private void clearBackground(boolean isSearchMode) {
        if (!isSearchMode) {
            binding.getRoot().setBackgroundResource(R.color.form_field_background);
        }
    }

    private void subscribe() {
        ((ActivityResultObservable) getContext()).subscribe(this);
    }
}

