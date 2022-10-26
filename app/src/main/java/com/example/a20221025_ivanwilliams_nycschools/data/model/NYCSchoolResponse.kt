package com.example.a20221025_ivanwilliams_nycschools.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NYCSchoolResponse(
        @SerializedName("dbn")
        var dbn: String? = null,
        @SerializedName("school_name")
        var school_name: String? = null,
        @SerializedName("location")
        var location: String? = null,
        @SerializedName("school_email")
        var school_email: String? = null,
        @SerializedName("phone_number")
        var phone_number: String? = null,
        @SerializedName("overview_paragraph")
        val overviewDetails: String? = null,
        @SerializedName("website")
        val website: String? = null
): Serializable