package com.gojek.sample.presentation.utils

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider


class ViewModelFactory @Inject constructor(providerMap: Map<Class<out ViewModel>, Provider<ViewModel>>) :
    ViewModelProvider.Factory {
    val mProviderMap: Map<Class<out ViewModel>, Provider<ViewModel>> = providerMap

    @NonNull
    override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
        return mProviderMap[modelClass]?.get() as T
    }

}