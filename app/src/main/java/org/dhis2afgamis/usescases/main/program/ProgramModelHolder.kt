package org.dhis2afgamis.usescases.main.program

import androidx.recyclerview.widget.RecyclerView
import org.dhis2afgamis.R
import org.dhis2afgamis.databinding.ItemProgramModelBinding
import org.dhis2afgamis.utils.ColorUtils
import org.dhis2afgamis.utils.resources.ResourceManager

class ProgramModelHolder(private val binding: ItemProgramModelBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(presenter: ProgramPresenter, programViewModel: ProgramViewModel) {
        binding.program = programViewModel
        binding.presenter = presenter

        val color = ColorUtils.getColorFrom(
            programViewModel.color(),
            ColorUtils.getPrimaryColor(binding.programImage.context, ColorUtils.ColorType.PRIMARY)
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
