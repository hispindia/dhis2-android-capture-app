package org.medhis2yes.usescases.programEventDetail;

import org.medhis2yes.usescases.general.AbstractActivityContracts;
import org.medhis2yes.utils.filters.FilterItem;
import org.medhis2yes.utils.filters.FilterManager;
import org.medhis2yes.utils.filters.workingLists.WorkingListItem;
import org.hisp.dhis.android.core.common.FeatureType;
import org.hisp.dhis.android.core.program.Program;

import java.util.List;

public class ProgramEventDetailContract {

    public interface View extends AbstractActivityContracts.View {

        void setProgram(Program programModel);

        void renderError(String message);

        void showHideFilter();

        void setWritePermission(Boolean aBoolean);

        void showFilterProgress();

        void updateFilters(int totalFilters);

        void openOrgUnitTreeSelector();

        void showPeriodRequest(FilterManager.PeriodRequest periodRequest);

        void setFeatureType(FeatureType featureType);

        void startNewEvent();

        void navigateToEvent(String eventId, String orgUnit);

        void showSyncDialog(String uid);

        void showCatOptComboDialog(String catComboUid);

        void setFilterItems(List<FilterItem> programFilters);

        void hideFilters();
    }

    public interface Presenter extends AbstractActivityContracts.Presenter {
        void init();

        void addEvent();

        void onBackClick();

        void showFilter();

        void onSyncIconClick(String uid);

        void clearFilterClick();

        void filterCatOptCombo(String selectedCatOptionCombo);

        Program getProgram();

        FeatureType getFeatureType();

        List<WorkingListItem> workingLists();

        void clearOtherFiltersIfWebAppIsConfig();

        void setOpeningFilterToNone();

        String getStageUid();
    }
}
