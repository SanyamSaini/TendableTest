package com.example.tendable.test.repository

import android.util.Log
import com.example.tendable.test.api.ApiResult
import com.example.tendable.test.api.Network
import com.example.tendable.test.model.ApiResponse
import com.example.tendable.test.model.LoginModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class LoginRepository {

    companion object {
        private const val TAG = "LoginRepository"
    }

    suspend fun loginUser(loginModel: LoginModel): String {
        return withContext(Dispatchers.IO) {
            Log.d(TAG, "loginUser : $loginModel")

            return@withContext when (val loginRes = loginUserImpl(loginModel)) {
                is ApiResult.Success -> {
                    ""
                }
                is ApiResult.Error -> {
                    if(loginRes.apiResponse?.error == null) {
                        "Something Went Wrong."
                    } else {
                        loginRes.apiResponse.error
                    }
                }
            }
        }
    }

    private suspend fun loginUserImpl(loginModel: LoginModel): ApiResult<ApiResponse> {

        val defaultResponse = Network.network.loginUser(loginModel)
        Log.d(TAG, "defaultResponse : $defaultResponse")

        return try {
            if(defaultResponse.error.isEmpty()) {
                ApiResult.Success(data = defaultResponse)
            } else {
                ApiResult.Error(Exception("API Error"), defaultResponse)
            }
        } catch (e: HttpException) {
            //handles exception with the request
            Log.d(TAG, "HttpException : ${e.printStackTrace()}")
            ApiResult.Error(exception = e)
        } catch (e: IOException) {
            //handles no internet exception
            Log.d(TAG, "IOException : $e")
            ApiResult.Error(exception = e)
        } catch (e : Exception) {
            Log.d(TAG, "Exception : $e")
            ApiResult.Error(exception = e)
        }
    }

    suspend fun registerUser(loginModel: LoginModel): String {
        return withContext(Dispatchers.IO) {

            return@withContext when (val res = registerUserImpl(loginModel)) {
                is ApiResult.Success -> {
                    ""
                }
                is ApiResult.Error -> {
                    if(res.apiResponse?.error == null) {
                        "Something Went Wrong."
                    } else {
                        res.apiResponse.error
                    }
                }
            }
        }
    }

    private suspend fun registerUserImpl(loginModel: LoginModel): ApiResult<ApiResponse> {
        return try {
            val defaultResponse = Network.network.registerUser(loginModel)

            if(defaultResponse.error.isEmpty()) {
                ApiResult.Success(data = defaultResponse)
            } else {
                ApiResult.Error(Exception(), defaultResponse)
            }
        } catch (e: HttpException) {
            //handles exception with the request
            ApiResult.Error(exception = e)
        } catch (e: IOException) {
            //handles no internet exception
            ApiResult.Error(exception = e)
        } catch (e : Exception) {
            Log.d(TAG, "Exception : $e")
            ApiResult.Error(exception = e)
        }
    }
}