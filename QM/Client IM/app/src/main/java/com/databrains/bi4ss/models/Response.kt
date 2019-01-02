package com.databrains.bi4ss.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Response(@SerializedName("state") @Expose
                    val state: Boolean)