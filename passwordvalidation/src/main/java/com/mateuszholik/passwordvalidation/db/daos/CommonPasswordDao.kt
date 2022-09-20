package com.mateuszholik.passwordvalidation.db.daos

import androidx.room.Dao
import androidx.room.Query
import com.mateuszholik.passwordvalidation.db.models.CommonPassword
import io.reactivex.rxjava3.core.Single

@Dao
internal interface CommonPasswordDao {

    @Query("select * from most_used_passwords where " +
            ":password like \"%\" || password || \"%\" " +
            "or password like \"%\" || :password || \"%\" " +
            "or password = :password"
    )
    fun getMatchingPasswords(password: String): Single<List<CommonPassword>>
}