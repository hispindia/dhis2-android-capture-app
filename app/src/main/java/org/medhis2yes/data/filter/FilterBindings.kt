package org.medhis2yes.data.filter

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.Observable
import org.medhis2yes.animations.collapse
import org.medhis2yes.animations.expand
import org.medhis2yes.animations.hide
import org.medhis2yes.animations.show
import org.medhis2yes.utils.filters.CatOptionComboFilter
import org.medhis2yes.utils.filters.FilterItem
import org.medhis2yes.utils.filters.OrgUnitFilter

@BindingAdapter("expand_view")
fun View.setExpanded(expanded: Boolean) {
    if (expanded && visibility == View.GONE) {
        expand { }
    } else if (!expanded && visibility == View.VISIBLE) {
        collapse { }
    }
}

@BindingAdapter("request_layout")
fun View.setRequestLayout(filterItem: FilterItem) {
    if (filterItem is CatOptionComboFilter || filterItem is OrgUnitFilter) {
        filterItem.observeCount()
            .addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    expand(true) { }
                }
            })
    }
}

@BindingAdapter("animated_visibility")
fun View.setAnimatedVisibility(visible: Boolean) {
    if (visible) {
        show()
    } else {
        hide()
    }
}
