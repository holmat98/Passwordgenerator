package com.mateuszholik.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mateuszholik.data.db.models.entities.PasswordEntity
import com.mateuszholik.data.db.models.views.PasswordInfoView
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

    @Query("DELETE FROM new_passwords WHERE id = :passwordId")
    fun delete(passwordId: Long): Completable

    @Query("SELECT * FROM PasswordInfoView")
    fun getAllPasswordsInfo(): Single<List<PasswordInfoView>>
}
