package org.dhis2_haparent.form.ui.provider

import org.dhis2_haparent.form.model.UiRenderType
import org.hisp.dhis.android.core.common.FeatureType
import org.hisp.dhis.android.core.common.ValueTypeRenderingType
import org.hisp.dhis.android.core.program.SectionRenderingType

interface UiEventTypesProvider {

    fun provideUiRenderType(
        featureType: FeatureType?,
        valueTypeRenderingType: ValueTypeRenderingType?,
        sectionRenderingType: SectionRenderingType?
    ): UiRenderType
}
