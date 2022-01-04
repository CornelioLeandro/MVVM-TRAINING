package com.leandro.mvvmtraining.repository

import com.leandro.mvvmtraining.rest.RetrofitService

//Classe que instancia o retrofitService chamando as funções solicitadas no caso o getAllLives
class MainRepository constructor(private val retrofitService: RetrofitService) {

    fun getAllLives() = retrofitService.getAllLives()


}