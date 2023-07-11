package com.mateuszholik.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mateuszholik.data.db.models.entities.NamesEntity
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
internal interface NamesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndGetId(namesEntity: NamesEntity): Single<Long>

    @Query(
        """
            UPDATE names SET
                name = :name,
                name_iv = :nameIv,
                website = :website,
                website_iv = :websiteIv
            WHERE id = :id
        """
    )
    fun update(
        id: Long,
        name: ByteArray,
        nameIv: ByteArray,
        website: ByteArray?,
        websiteIv: ByteArray?
    ): Completable
}
