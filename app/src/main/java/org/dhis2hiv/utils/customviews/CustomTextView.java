package org.dhis2hiv.utils.customviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.google.android.material.textfield.TextInputLayout;

import org.dhis2hiv.BR;
import org.dhis2hiv.R;
import org.dhis2hiv.data.forms.dataentry.validation.ValueTypeValidatorFactoryKt;
import org.dhis2hiv.utils.ColorUtils;
import org.dhis2hiv.utils.ObjectStyleUtils;
import org.dhis2hiv.utils.Validator;
import org.hisp.dhis.android.core.common.ObjectStyle;
import org.hisp.dhis.android.core.common.ValueType;
import org.hisp.dhis.android.core.program.ProgramStageSectionRenderingType;

import java.util.regex.Pattern;

import static android.text.TextUtils.isEmpty;

/**
 * QUADRAM. Created by frodriguez on 1/17/2018.
 */

public class CustomTextView extends FieldLayout {

    String urlStringPattern = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";
    Pattern urlPattern = Pattern.compile(urlStringPattern);

    private boolean isBgTransparent;
    private TextInputAutoCompleteTextView editText;
    private ImageView icon;
    private ImageView descIcon;
    private String label;
    private ValueType valueType;
    private ViewDataBinding binding;
    private Validator validator;

    private OnFocusChangeListener focusListener;

    private LayoutInflater inflater;
    private TextInputLayout inputLayout;
    private boolean isLongText;
    private View descriptionLabel;
    private View dummy;
    private TextView labelText;

    public CustomTextView(Context context) {
        super(context);
        init(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setLayout() {
        if (isBgTransparent && !isLongText)
            binding = DataBindingUtil.inflate(inflater, R.layout.custom_text_view, this, true);
        else if (!isBgTransparent && !isLongText)
            binding = DataBindingUtil.inflate(inflater, R.layout.custom_text_view_accent, this, true);
        else if (isBgTransparent && isLongText)
            binding = DataBindingUtil.inflate(inflater, R.layout.custom_long_text_view, this, true);
        else
            binding = DataBindingUtil.inflate(inflater, R.layout.custom_long_text_view_accent, this, true);

        inputLayout = findViewById(R.id.input_layout);
        editText = findViewById(R.id.input_editText);
        icon = findViewById(R.id.renderImage);
        descriptionLabel = binding.getRoot().findViewById(R.id.descriptionLabel);
        dummy = findViewById(R.id.dummyFocusView);
        labelText = findViewById(R.id.label);

        descIcon = findViewById(R.id.descIcon);

        editText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                activate();
            } else if (focusListener != null && validate()) {
                focusListener.onFocusChange(v, hasFocus);
            }
        });

        if(isLongText){
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    updateDeleteVisibility(findViewById(R.id.clear_button));
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }

    public void setDescription(String description) {
        descriptionLabel.setVisibility(description != null ? View.VISIBLE : View.GONE);
    }

