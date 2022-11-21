package com.example.komoke.event

class EventApi {
    companion object {
        val BASE_URL = "http://192.168.43.76:8080/databaseKomoke/public/"

        val GET_ALL_URL = BASE_URL + "event/"
        val GET_BY_ID_URL = BASE_URL + "event/"
        val ADD_URL = BASE_URL + "event"
        val UPDATE_URL = BASE_URL + "event/"
        val DELETE_URL = BASE_URL + "event/"
    }
}