package com.hasnaoui.geddoc.models


data class DemandeDApprovisionnementModal(val data: Map<Int, Map<String, String>>)

data class DemandeDapproDetailModel(
    val date_souhaite_de_la_reception: String?,
    val designation_article: String?,
    val la_quantite_demande: Int?,
    val centre_de_cout: String?,
    val um: String?
)

data class DemandeApproOwner(
    val date_de_la_demande: String?,
    val id: Int?,
    val record_id: Int?,
    val user: String?
)