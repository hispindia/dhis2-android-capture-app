package org.dhis2_haparent.maps.extensions

const val PROPERTY_FEATURE_SOURCE = "FeatureSource"

enum class FeatureSource {
    TEI,
    ENROLLMENT,
    RELATIONSHIP,
    TRACKER_EVENT,
    EVENT,
    FIELD
}
