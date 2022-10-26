package com.example.a20221025_ivanwilliams_nycschools.repository

import com.example.a20221025_ivanwilliams_nycschools.repository.state.UIState
import io.reactivex.rxjava3.core.Single

interface Repository {
    fun schoolList(): Single<UIState>
    fun getSchoolDetails(dbn: String?): Single<UIState>
}