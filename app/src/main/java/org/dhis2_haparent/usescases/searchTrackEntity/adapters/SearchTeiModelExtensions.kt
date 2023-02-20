package org.dhis2_haparent.usescases.searchTrackEntity.adapters

import org.dhis2_haparent.commons.data.SearchTeiModel

fun List<SearchTeiModel>.uids(): List<String> {
    return map { it.tei.uid() }
}
