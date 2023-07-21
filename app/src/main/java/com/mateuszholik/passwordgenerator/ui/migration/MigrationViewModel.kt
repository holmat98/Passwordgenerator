package com.mateuszholik.passwordgenerator.ui.migration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mateuszholik.domain.usecase.GetIfShouldMigrateDataUseCase
import com.mateuszholik.domain.usecase.MigrateDataToTheCorrectStateUseCase
import com.mateuszholik.domain.usecase.SaveMigrationStateUseCase
import com.mateuszholik.passwordgenerator.extensions.addTo
import com.mateuszholik.passwordgenerator.extensions.subscribeWithObserveOnMainThread
import com.mateuszholik.passwordgenerator.ui.base.BaseViewModel
import io.reactivex.rxjava3.core.Completable
import timber.log.Timber

class MigrationViewModel(
    getMigrationStateUseCase: GetIfShouldMigrateDataUseCase,
    private val migrateDataToTheCorrectStateUseCase: MigrateDataToTheCorrectStateUseCase,
    private val saveMigrationStateUseCase: SaveMigrationStateUseCase,
) : BaseViewModel() {

    private val _migrationCompleted = MutableLiveData(false)
    val migrationCompleted: LiveData<Boolean>
        get() = _migrationCompleted

    init {
        getMigrationStateUseCase()
            .flatMapCompletable { dataMigrated ->
                if (dataMigrated) {
                    Completable.complete()
                } else {
                    migrateDataToTheCorrectStateUseCase()
                        .andThen(saveMigrationStateUseCase())
                }
            }.subscribeWithObserveOnMainThread(
                doOnSuccess = {
                    _migrationCompleted.postValue(true)
                },
                doOnError = {
                    Timber.d(it, "Error while migrating data")
                    _migrationCompleted.postValue(true)
                }
            ).addTo(compositeDisposable)
    }
}
