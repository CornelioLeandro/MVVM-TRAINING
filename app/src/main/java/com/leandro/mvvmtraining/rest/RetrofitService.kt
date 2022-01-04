package com.leandro.mvvmtraining.rest

import com.leandro.mvvmtraining.models.Live
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


//Base necessaria para instanciar o retrofit
interface RetrofitService {

    //Path onde estar o json em si
    @GET("lista-lives.json")
    fun getAllLives(): Call<List<Live>>


    companion object{

        private val retrofitService: RetrofitService by lazy{
            val retrofit = Retrofit.Builder()
                // URL BASE para todos os outros PATH
                .baseUrl("https://d3c0cr0sze1oo6.cloudfront.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(RetrofitService::class.java)
        }
        //RETORNA A INSTANCIA DO RETROFIT Unica
        fun getInstance() : RetrofitService{
            return  retrofitService
        }
    }



}