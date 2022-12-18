package com.mateuszholik.passwordgenerator.ui.transfer.models

enum class PasswordTransferFlow {

    CHOOSE_TRANSFER_TYPE,
    CHOOSE_TRANSFER_ENCRYPTION_TYPE,
    PROVIDE_ENCRYPTION_KEY,
    CHOOSE_IMPORT_FILE,
    FINALIZE
}