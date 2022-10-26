package com.example.a20221025_ivanwilliams_nycschools.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a20221025_ivanwilliams_nycschools.repository.Repository
import com.example.a20221025_ivanwilliams_nycschools.repository.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SchoolViewModel @Inject constructor(
    private var repository: Repository,
    private val compositeDisposable: CompositeDisposable
    ) : ViewModel() {

    private val _schoolState: MutableLiveData<UIState> = MutableLiveData<UIState>()
    val schoolState: LiveData<UIState>
        get() = _schoolState

    private val _schoolSATState: MutableLiveData<UIState> = MutableLiveData<UIState>()
    val schoolSATState: LiveData<UIState>
        get() = _schoolSATState

    init {
        init()
    }

    private fun init() {
        repository.schoolList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _schoolState.value = it },
                { _schoolState.value = UIState.Error(it) }
            )
            .also { compositeDisposable.add(it) }
    }

    fun getSatDetails(dbn: String?) {
        repository.getSchoolDetails(dbn)
            .subscribeOn(
                Schedulers.io()
            )
            .observeOn(
                AndroidSchedulers.mainThread()
            )
            .subscribe(
                { _schoolSATState.value = it },
                { _schoolSATState.value = UIState.Error(it) }
            )
            .also { compositeDisposable.add(it) }
    }


}