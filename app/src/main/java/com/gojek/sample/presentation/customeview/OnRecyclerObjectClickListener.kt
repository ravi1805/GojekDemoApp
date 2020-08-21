package com.cavista.sample.presentation.customeview

interface OnRecyclerObjectClickListener<in T> : IBaseRecyclerListener {
    /**
     * Item has been clicked.
     *
     * @param item object associated with the clicked item.
     * @param operationId what operation would like to do Ex: Delete/Detail/Edit
     */
    fun onItemClicked(item: T, position: Int, operationId: Int)

    /**
     * Row has been clicked
     * @param item Object associated with the clicked item.
     * @param position Object position
     */
    fun onRowClicked(item: T, position: Int)

}