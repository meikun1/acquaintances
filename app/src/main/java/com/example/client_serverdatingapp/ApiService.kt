package com.example.client_serverdatingapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Call

// URL сервера (замени на свой IP, если тестируешь локально)
private const val BASE_URL = "http://192.168.0.106:3000/"  // Для эмулятора

// Модель данных
data class RegisterRequest(val email: String)
data class RegisterResponse(val message: String)

data class VerifyRequest(val email: String, val code: String)
data class VerifyResponse(val message: String, val userId: String)

// Интерфейс API
interface ApiService {
    @POST("register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("verify")
    fun verify(@Body request: VerifyRequest): Call<VerifyResponse>
}

// Настройка Retrofit
object ApiClient {
    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
