package me.kubister11.bytepanel.shared.proxy

import java.io.IOException
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.Executors

class TPCProxy(
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

            val clientToServer = Thread {
                try {
                    clientSocket.getInputStream().copyTo(remoteSocket.getOutputStream())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            val serverToClient = Thread {
                try {
                    remoteSocket.getInputStream().copyTo(clientSocket.getOutputStream())
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            clientToServer.start()
            serverToClient.start()

            clientToServer.join()
            serverToClient.join()
        } catch (e: IOException) {
           e.printStackTrace()
        } finally {
            try {
                clientSocket.close()
                println("Client disconnected")
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}