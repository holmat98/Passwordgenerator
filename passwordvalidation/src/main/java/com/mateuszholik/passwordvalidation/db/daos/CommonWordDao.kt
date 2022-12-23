package com.mateuszholik.passwordvalidation.db.daos

import androidx.room.Dao
import androidx.room.Query
import com.mateuszholik.passwordvalidation.db.models.CommonWord
import io.reactivex.rxjava3.core.Single

@Dao
internal interface CommonWordDao {

    @Query("select word from most_common_words where " +
            ":word like \"%\" || word || \"%\" " +
            "or word like \"%\" || :word || \"%\" " +
            "or word = :word"
    )
    fun getMatchingWords(word: String): Single<List<String>>
}