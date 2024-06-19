package com.example.tendable.test.model

import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("email")
    var email : String? = null,
    @SerializedName("password")
    var password : String? = null
)
