package org.dhis2hiv.usescases.sync;

import org.dhis2hiv.usescases.general.AbstractActivityContracts;

public class SyncContracts {

    public interface View extends AbstractActivityContracts.View{

        void saveTheme(Integer themeId);

        void saveFlag(String s);
    }

    public interface Presenter extends AbstractActivityContracts.Presenter {

        void init(View view);

        void sync();

        void syncReservedValues();

        void getTheme();
    }
}
