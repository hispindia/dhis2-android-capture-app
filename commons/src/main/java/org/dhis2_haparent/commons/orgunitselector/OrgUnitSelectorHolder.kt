package org.dhis2_haparent.commons.orgunitselector

import android.graphics.Typeface
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.dhis2_haparent.commons.R
import org.dhis2_haparent.commons.bindings.dp
import org.dhis2_haparent.commons.bindings.setTextStyle
import org.dhis2_haparent.commons.databinding.ItemOuTreeBinding
import org.hisp.dhis.android.core.organisationunit.OrganisationUnit

class OrgUnitSelectorHolder(
    private val binding: ItemOuTreeBinding,
    private val checkCallback: (OrganisationUnit, Boolean) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var node: TreeNode

    fun bind(
        node: TreeNode,
        preselected: Boolean
    ) {
        this.node = node
        binding.checkBox.setOnCheckedChangeListener(null)

        binding.ouName.text = node.displayName()
        binding.ouName.setTextStyle(
            when {
                node.selectedChildrenCount > 0 -> Typeface.BOLD
                else -> Typeface.NORMAL
            }
        )
        node.isChecked = preselected
        val marginParams = binding.root.layoutParams as ViewGroup.MarginLayoutParams
        marginParams.leftMargin = (node.level - 1) * 16.dp
        binding.checkBox.isChecked = node.isChecked

        if (!node.hasChild) {
            binding.icon.setImageResource(R.drawable.ic_ou_tree_circle_primary)
        } else if (node.isOpen) {
            binding.icon.setImageResource(R.drawable.ic_ou_tree_remove_circle_primary)
        } else {
            binding.icon.setImageResource(R.drawable.ic_ou_tree_add_circle)
        }

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            checkCallback(node.content, isChecked)
        }
    }
}
