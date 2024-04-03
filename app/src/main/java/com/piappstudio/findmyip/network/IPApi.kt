package com.piappstudio.findmyip.network

import com.piappstudio.findmyip.data.IpDetail
import retrofit2.Response
import retrofit2.http.GET

interface IPApi {
    @GET("json")
    suspend fun getIpAddress():Response<IpDetail?>
}