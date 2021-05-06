package org.fpandhis2.usescases.datasets.dataSetTable;

import org.fpandhis2.usescases.general.AbstractActivityContracts;
import org.fpandhis2.utils.validationrules.Violation;
import org.hisp.dhis.android.core.dataset.DataSet;
import org.hisp.dhis.android.core.period.Period;

import java.util.List;

import io.reactivex.Observable;

public class DataSetTableContract {

    public interface View extends AbstractActivityContracts.View {

        void setSections(List<String> sections);

        Boolean accessDataWrite();

        String getDataSetUid();

        String getOrgUnitName();

        void renderDetails(DataSet dataSet, String catcomboName, Period period, boolean isComplete);

        Observable<Object> observeSaveButtonClicks();

        void showMandatoryMessage(boolean isMandatoryFields);

        void showValidationRuleDialog();

        void showSuccessValidationDialog();

        void savedAndCompleteMessage();

        void showErrorsValidationDialog(List<Violation> violations);

        void showCompleteToast();

        void collapseExpandBottom();

        void closeBottomSheet();

        void completeBottomSheet();

        void displayReopenedMessage(boolean done);

        void showInternalValidationError();

        void saveAndFinish();

        boolean isErrorBottomSheetShowing();
    }

    public interface Presenter extends AbstractActivityContracts.Presenter {

        void onBackClick();

        void init(String orgUnitUid, String periodTypeName, String catCombo, String periodFinalDate, String periodId);

        String getOrgUnitUid();

        String getPeriodTypeName();

        String getPeriodFinalDate();

        String getCatCombo();

        String getPeriodId();

        void executeValidationRules();

        void completeDataSet();

        void collapseExpandBottomSheet();

        void closeBottomSheet();

        void onCompleteBottomSheet();

        boolean isValidationMandatoryToComplete();

        void reopenDataSet();

        boolean shouldAllowCompleteAnyway();

        boolean isComplete();
    }

}
