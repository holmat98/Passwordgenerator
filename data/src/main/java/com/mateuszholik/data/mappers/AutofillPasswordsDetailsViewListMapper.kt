package com.mateuszholik.data.mappers

import com.mateuszholik.data.db.models.views.AutofillPasswordDetailsView
import com.mateuszholik.data.repositories.models.AutofillPasswordDetails

internal interface AutofillPasswordsDetailsViewListMapper :
    ListMapper<AutofillPasswordDetailsView, AutofillPasswordDetails>

internal class AutofillPasswordsDetailsViewListMapperImpl(
    private val autofillPasswordDetailsViewMapper: AutofillPasswordDetailsViewMapper,
) : AutofillPasswordsDetailsViewListMapper {

    override fun map(param: List<AutofillPasswordDetailsView>): List<AutofillPasswordDetails> =
        param.map { autofillPasswordDetailsViewMapper.map(it) }
}
