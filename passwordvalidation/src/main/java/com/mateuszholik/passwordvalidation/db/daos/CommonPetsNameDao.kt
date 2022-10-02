package com.mateuszholik.passwordvalidation.db.daos

import androidx.room.Dao
import androidx.room.Query
import com.mateuszholik.passwordvalidation.db.models.CommonPetName
import io.reactivex.rxjava3.core.Single

@Dao
internal interface CommonPetsNameDao {

    @Query("select * from most_common_pets_names where " +
            ":name like \"%\" || name || \"%\" " +
            "or name like \"%\" || :name || \"%\" " +
            "or name = :name"
    )
    fun getMatchingPetNames(name: String): Single<List<CommonPetName>>
}