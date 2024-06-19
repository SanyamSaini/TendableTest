import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class CustomCallback<T> : Callback<T> {

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            if (response.body() == null || response.raw().body()?.contentLength() == 0L) {
                // Handle empty response body
                onSuccessEmptyResponse(call)
            } else {
                onSuccess(call, response)
            }
        } else {
            onError(call, response)
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onApiFailure(call, t)
    }

    abstract fun onSuccess(call: Call<T>, response: Response<T>)

    abstract fun onSuccessEmptyResponse(call: Call<T>)

    abstract fun onError(call: Call<T>, response: Response<T>)

    abstract fun onApiFailure(call: Call<T>, t: Throwable)
}
