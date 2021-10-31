package com.kosalaamInc.kosalaam.util


import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

class SocketApplication {
    companion object {
        private lateinit var socket : Socket
        fun get(): Socket {
            try {

                socket = IO.socket("http://52.79.248.96:8080/ws/chat:8080")
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
            return socket
        }
    }
}