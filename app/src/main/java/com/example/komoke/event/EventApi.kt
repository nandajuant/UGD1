package com.example.komoke.event

class EventApi {
    companion object {
        val BASE_URL = "https://6379f42c702b9830b9e35c25.mockapi.io/"

        val GET_ALL_URL = BASE_URL + "event/"
        val GET_BY_ID_URL = BASE_URL + "event/"
        val ADD_URL = BASE_URL + "event"
        val UPDATE_URL = BASE_URL + "event/"
        val DELETE_URL = BASE_URL + "event/"
    }
}