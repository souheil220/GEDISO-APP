package com.hasnaoui.geddoc.models


data class DemandeModal(
    var deg_documents: ArrayList<ArrayList<Any>>,
    var validation_cycle: ArrayList<ValidationCycle>
)

data class ValidationCycle(
    var name: String? = null,
    var user_id: Int,
    var valide: Any? = null

)

//data class DemandeModal(val attachment_id:ArrayList<Any>,val document_id:ArrayList<Any>, val id:Int, val state:String)