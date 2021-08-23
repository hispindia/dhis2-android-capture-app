package org.medhis2yes.usescases.searchTrackEntity.adapters

fun List<SearchTeiModel>.uids(): List<String> {
    return map { it.tei.uid() }
}
