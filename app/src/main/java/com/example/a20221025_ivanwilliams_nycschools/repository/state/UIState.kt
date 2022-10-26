package com.example.a20221025_ivanwilliams_nycschools.repository.state

import com.example.a20221025_ivanwilliams_nycschools.data.model.NYCSATResponse
import com.example.a20221025_ivanwilliams_nycschools.data.model.NYCSchoolResponse

sealed class UIState {
    data class SchoolResponse (val  data:List<NYCSchoolResponse>): UIState()
    data class SATResponse ( val data: NYCSATResponse) : UIState()
    data class Error(val error: Throwable) : UIState()
}