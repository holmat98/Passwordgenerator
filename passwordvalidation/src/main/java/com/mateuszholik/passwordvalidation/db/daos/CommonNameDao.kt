package com.mateuszholik.passwordvalidation.db.daos

import androidx.room.Dao
import androidx.room.Query
import com.mateuszholik.passwordvalidation.db.models.CommonName
import io.reactivex.rxjava3.core.Single

@Dao
internal interface CommonNameDao {

    @Query("select name from most_common_names where " +
            ":name like \"%\" || name || \"%\" " +
            "or name like \"%\" || :name || \"%\" " +
            "or name = :name"
    )
    fun getMatchingNames(name: String): Single<List<String>>
}