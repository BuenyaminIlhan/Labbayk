package de.bueny.labbayk.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface QuranApiInterfaceGerman {
    @GET("editions/deu-asfbubenheimand-la.json")
    suspend fun getChapter(): ChapterResponseGerman
}


object QuranApiGerman {
    const val BASE_URL = "https://cdn.jsdelivr.net/gh/fawazahmed0/quran-api@1/"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val logger =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    private val client = OkHttpClient.Builder().addInterceptor(logger).build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    val service: QuranApiInterfaceGerman by lazy { retrofit.create(QuranApiInterfaceGerman::class.java) }
}
