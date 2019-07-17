package rmi

import java.rmi.Remote
import java.rmi.RemoteException

interface RemoteInterface : Remote {

    @Throws(RemoteException::class)
    fun printMessage()
}