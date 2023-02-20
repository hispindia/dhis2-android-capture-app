package org.dhis2_haparent.commons.featureconfig.data

import org.dhis2_haparent.commons.featureconfig.model.Feature
import org.dhis2_haparent.commons.featureconfig.model.FeatureState

interface FeatureConfigRepository {

    val featuresList: List<FeatureState>
    fun updateItem(featureState: FeatureState)
    fun isFeatureEnable(feature: Feature): Boolean
}
