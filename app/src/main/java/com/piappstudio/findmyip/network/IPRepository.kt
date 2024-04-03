package com.piappstudio.findmyip.network

import android.content.Context
import com.piappstudio.findmyip.data.IpDetail
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IPRepository @Inject constructor(val ipApi: IPApi, @ApplicationContext val context: Context) {

    suspend fun getIPAddress(): Flow<Resource<IpDetail?>> {
       return makeSafeApiCall {
           withContext(Dispatchers.IO) {
               ipApi.getIpAddress()
           }
        }
    }

    private suspend fun <T> makeSafeApiCall(api: suspend () -> Response<T?>) = flow {
        emit(Resource.loading())
        //if (context.isNetworkAvailable()) {
            val response = api.invoke()
            if (response.isSuccessful) {
                ///
                emit(Resource.success(response.body()))
            } else {
                emit(Resource.error(null, error = PIError(response.code())))
            }
    }.catch {
        emit(Resource.error(error = PIError(code = 10001)))
    }
}