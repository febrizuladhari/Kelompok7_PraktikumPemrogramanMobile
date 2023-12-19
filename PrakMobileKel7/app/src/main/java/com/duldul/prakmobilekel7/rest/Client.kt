package com.duldul.prakmobilekel7.rest

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client {

    private var service: Service? = null

    fun getApiClient(): Service? {
        if (service == null) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
            val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://mobile.jo766hi.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
            service = retrofit.create(Service::class.java)
        }
        return service
    }

}