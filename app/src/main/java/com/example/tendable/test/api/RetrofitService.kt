package com.example.tendable.test.api

import EmptyResponseConverterFactory
import com.example.tendable.test.model.ApiResponse
import com.example.tendable.test.model.InspectionResponse
import com.example.tendable.test.model.LoginModel
import com.example.tendable.test.util.Constants.LOCAL_URL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST


interface NetworkService {

    @Headers("Content-Type: application/json")
    @POST("api/register")
    suspend fun registerUser(
        @Body loginModel: LoginModel
    ) : ApiResponse

    @Headers("Content-Type: application/json")
    @POST("api/login")
    suspend fun loginUser(
        @Body loginModel: LoginModel
    ) : ApiResponse

    @Headers("Content-Type: application/json")
    @GET("api/inspections/start")
    suspend fun startInspection() : InspectionResponse

    @Headers("Content-Type: application/json")
    @POST("api/inspections/submit")
    suspend fun submitInspection(
        @Body inspection : InspectionResponse
    ) : ApiResponse
}


object Network {


    var baseURL = LOCAL_URL

    // Configure retrofit to parse JSON and use coroutines
    private val interceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private var client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val gson = GsonBuilder().create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .addConverterFactory(EmptyResponseConverterFactory(gson))
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build()

    val network = retrofit.create(NetworkService::class.java)

}