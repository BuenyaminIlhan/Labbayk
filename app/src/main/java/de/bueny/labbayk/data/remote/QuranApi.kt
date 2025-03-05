package de.bueny.labbayk.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


//https://quranapi.pages.dev/api/surah.json

interface QuranApiInterface {

    @GET("api/{surahNumber}.json")
    suspend fun getChapter(@Path("surahNumber") surahNumber: Int): ChapterResponse

    @GET("api/surah.json")
    suspend fun  getQuranList(): List<ChapterListResponse>

//    @GET("chapter/{surahNumber}/audios")
//    suspend fun getChapterAudios(@Path("surahNumber") surahNumber: Int): List<ChapterAudioResponse>

}

object QuranApi {
    private const val BASE_URL = "https://quranapi.pages.dev/"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val logger =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
    private val client = OkHttpClient.Builder().addInterceptor(logger).build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    val service: QuranApiInterface by lazy { retrofit.create(QuranApiInterface::class.java) }

}