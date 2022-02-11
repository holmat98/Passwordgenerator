package com.mateuszholik.passwordgenerator.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwords_Table")
data class Password(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var platformName: String,
    var password: String,
    var passwordIv: String
)