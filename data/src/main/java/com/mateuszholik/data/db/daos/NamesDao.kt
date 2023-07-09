package com.mateuszholik.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.mateuszholik.data.db.models.NamesEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
internal interface NamesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndGetId(namesEntity: NamesEntity): Single<Long>

    @Update
    fun update(namesEntity: NamesEntity): Completable
}
