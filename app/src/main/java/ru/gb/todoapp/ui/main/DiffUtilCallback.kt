package ru.gb.todoapp.ui.main

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback(
    private val oldItems: List<Pair<Data, Boolean>>,
    private val newItems: List<Pair<Data, Boolean>>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].first.tittle == newItems[newItemPosition].first.tittle
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]

        return oldItem.first.tittle == newItem.first.tittle &&
                oldItem.first.description == newItem.first.description
    }
}