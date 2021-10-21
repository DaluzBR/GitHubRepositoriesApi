package br.com.daluz.android.apps.githubrepositories.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Repo (
    @SerializedName(value = "id")
    val id: Long,
    @SerializedName(value = "owner")
    val owner: Owner,
    @SerializedName(value = "name")
    val name: String,
    @SerializedName(value = "description")
    val description: String,
    @SerializedName(value = "language")
    val language: String,
    @SerializedName(value = "stargazers_count")
    val stargazersCount: Long,
    @SerializedName(value = "watchers_count")
    val watchersCount: Long,
    @SerializedName(value = "html_url")
    val htmlURL: String
): Parcelable