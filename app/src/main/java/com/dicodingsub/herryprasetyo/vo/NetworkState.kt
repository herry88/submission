package com.dicodingsub.herryprasetyo.vo

data class NetworkState(val status: Status = Status.RUNNING, val msg: String) {


    enum class Status {
        FAILED, RUNNING, SUCCESS, EMPTY
    }

    companion object {
        val LOADING = NetworkState(
            Status.RUNNING,
            "Success"
        )
        val LOADED = NetworkState(
            Status.SUCCESS,
            "Running"
        )
        val EMPTY = NetworkState(
            Status.EMPTY,
            ""
        )
    }
}
