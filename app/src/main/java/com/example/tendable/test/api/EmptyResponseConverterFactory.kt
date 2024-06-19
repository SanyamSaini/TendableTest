import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type

class EmptyResponseConverterFactory(private val gson: Gson) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val adapter: TypeAdapter<*> = gson.getAdapter(TypeToken.get(type))
        return EmptyResponseBodyConverter(adapter)
    }

    private class EmptyResponseBodyConverter<T>(private val adapter: TypeAdapter<T>) :
        Converter<ResponseBody, T> {
        @Throws(IOException::class)
        override fun convert(value: ResponseBody): T? {
            val bodyString = value.string()
            return if (bodyString.isEmpty()) {
                // Return an instance with default values (if any)
                adapter.fromJson("{}")
            } else {
                adapter.fromJson(bodyString)
            }
        }
    }
}