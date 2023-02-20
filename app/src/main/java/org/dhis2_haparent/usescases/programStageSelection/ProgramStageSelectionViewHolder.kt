package org.dhis2_haparent.usescases.programStageSelection

import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.recyclerview.widget.RecyclerView
import org.dhis2_haparent.R
import org.dhis2_haparent.commons.resources.ColorUtils
import org.dhis2_haparent.commons.resources.ResourceManager
import org.dhis2_haparent.databinding.ItemProgramStageBinding
import org.dhis2_haparent.ui.MetadataIconData
import org.dhis2_haparent.ui.setUpMetadataIcon
import org.hisp.dhis.android.core.program.ProgramStage

class ProgramStageSelectionViewHolder(
    private val binding: ItemProgramStageBinding,
    val onItemClick: (ProgramStage) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.composeProgramStageIcon.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
        )
    }

    fun bind(programStage: ProgramStage) {
        binding.programStage = programStage
        binding.executePendingBindings()

        val color = ColorUtils.getColorFrom(
            programStage.style().color(),
            ColorUtils.getPrimaryColor(
                itemView.context,
                ColorUtils.ColorType.PRIMARY_LIGHT
            )
        )

        val iconResource = ResourceManager(itemView.context).getObjectStyleDrawableResource(
            programStage.style().icon(),
            R.drawable.ic_default_outline
        )

        binding.composeProgramStageIcon.setUpMetadataIcon(
            MetadataIconData(
                programColor = color,
                iconResource = iconResource,
                sizeInDp = 80
            ),
            false
        )

        itemView.setOnClickListener {
            onItemClick(programStage)
        }
    }
}
