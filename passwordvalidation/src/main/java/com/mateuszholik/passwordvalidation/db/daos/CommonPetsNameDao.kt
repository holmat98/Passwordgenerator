package com.mateuszholik.passwordvalidation.db.daos

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
internal interface CommonPetsNameDao {

    @Query(
        """
            SELECT CASE WHEN EXISTS (
                SELECT name FROM most_common_pets_names WHERE
                LENGTH(:name) > 1 AND
                (:name LIKE "%" || name || "%"
                OR name LIKE "%" || :name || "%")
            )
            THEN CAST(0 AS BIT)
            ELSE CAST(1 AS BIT) END
        """
    )
    fun getMatchingPetNames(name: String): Single<Boolean>
}
