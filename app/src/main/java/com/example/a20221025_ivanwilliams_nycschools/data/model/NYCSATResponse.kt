package com.example.a20221025_ivanwilliams_nycschools.data.model

import com.google.gson.annotations.SerializedName

data class NYCSATResponse(
        @SerializedName("dbn")
        var dbn: String? = null,
        @SerializedName("school_name")
        var school_name: String? = null,
        @SerializedName("num_of_sat_test_takers")
        var num_of_sat_test_takers: String? = null,
        @SerializedName("sat_critical_reading_avg_score")
        var sat_critical_reading_avg_score: String? = null,
        @SerializedName("sat_math_avg_score")
        var sat_math_avg_score: String? = null,
        @SerializedName("sat_writing_avg_score")
        var sat_writing_avg_score: String? = null
)