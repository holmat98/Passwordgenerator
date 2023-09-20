package com.mateuszholik.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mateuszholik.data.db.models.PasswordDB
import com.mateuszholik.data.db.models.entities.PasswordEntity
import com.mateuszholik.data.db.models.views.AutofillPasswordDetailsView
import com.mateuszholik.data.db.models.views.PasswordDetailsView
import com.mateuszholik.data.db.models.views.PasswordInfoView
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single

@Dao
internal interface PasswordsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAndGetId(passwordEntity: PasswordEntity): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(passwordEntity: PasswordEntity): Completable

    @Update
    fun update(passwordEntity: PasswordEntity): Completable

    @Query("DELETE FROM new_passwords WHERE id = :passwordId")
    fun delete(passwordId: Long): Completable

    @Query("SELECT * FROM PasswordInfoView")
    fun getAllPasswordsInfo(): Single<List<PasswordInfoView>>

    @Query("SELECT * FROM PasswordDetailsView WHERE id = :id")
    fun getPasswordDetailsFor(id: Long): Maybe<PasswordDetailsView>

    @Query("SELECT platformName, platformNameIv, password, passwordIv FROM PasswordDetailsView")
    fun getPasswords(): Single<List<PasswordDB>>

    @Query("SELECT name_id FROM new_passwords WHERE id = :passwordId")
    fun getNameIdFor(passwordId: Long): Maybe<Long>

    @Query("SELECT * from AutofillPasswordDetailsView")
    fun getAllAutofillPasswordDetails(): Single<List<AutofillPasswordDetailsView>>
}
