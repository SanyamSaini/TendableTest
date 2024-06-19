package com.example.tendable.test.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("error")
    var error : String = ""
)