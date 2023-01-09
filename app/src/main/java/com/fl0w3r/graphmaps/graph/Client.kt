package com.fl0w3r.graphmaps.graph

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.OkHttpClient

object GraphMapClient {
    private lateinit var INSTANCE: ApolloClient

    fun getClient(): ApolloClient {
        if (::INSTANCE.isInitialized) {
            // Return the client instance if it's been initialized already.
            return INSTANCE
        }

        val okHttpClient = OkHttpClient.Builder()
            .build()

        INSTANCE = ApolloClient.Builder()
            .serverUrl("https://graphqlzero.almansi.me/api")
            .okHttpClient(okHttpClient)
            .build()

        return INSTANCE
    }
}
