package com.example.komoke.register

class AccountApi {
    companion object {
        val BASE_URL = "https://6379f42c702b9830b9e35c25.mockapi.io/"

        val GET_ALL_URL = BASE_URL + "account/"
        val GET_BY_ID_URL = BASE_URL + "account/"
        val ADD_URL = BASE_URL + "account"
        val UPDATE_URL = BASE_URL + "account/"
        val DELETE_URL = BASE_URL + "account/"
    }
}