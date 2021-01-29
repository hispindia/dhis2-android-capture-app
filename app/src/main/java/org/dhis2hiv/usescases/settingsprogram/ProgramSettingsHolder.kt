package org.dhis2hiv.usescases.settingsprogram

import androidx.recyclerview.widget.RecyclerView
import org.dhis2hiv.Bindings.toTrailingText
import org.dhis2hiv.R
import org.dhis2hiv.databinding.ItemSettingProgramBinding
import org.dhis2hiv.utils.ColorUtils
import org.dhis2hiv.utils.resources.ResourceManager

class ProgramSettingsHolder(
    private val binding: ItemSettingProgramBinding,
    private val resourceManager: ResourceManager
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(settingProgram: ProgramSettingsViewModel) {
        val color = ColorUtils.getColorFrom(
            settingProgram.color,
            ColorUtils.getPrimaryColor(
                itemView.context,
                ColorUtils.ColorType.PRIMARY_LIGHT
            )
        )

        val icon = resourceManager.getObjectStyleDrawableResource(
            settingProgram.icon,
            R.drawable.ic_default_positive
        )
        binding.programIcon.setBackgroundColor(color)
        binding.programIcon.setImageResource(icon)

        binding.programName.text = settingProgram.programSettings.name()

        val settings: String = if (settingProgram.programSettings.eventsDownload() != null) {
            "${settingProgram.programSettings.eventsDownload()} " +
                "${itemView.context.getString(R.string.events)} " +
                settingProgram.programSettings.settingDownload().toTrailingText(itemView.context)
        } else {
            "${settingProgram.programSettings.teiDownload()} " +
                "${itemView.context.getString(R.string.teis)} " +
                settingProgram.programSettings.settingDownload().toTrailingText(itemView.context)
        }

        binding.programSettings.text = settings
    }
}
