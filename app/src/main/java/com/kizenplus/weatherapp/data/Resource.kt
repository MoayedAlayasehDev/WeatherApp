package com.kizenplus.weatherapp.data


class Resource<out T>(val status: ApiStatus, val data: T?) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = ApiStatus.SUCCESS, data = data)

        fun <T> error(data: T?): Resource<T> =
            Resource(ApiStatus.ERROR, data = data)


        fun <T> loading(data: T?): Resource<T> =
            Resource(status = ApiStatus.LOADING, data = data)
    }

}