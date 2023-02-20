package org.dhis2_haparent.usescases.programEventDetail.eventList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.test.espresso.idling.concurrent.IdlingThreadPoolExecutor
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import org.dhis2_haparent.R
import org.dhis2_haparent.commons.data.EventViewModel
import org.dhis2_haparent.databinding.FragmentProgramEventDetailListBinding
import org.dhis2_haparent.usescases.general.FragmentGlobalAbstract
import org.dhis2_haparent.usescases.programEventDetail.ProgramEventDetailActivity
import org.dhis2_haparent.usescases.programEventDetail.ProgramEventDetailLiveAdapter
import org.dhis2_haparent.usescases.programEventDetail.ProgramEventDetailViewModel

class EventListFragment : FragmentGlobalAbstract(), EventListFragmentView {

    lateinit var binding: FragmentProgramEventDetailListBinding
    private var liveAdapter: ProgramEventDetailLiveAdapter? = null
    private val programEventsViewModel: ProgramEventDetailViewModel by activityViewModels()

    @Inject
    lateinit var presenter: EventListPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as ProgramEventDetailActivity).component
            ?.plus(EventListModule(this))
            ?.inject(this)
        programEventsViewModel.setProgress(true)

        val bgThreadPoolExecutor = IdlingThreadPoolExecutor(
            "DiffExecutor",
            2,
            2,
            0L,
            TimeUnit.MILLISECONDS,
            LinkedBlockingQueue(),
            Executors.defaultThreadFactory()
        )

        val config = AsyncDifferConfig.Builder(ProgramEventDetailLiveAdapter.diffCallback)
            .setBackgroundThreadExecutor(bgThreadPoolExecutor)
            .build()

        liveAdapter =
            ProgramEventDetailLiveAdapter(presenter.program(), programEventsViewModel, config)
        return FragmentProgramEventDetailListBinding.inflate(inflater, container, false)
            .apply {
                binding = this
                recycler.adapter = liveAdapter
            }.root
    }

    override fun onResume() {
        super.onResume()
        programEventsViewModel.setProgress(true)
        presenter.init()
    }

    override fun setLiveData(pagedListLiveData: LiveData<PagedList<EventViewModel>>) {
        pagedListLiveData.observe(
            this
        ) { pagedList: PagedList<EventViewModel> ->
            programEventsViewModel.setProgress(false)
            liveAdapter?.submitList(pagedList) {
                if ((binding.recycler.adapter?.itemCount ?: 0) == 0) {
                    binding.emptyTeis.text = getString(R.string.empty_tei_add)
                    binding.emptyTeis.visibility = View.VISIBLE
                    binding.recycler.visibility = View.GONE
                } else {
                    binding.emptyTeis.visibility = View.GONE
                    binding.recycler.visibility = View.VISIBLE
                }
            }
        }
    }
}
