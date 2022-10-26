package com.example.a20221025_ivanwilliams_nycschools.repository

import com.example.a20221025_ivanwilliams_nycschools.data.api.NYCSchoolsInterface
import com.example.a20221025_ivanwilliams_nycschools.repository.state.UIState
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val network: NYCSchoolsInterface
    ) : Repository {

    override fun schoolList(): Single<UIState> {
        return network.getListSchools()
            .map { nycSchoolResponses ->
                UIState.SchoolResponse(nycSchoolResponses)
            }
    }

    override fun getSchoolDetails(dbn: String?): Single<UIState> {
        return network.getListSAT()
            .map { nycSchoolSats ->
                nycSchoolSats.firstOrNull { it.dbn.equals(dbn) }?.let {
                    UIState.SATResponse(it)
                } ?: UIState.Error(Throwable("NO SCORES"))
            }
    }
}