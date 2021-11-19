package ru.gb.todoapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.gb.todoapp.R

class MainAdapter(
    private var data: MutableList<Data>,
    private val onItemViewClicksListener: OnItemViewClicksListener
): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    fun setData(newData: List<Data>){
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


    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(data: Data) {
            itemView.apply {
                findViewById<TextView>(R.id.DescriptionTextView).text = data.description
                findViewById<TextView>(R.id.tittleText).text =  data.tittle
                findViewById<TextView>(R.id.delete).setOnClickListener { removeItem()}
            }
            itemView.setOnClickListener { onItemViewClicksListener.onItemClick(data) }
        }

        private fun removeItem() {
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun addItem() {
            data.add(layoutPosition, generateItem())
            notifyItemInserted(layoutPosition)

        }

    }



    fun interface OnItemViewClicksListener{
        fun onItemClick(data: Data)
    }

    fun appendItem(){
        data.add(generateItem())
        notifyDataSetChanged()
    }
    fun generateItem() = Data("New Note")


}