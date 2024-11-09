package me.kubister11.bytepanel.shared.proxy

import java.io.IOException
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.Executors

abstract class TPCProxy(
    private val port: Int,
    private val endpoint: String,
    private val endpointPort: Int
) {

    private val executor = Executors.newCachedThreadPool()

    private lateinit var serverSocket: ServerSocket
    private lateinit var proxyThread: Thread

    fun start() {
        this.proxyThread = Thread {
            try {
                this.serverSocket = ServerSocket(port)

                while (true) {
                    val clientSocket = serverSocket.accept()
                    println("New client connected")
                    executor.submit { handleClient(clientSocket) }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        this.proxyThread.start()
    }

    fun stop() {
        this.serverSocket.close()
        this.proxyThread.interrupt()
    }

    private fun handleClient(clientSocket: Socket) {
        try {
            val remoteSocket = Socket(endpoint, endpointPort)

            prepareRemoteSocket(clientSocket, remoteSocket)

            val clientToServer = Thread {
                try {
                    clientSocket.getInputStream().copyTo(remoteSocket.getOutputStream())
                } catch (exception: IOException) {
                    exception.printStackTrace()
                }
            }

            val serverToClient = Thread {
                try {
                    remoteSocket.getInputStream().copyTo(clientSocket.getOutputStream())
                } catch (exception: IOException) {
                    exception.printStackTrace()
                }
            }

            clientToServer.start()
            serverToClient.start()

            clientToServer.join()
            serverToClient.join()
        } catch (exception: IOException) {
           exception.printStackTrace()
        } finally {
            try {
                clientSocket.close()
                println("Client disconnected")
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
    }

    abstract fun prepareRemoteSocket(clientSocket: Socket, remoteSocket: Socket)
}