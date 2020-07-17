package com.githubopenpulls.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PullInfo(
    val state: String,
    val title: String,
    @SerializedName("created_at") val createdAt: String
) : Serializable {
}