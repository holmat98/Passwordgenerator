package com.mateuszholik.domain.usecase

import com.mateuszholik.data.repositories.PasswordsRepository
import com.mateuszholik.domain.models.NewPackage
import com.mateuszholik.domain.usecase.base.CompletableParameterizedUseCase
import io.reactivex.rxjava3.core.Completable

interface UpdatePackageNameUseCase : CompletableParameterizedUseCase<NewPackage>

internal class UpdatePackageNameUseCaseImpl(
    private val passwordsRepository: PasswordsRepository,
) : UpdatePackageNameUseCase {

    override fun invoke(param: NewPackage): Completable =
        passwordsRepository.updatePackageName(
            id = param.passwordId,
            packageName = param.packageName
        )
}