    private void configureViews() {

        editText.setFilters(new InputFilter[]{});
        editText.setMaxLines(1);
        editText.setVerticalScrollBarEnabled(false);
        descIcon.setVisibility(View.GONE);

        if (valueType != null)
            switch (valueType) {
                case PHONE_NUMBER:
                    editText.setInputType(InputType.TYPE_CLASS_PHONE);
                    break;
                case EMAIL:
                    editText.setInputType(InputType.TYPE_CLASS_TEXT |
                            InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    break;
                case TEXT:
                    editText.setInputType(InputType.TYPE_CLASS_TEXT);
                    editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(50000)});
                    editText.setLines(1);
                    editText.setEllipsize(TextUtils.TruncateAt.END);
                    break;
                case LONG_TEXT:
                    editText.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    editText.setMaxLines(Integer.MAX_VALUE);
                    editText.setEllipsize(null);
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    editText.setVerticalScrollBarEnabled(true);
                    editText.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
                    editText.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
                    editText.setSingleLine(false);
                    editText.setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                    findViewById(R.id.clear_button).setOnClickListener(v -> {
                        editText.getText().clear();
                        updateDeleteVisibility(findViewById(R.id.clear_button));
                    });
                    break;
                case LETTER:
                    editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                    editText.setFilters(new InputFilter[]{
                            new InputFilter.LengthFilter(1),
                            (source, start, end, dest, dstart, dend) -> {
                                if (source.equals(""))
                                    return source;
                                if (source.toString().matches("[a-zA-Z]"))
                                    return source;
                                return "";
                            }});
                    break;
                case NUMBER:
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER |
                            InputType.TYPE_NUMBER_FLAG_DECIMAL |
                            InputType.TYPE_NUMBER_FLAG_SIGNED);
                    break;
                case INTEGER_NEGATIVE:
                case INTEGER:
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    break;
                case INTEGER_ZERO_OR_POSITIVE:
                case INTEGER_POSITIVE:
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    editText.setKeyListener(DigitsKeyListener.getInstance(false, false));
                    break;
                case UNIT_INTERVAL:
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    break;
                case PERCENTAGE:
                    descIcon.setVisibility(VISIBLE);
                    descIcon.setImageResource(R.drawable.ic_form_percentage);
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;
                case URL:
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
                    break;
                default:
                    break;
            }
        editText.setCompoundDrawablePadding(24);
        binding.executePendingBindings();
    }

    public void setLayoutData(boolean isBgTransparent, boolean isLongText) {
        this.isBgTransparent = isBgTransparent;
        this.isLongText = isLongText;
        setLayout();
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
        this.validator = ValueTypeValidatorFactoryKt.getValidator(valueType);

        configureViews();
    }

    public void setEditable(Boolean editable) {
        editText.setFocusable(editable);
        editText.setFocusableInTouchMode(editable);
        editText.setClickable(editable);
        editText.setEnabled(editable);
        editText.setTextColor(
                !isBgTransparent && !isLongText ? ColorUtils.getPrimaryColor(getContext(), ColorUtils.ColorType.ACCENT) :
                        ContextCompat.getColor(getContext(), R.color.textPrimary)
        );

        if (findViewById(R.id.clear_button) != null) {
            findViewById(R.id.clear_button).setVisibility(editable ? View.VISIBLE : View.GONE);
        }

        setEditable(editable, labelText,
                inputLayout, descIcon, descriptionLabel, findViewById(R.id.clear_button));

        updateDeleteVisibility(findViewById(R.id.clear_button));
    }

    public void setWarning(String warning, String error) {
        if (!isEmpty(error)) {
            inputLayout.setErrorTextAppearance(R.style.error_appearance);
            inputLayout.setError(error);
            inputLayout.setErrorTextColor(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.error_color, null)));
        } else if (!isEmpty(warning)) {
            inputLayout.setErrorTextAppearance(R.style.warning_appearance);
            inputLayout.setError(warning);
            inputLayout.setErrorTextColor(ColorStateList.valueOf(ResourcesCompat.getColor(getResources(), R.color.warning_color, null)));
        } else
            inputLayout.setError(null);
    }

    public void setText(String text) {
        editText.setText(text);
        editText.setSelection(editText.getText() == null ?
                0 : editText.getText().length());
        updateDeleteVisibility(findViewById(R.id.clear_button));
    }

    public void setLabel(String label, boolean mandatory) {
        if (inputLayout.getHint() == null || !inputLayout.getHint().toString().equals(label)) {
            StringBuilder labelBuilder = new StringBuilder(label);
            if (mandatory)
                labelBuilder.append("*");
            this.label = labelBuilder.toString();
            inputLayout.setHint(null);
            binding.setVariable(BR.label, this.label);
        }
    }

    public void setHint(String hint) {
        binding.setVariable(BR.fieldHint, hint);
    }

    public TextInputAutoCompleteTextView getEditText() {
        return editText;
    }

    public TextInputLayout getInputLayout() {
        return inputLayout;
    }

    public void setFocusChangedListener(OnFocusChangeListener listener) {
        this.focusListener = listener;
    }

    private boolean validate() {
        if (editText.getText() != null && !isEmpty(editText.getText())) {
            switch (valueType) {
                case PHONE_NUMBER:
                    if (Patterns.PHONE.matcher(editText.getText().toString()).matches())
                        return true;
                    else {
                        inputLayout.setError(editText.getContext().getString(R.string.invalid_phone_number));
                        return false;
                    }
                case EMAIL:
                    if (Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString()).matches())
                        return true;
                    else {
                        inputLayout.setError(editText.getContext().getString(R.string.invalid_email));
                        return false;
                    }
                case INTEGER_NEGATIVE:
                    if (validator.validate(editText.getText().toString()))
                        return true;
                    else {
                        inputLayout.setError(editText.getContext().getString(R.string.invalid_negative_number));
                        return false;
                    }
                case INTEGER_ZERO_OR_POSITIVE:
                    if (validator.validate(editText.getText().toString()))
                        return true;
                    else {
                        inputLayout.setError(editText.getContext().getString(R.string.invalid_possitive_zero));
                        return false;
                    }
                case INTEGER_POSITIVE:
                    if (validator.validate(editText.getText().toString()))
                        return true;
                    else {
                        inputLayout.setError(editText.getContext().getString(R.string.invalid_possitive));
                        return false;
                    }
                case UNIT_INTERVAL:
                    if (Float.valueOf(editText.getText().toString()) >= 0 && Float.valueOf(editText.getText().toString()) <= 1)
                        return true;
                    else {
                        inputLayout.setError(editText.getContext().getString(R.string.invalid_interval));
                        return false;
                    }
                case PERCENTAGE:
                    if (Float.valueOf(editText.getText().toString()) >= 0 && Float.valueOf(editText.getText().toString()) <= 100)
                        return true;
                    else {
                        inputLayout.setError(editText.getContext().getString(R.string.invalid_percentage));
                        return false;
                    }
                case URL:
                    if (urlPattern.matcher(editText.getText().toString()).matches()) {
                        inputLayout.setError(null);
                        return true;
                    } else {
                        inputLayout.setError(getContext().getString(R.string.validation_url));
                        return false;
                    }
                case INTEGER:
                case NUMBER:
                    if (validator.validate(editText.getText().toString()))
                        return true;
                    else {
                        inputLayout.setError(editText.getContext().getString(R.string.formatting_error));
                        return false;
                    }
                default:
                    return true;
            }
        }
        return true;
    }

    public void setRenderType(String renderType) {
        if (renderType != null && !renderType.equals(ProgramStageSectionRenderingType.LISTING.name()))
            icon.setVisibility(View.VISIBLE);
    }

    public void setOnEditorActionListener(TextView.OnEditorActionListener actionListener) {
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (validate())
                return actionListener.onEditorAction(v, actionId, event);
            return true;
        });
    }


    public void setObjectStyle(ObjectStyle objectStyle) {
        Drawable styleIcon = ObjectStyleUtils.getIconResource(editText.getContext(), objectStyle.icon(), -1);
        icon.setImageDrawable(styleIcon);
        int colorResource = ObjectStyleUtils.getColorResource(
                editText.getContext(),
                objectStyle.color(),
                isBgTransparent ? R.color.default_field_icon_color : R.color.colorAccent
        );
        descIcon.setColorFilter(colorResource);
    }

    public void setOnLongActionListener(View.OnLongClickListener listener) {
        if (!editText.isFocusable())
            editText.setOnLongClickListener(listener);
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
    protected boolean hasValue(){
        return editText.getText()!=null && !editText.getText().toString().isEmpty();
    }

    @Override
    protected boolean isEditable(){
        return editText.isEnabled();
    }
}
