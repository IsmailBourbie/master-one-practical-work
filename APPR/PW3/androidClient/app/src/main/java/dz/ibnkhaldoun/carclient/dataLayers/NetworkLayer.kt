package dz.ibnkhaldoun.carclient.dataLayers

import dz.ibnkhaldoun.carclient.webServices.StudentsService

class NetworkLayer {

    private val webService: StudentsService = StudentsService.retrofit
        .create(StudentsService::class.java)

    suspend fun loginClient(email: String, password: String) =
        webService.loginAsync(email, password).await()

    suspend fun signUpClient(name: String, email: String, password: String) =
        webService.signUpClientAsync(name, email, password).await()
}