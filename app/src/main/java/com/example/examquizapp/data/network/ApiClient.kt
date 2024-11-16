package com.example.examquizapp.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    companion object
    {
        val url = "https://opentdb.com/"
        private var retrofit:Retrofit?=null

        fun getApi() : Retrofit
        {
            if(retrofit==null)
            {
                retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(url).build()

            }
            return retrofit!!
        }

    }

}