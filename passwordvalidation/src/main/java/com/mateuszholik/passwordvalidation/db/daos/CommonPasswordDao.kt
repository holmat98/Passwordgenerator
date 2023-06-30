package com.mateuszholik.passwordvalidation.db.daos

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
internal interface CommonPasswordDao {

    @Query(
        """
            SELECT CASE WHEN EXISTS (
                SELECT password FROM most_used_passwords WHERE
                LENGTH(:password) > 1 AND
                (:password LIKE "%" || password || "%"
                OR password LIKE "%" || :password || "%")
            )
            THEN CAST(0 AS BIT)
            ELSE CAST(1 AS BIT) END
        """
    )
    fun getMatchingPasswords(password: String): Single<Boolean>
}
