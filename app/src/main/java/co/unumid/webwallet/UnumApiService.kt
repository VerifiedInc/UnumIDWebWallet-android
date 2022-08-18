package co.unumid.webwallet


import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UnumApiService {
    @POST("/hyperVerge")
    suspend fun postHyperVergeData(@Body request: RequestBody): Response<HyperVergeResponse>
}