package org.medhis2yes.usescases.settingsprogram

import androidx.recyclerview.widget.RecyclerView
import org.medhis2yes.Bindings.toTrailingText
import org.medhis2yes.R
import org.medhis2yes.databinding.ItemSettingProgramBinding
import org.medhis2yes.utils.ColorUtils
import org.medhis2yes.utils.resources.ResourceManager

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
