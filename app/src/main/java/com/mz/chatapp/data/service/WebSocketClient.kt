package com.mz.chatapp.data.service

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import timber.log.Timber
import javax.inject.Inject

/**
 * This class provides a WebSocket client implementation using the `OkHttpClient` library.
 * It allows for connecting, reconnecting, sending messages, and receiving messages through a `SocketMessageListener`.
 *
 * @param client The OkHttpClient instance to use for creating the WebSocket connection.
 * @param request The Request object representing the WebSocket connection parameters.
 */
class WebSocketClient @Inject constructor(
    private val client: OkHttpClient?, private val request: Request
) {
    private lateinit var webSocket: WebSocket
    private var messageListener: SocketMessageListener? = null
    private var shouldReconnect = true


    fun setListener(listener: SocketMessageListener) {
        this.messageListener = listener
    }
    private fun initWebSocket() {
        webSocket = client!!.newWebSocket(request, SocketListener())
        //this must me done else memory leak will be caused
        // client!!.dispatcher.executorService.shutdown()
    }

    fun connect() {
        Timber.e("connect()")
        shouldReconnect = true
        initWebSocket()
    }

    fun reconnect() {
        Timber.e("reconnect()")
        initWebSocket()
    }

    fun sendMessage(message: String): Boolean {
        Timber.e("sendMessage(  $message  )")
        return webSocket.send(message)
    }

    fun disconnect() {
        if (::webSocket.isInitialized) webSocket.close(1000, "Do not need connection anymore.")
        shouldReconnect = false
    }

    interface SocketMessageListener {
        fun onMessage(text: String)
    }

    private inner class SocketListener : WebSocketListener() {

        override fun onOpen(webSocket: okhttp3.WebSocket, response: Response) {
            super.onOpen(webSocket, response)
            Timber.e("onOpen()")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            messageListener?.onMessage(text)

        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Timber.e("onClosed()")
            if (shouldReconnect) reconnect()
        }

        override fun onFailure(
            webSocket: WebSocket, t: Throwable, response: Response?
        ) {
            super.onFailure(webSocket, t, response)
            Timber.e("onFailure:$t")
            if (shouldReconnect) reconnect()
        }
    }
}