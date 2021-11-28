package ru.gb.todoapp.ui.main

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.MotionEventCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.gb.todoapp.R

class MainAdapter(
    private var data: MutableList<Pair<Data, Boolean>>,
    private val onEditItemClicksListener: OnEditItemClicksListener,
    private val dragListener: OnStartDragListener
): RecyclerView.Adapter<MainAdapter.MainViewHolder>(), ItemTouchHelperAdapter {

    fun setData(newData: List<Pair<Data, Boolean>>){
        val result = DiffUtil.calculateDiff(DiffUtilCallback(data,newData))
        result.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(newData)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view), ItemTouchHelperViewHolder{
        @SuppressLint("ClickableViewAccessibility")
        fun bind(data: Pair<Data, Boolean>) {
            itemView.apply {
                findViewById<TextView>(R.id.DescriptionTextView).apply {
                    text = data.first.description
                    visibility = if (data.second)
                        View.VISIBLE else View.GONE
                }
                findViewById<TextView>(R.id.tittleText).apply {
                    text =  data.first.tittle
                    setOnClickListener { toggleText() }
                }
                findViewById<ImageView>(R.id.delete).setOnClickListener { removeItem()}
                findViewById<ImageView>(R.id.edit_note).setOnClickListener { onEditItemClicksListener.onItemClick(data.first, layoutPosition) }

            }
            itemView.findViewById<ImageView>(R.id.dragHandleImageView).setOnTouchListener { v, event ->
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN){
                    dragListener.onStartDrag(this)
                }
                false
            }

        }

        private fun toggleText() {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun addItem() {
            data.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
           itemView.setBackgroundColor(Color.WHITE)
        }
    }



    fun appendItem(){
        data.add(generateItem())
        notifyDataSetChanged()
    }
    fun generateItem() = Pair(Data("New Note"),false)

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        data.removeAt(fromPosition).apply {
            data.add(toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }


    fun interface OnItemViewClicksListener{
        fun onItemClick(data: Data)
    }

    fun interface OnEditItemClicksListener{
        fun onItemClick(data: Data, position: Int)
    }

    interface OnStartDragListener {
        fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
    }


}