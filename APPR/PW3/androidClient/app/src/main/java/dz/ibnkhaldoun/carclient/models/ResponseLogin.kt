package dz.ibnkhaldoun.carclient.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @Expose @SerializedName("status") val status: Int,
    @Expose @SerializedName("id") val id: String,
    @Expose @SerializedName("name") val name: String
)