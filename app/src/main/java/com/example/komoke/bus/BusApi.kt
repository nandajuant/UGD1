package com.example.komoke.bus

class BusApi {
    companion object {
        val BASE_URL = "https://6379f42c702b9830b9e35c25.mockapi.io/"

        val GET_ALL_URL = BASE_URL + "bus/"
        val GET_BY_ID_URL = BASE_URL + "bus/"
        val ADD_URL = BASE_URL + "bus"
        val UPDATE_URL = BASE_URL + "bus/"
        val DELETE_URL = BASE_URL + "bus/"
    }
}