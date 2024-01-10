package com.example.rickandmortyapi.data.remote

import com.example.rickandmortyapi.data.model.RemoteModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://rickandmortyapi.com/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface RAMAPIService {
    // MAX NUMBER OF CHARACTERS IS 826
    @GET("character/{randomNumber}/")
    suspend fun getRandomImage(@Path("randomNumber") randomNumber: Int): RemoteModel

    @GET("character/{imageIDs}/")
    suspend fun getImageList(@Path("imageIDs") imageIDs: String): List<RemoteModel>
}

object RAMAPI {
    val retrofitService: RAMAPIService by lazy { retrofit.create(RAMAPIService::class.java) }
}