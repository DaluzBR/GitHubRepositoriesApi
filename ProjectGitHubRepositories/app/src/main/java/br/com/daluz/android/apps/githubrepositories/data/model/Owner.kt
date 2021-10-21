package br.com.daluz.android.apps.githubrepositories.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Owner (
    @SerializedName(value = "login")
    val login: String,
    @SerializedName(value = "avatar_url")
    val avatarURL: String
): Parcelable