package com.mateuszholik.data.db.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "passwords")
internal data class PasswordDB(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val platformName: String,
    val password: String,
    val expiringDate: Date
)