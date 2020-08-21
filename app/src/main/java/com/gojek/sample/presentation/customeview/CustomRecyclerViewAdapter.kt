package com.cavista.sample.presentation.customeview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView

abstract class CustomRecyclerViewAdapter<T,
        in L : IBaseRecyclerListener,
        VH : BaseViewHolder<T, L>>
    (private var context: Context) : RecyclerView.Adapter<VH>() {

    private var list: ArrayList<T> = ArrayList()
    private lateinit var listener: L
    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    private var selectedPositionsMap = HashMap<String, Boolean>() // position=selected

    fun select(id: String) {
        selectedPositionsMap[id] = true
    }

    fun deSelect(id: String) {
        selectedPositionsMap[id] = false
    }

    fun setItems(items: List<T>) {
        this.list.clear()
        this.list.addAll(items)
        notifyDataSetChanged()
    }

    fun getList(): List<T> {
        return this.list
    }

    fun getItemAt(position: Int): T {
        return list[position]
    }

    fun clearAll() {
        this.list.clear()
        this.selectedPositionsMap.clear()
        notifyDataSetChanged()
    }

    @NonNull
    protected fun inflate(@LayoutRes layout: Int, @Nullable parent: ViewGroup, attachToRoot: Boolean): View {
        return layoutInflater.inflate(layout, parent, attachToRoot)
    }

    @NonNull
    protected fun inflate(@LayoutRes layout: Int, parent: ViewGroup?): View {
        return inflate(layout, parent!!, false)
    }

    fun setListener(listener: L) {
        this.listener = listener
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = list[position]
        holder.onBind(item, listener, position)
    }

    abstract fun getId(position: Int): String

}