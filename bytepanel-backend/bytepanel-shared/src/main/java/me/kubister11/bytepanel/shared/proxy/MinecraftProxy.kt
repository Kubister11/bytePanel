package me.kubister11.bytepanel.shared.proxy

import java.io.IOException
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.Executors

class MinecraftProxy(
    port: Int,
    endpoint: String,
    endpointPort: Int
) : TPCProxy(port, endpoint, endpointPort) {

    override fun prepareRemoteSocket(clientSocket: Socket, remoteSocket: Socket) {
        try {
            val clientIp = clientSocket.inetAddress.hostAddress
            val clientPort = clientSocket.port
            val remoteIp = remoteSocket.inetAddress.hostAddress
            val remotePort = remoteSocket.port

            val proxyHeader = "PROXY TCP4 $clientIp $remoteIp $clientPort $remotePort\r\n"
            remoteSocket.getOutputStream().write(proxyHeader.toByteArray())
            remoteSocket.getOutputStream().flush()
        } catch (ioException: IOException) {
            println("Error while sending PROXY header: ${ioException.message}")
        }
    }

}