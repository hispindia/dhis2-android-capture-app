package org.medhis2yes.usescases.main.program

import androidx.recyclerview.widget.RecyclerView
import org.medhis2yes.R
import org.medhis2yes.databinding.ItemProgramModelBinding
import org.medhis2yes.utils.ColorUtils
import org.medhis2yes.utils.resources.ResourceManager

class ProgramModelHolder(private val binding: ItemProgramModelBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(presenter: ProgramPresenter, programViewModel: ProgramViewModel) {
        binding.program = programViewModel
        binding.presenter = presenter

        val color = ColorUtils.getColorFrom(
            programViewModel.color(),
            ColorUtils.getPrimaryColor(
                binding.programImage.context,
                ColorUtils.ColorType.PRIMARY_LIGHT
            )
        )

        binding.programImage.background = ColorUtils.tintDrawableWithColor(
            binding.programImage.background,
            color
        )

        binding.programImage.setImageResource(
            ResourceManager(itemView.context).getObjectStyleDrawableResource(
                programViewModel.icon(),
                R.drawable.ic_program_default
            )
        )

        binding.programImage.setColorFilter(ColorUtils.getContrastColor(color))

        itemView.setOnClickListener { v ->
            val programTheme = ColorUtils.getThemeFromColor(programViewModel.color())
            presenter.onItemClick(programViewModel, programTheme)
        }

        binding.root.alpha = if (programViewModel.translucent()) {
            0.5f
        } else {
            1.0f
        }
    }
}
