package org.dhis2hiv.data.server;

import io.reactivex.Flowable;

public interface DataBaseExporter {
    Flowable<Boolean> exportDb();
    Flowable<Boolean> importDb();
}
