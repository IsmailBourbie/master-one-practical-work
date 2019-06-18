package dz.ibnkhaldoun.carclient.webServices

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dz.ibnkhaldoun.carclient.models.ResponseLogin
import dz.ibnkhaldoun.carclient.models.ResponseSignUp
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface StudentsService {

    companion object {
        private const val BASE_URL = "https://192.168.46.4/project"
        private const val PATH = "main.php"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(createClient())
            .build()

        private fun createClient(): OkHttpClient {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val builder = OkHttpClient.Builder()
            builder.addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
            return builder.build()
        }
    }

    @GET(PATH)
    fun loginAsync(
        @Query("email") email: String,
        @Query("password") password: String
    ): Deferred<ResponseLogin>

    @POST(PATH)
    @FormUrlEncoded
    fun signUpClientAsync(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Deferred<ResponseSignUp>

}