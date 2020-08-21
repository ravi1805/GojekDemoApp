package com.cavista.sample.presentation.customeview

import android.view.View
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<in T, in L : IBaseRecyclerListener>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /**
     * Bind data to the item and set listener if needed.
     *
     * @param item     object, associated with the item.
     * @param listener listener a listener [IBaseRecyclerListener] which has to b set at the item (if not `null`).
     */
    abstract fun onBind(item: T, @Nullable listener: L, position: Int)

}