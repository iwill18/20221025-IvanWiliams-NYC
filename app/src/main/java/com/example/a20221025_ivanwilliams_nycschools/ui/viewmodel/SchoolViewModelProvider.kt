package com.example.a20221025_ivanwilliams_nycschools.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a20221025_ivanwilliams_nycschools.repository.Repository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class SchoolViewModelProvider @Inject constructor(
    private val repository: Repository,
    private val compositeDisposable: CompositeDisposable
    ) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SchoolViewModel(repository, compositeDisposable) as T
    }
}