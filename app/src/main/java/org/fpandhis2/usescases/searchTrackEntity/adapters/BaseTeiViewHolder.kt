package org.fpandhis2.usescases.searchTrackEntity.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.fpandhis2.Bindings.addEnrollmentIcons
import org.fpandhis2.Bindings.hasFollowUp
import org.fpandhis2.Bindings.setAttributeList
import org.fpandhis2.Bindings.setStatusText
import org.fpandhis2.Bindings.setTeiImage
import org.fpandhis2.Bindings.toDateSpan
import org.fpandhis2.databinding.ItemSearchTrackedEntityBinding
import org.fpandhis2.usescases.searchTrackEntity.SearchTEContractsModule

abstract class BaseTeiViewHolder(
    private val binding: ItemSearchTrackedEntityBinding
) : RecyclerView.ViewHolder(binding.root) {

    lateinit var presenter: SearchTEContractsModule.Presenter
    lateinit var teiModel: SearchTeiModel

    abstract fun itemViewClick()

    abstract fun itemConfiguration()

    fun bind(
        presenter: SearchTEContractsModule.Presenter,
        teiModel: SearchTeiModel,
        attributeVisibilityCallback: () -> Unit,
        profileImagePreviewCallback: (String) -> Unit
    ) {
        this.presenter = presenter
        this.teiModel = teiModel
        if (teiModel.isAttributeListOpen) {
            showAttributeList()
        } else {
            hideAttributeList()
        }

        binding.apply {
            overdue = teiModel.isHasOverdue
            isOnline = teiModel.isOnline
            teiSyncState = teiModel.tei.state()
            attribute = teiModel.attributeValues.values.toList()
            attributeNames = teiModel.attributeValues.keys
            attributeListOpened = teiModel.isAttributeListOpen
            lastUpdated.text = teiModel.tei.lastUpdated().toDateSpan(itemView.context)
            sortingValue = teiModel.sortingValue
        }

        teiModel.apply {
            binding.setFollowUp(enrollments.hasFollowUp())
            programInfo.addEnrollmentIcons(
                itemView.context,
                binding.programList,
                if (selectedEnrollment != null) selectedEnrollment.program() else null
            )
            if (selectedEnrollment != null) {
                selectedEnrollment.setStatusText(
                    itemView.context,
                    binding.enrollmentStatus,
                    isHasOverdue,
                    overdueDate
                )
            }
            setTeiImage(
                itemView.context,
                binding.trackedEntityImage,
                binding.imageText,
                profileImagePreviewCallback
            )
            attributeValues.setAttributeList(
                binding.attributeList,
                binding.showAttributesButton,
                adapterPosition,
                teiModel.isAttributeListOpen,
                teiModel.sortingKey,
                teiModel.sortingValue
            ) {
                attributeVisibilityCallback()
            }
        }

        binding.executePendingBindings()
        itemConfiguration()
        itemViewClick()
    }

    private fun showAttributeList() {
        binding.attributeBName.visibility = View.GONE
        binding.attributeCName.visibility = View.GONE
        binding.sortingFieldName.visibility = View.GONE
        binding.entityAttribute2.visibility = View.GONE
        binding.entityAttribute3.visibility = View.GONE
        binding.sortingFieldValue.visibility = View.GONE
        binding.attributeList.visibility = View.VISIBLE
    }

    private fun hideAttributeList() {
        binding.attributeList.visibility = View.GONE
        binding.attributeBName.visibility = View.VISIBLE
        binding.attributeCName.visibility = View.VISIBLE
        binding.sortingFieldName.visibility = View.VISIBLE
        binding.entityAttribute2.visibility = View.VISIBLE
        binding.entityAttribute3.visibility = View.VISIBLE
        binding.sortingFieldValue.visibility = View.VISIBLE
    }
}
