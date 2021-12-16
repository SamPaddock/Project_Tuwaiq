package com.saraha.paws.Model.Facts

import androidx.room.Entity
import java.io.Serializable

@Entity
data class Link(
    val active: Boolean,
    val label: String,
    val url: String?
): Serializable