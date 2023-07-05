package com.mateuszholik.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Query
import androidx.room.OnConflictStrategy
import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.db.models.PasswordDB.Companion.COLUMN_ID_NAME
import com.mateuszholik.data.db.models.PasswordDB.Companion.COLUMN_PASSWORD_SCORE_NAME
import com.mateuszholik.data.db.models.PasswordDB.Companion.COLUMN_PLATFORM_NAME
import com.mateuszholik.data.db.models.PasswordDB.Companion.TABLE_NAME
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
internal interface PasswordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndGetId(passwordDB: PasswordDB): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPasswords(passwords: List<PasswordDB>): Completable

    @Update
    fun update(passwordDB: PasswordDB): Completable

    @Query("select * from passwords where id = :passwordId")
    fun getPassword(passwordId: Long): Maybe<PasswordDB>

    @Query("select * from passwords")
    fun getAllPasswords(): Single<List<PasswordDB>>

    @Query("delete from passwords where id = :passwordId")
    fun deletePassword(passwordId: Long): Completable

    /**
     * This method is supposed to be used only for updating the database to the correct state
     * after migrating from version 2 to 3
     */
    @Query(
        """
            UPDATE $TABLE_NAME SET
            $COLUMN_PLATFORM_NAME = :platform
            $COLUMN_PASSWORD_SCORE_NAME = :passwordScore
            WHERE $COLUMN_ID_NAME = :id
        """
    )
    fun updatePlatformAndPasswordScoreFor(
        id: Long,
        platform: String,
        passwordScore: Int,
    ): Completable
}
