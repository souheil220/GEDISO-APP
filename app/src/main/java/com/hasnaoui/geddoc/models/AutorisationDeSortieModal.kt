package com.hasnaoui.geddoc.models

data class AutorisationDeSortieModal(val id: Int,
                                val record_id: Int,
                                val user: String,
                                val cause: String,
                                val date_du_doc: String,
                                val date_de_retour: String,
                                val date_de_sortie: String,
                                val heure_de_sortie: String,
                                val heure_de_retour: String,
                                val la_durée_dabsence: String
                                ) {
}