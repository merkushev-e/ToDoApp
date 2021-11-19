package ru.gb.todoapp.ui.main

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback(
    private val oldItems: List<Data>,
    private val newItems: List<Data>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].tittle == newItems[newItemPosition].tittle
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]

        return oldItem.tittle == newItem.tittle &&
                oldItem.description == newItem.description
    }
}