package com.hasnaoui.geddoc.models

data class RequisitionsHeureModal(
    val id: Int,
    val record_id: Int,
    val user: String,
    val cause: String,
    val date_du_doc: String,
    val date_heure_supp: String,
    val heure_debut: String,
    val heure_fin: String,
    val heure_travaillé: String
)







