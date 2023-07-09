package com.mateuszholik.data.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.mateuszholik.data.db.models.PasswordEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
internal interface PasswordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndGetId(passwordEntity: PasswordEntity): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(passwordEntity: PasswordEntity): Completable

    @Update
    fun update(passwordEntity: PasswordEntity): Completable

    @Delete
    fun delete(passwordEntity: PasswordEntity)
}
