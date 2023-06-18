package com.mateuszholik.passwordvalidation.db.daos

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
internal interface CommonWordDao {

    @Query(
        """
            SELECT CASE WHEN EXISTS (
                SELECT word FROM most_common_words WHERE
                LENGTH(:word) > 1 AND
                LENGTH(word) > 2 AND
                (:word LIKE "%" || word || "%"
                OR word LIKE "%" || :word || "%")
            )
            THEN CAST(0 AS BIT)
            ELSE CAST(1 AS BIT) END
        """
    )
    fun getMatchingWords(word: String): Single<Boolean>
}
