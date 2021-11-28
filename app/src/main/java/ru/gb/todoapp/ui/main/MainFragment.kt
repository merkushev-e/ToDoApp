package ru.gb.todoapp.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.gb.todoapp.EditNoteDialog
import ru.gb.todoapp.R
import ru.gb.todoapp.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private val itemTouchHelper: ItemTouchHelper by lazy {
        ItemTouchHelper(ItemTouchHelperCallback(adapter))
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!


    private val data = arrayListOf(
        Pair(
            Data(
                "Note1",
            ), false
        )

    )

    private val dragHandlerListener = object : MainAdapter.OnStartDragListener {
        override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
            itemTouchHelper.startDrag(viewHolder)
        }

    }
    private val adapter: MainAdapter by lazy {
        MainAdapter(
            data,
            { data, position -> openEditDialogFragment() },
            dragHandlerListener
        )
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.main_fragment, container, false)
        _binding = MainFragmentBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            itemDecoration.setDrawable(resources.getDrawable(R.drawable.separator, null))
            recyclerView.addItemDecoration(itemDecoration)


            adapter.setData(data.toMutableList())
            recyclerActivityFAB.setOnClickListener { adapter.appendItem() }


        }
            itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }

    private fun openEditDialogFragment(){
        val editDialog = EditNoteDialog.newInstance()
        editDialog.show(parentFragmentManager, "")
    }


}
