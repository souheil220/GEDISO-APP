package com.hasnaoui.geddoc.data

import com.hasnaoui.geddoc.models.AutorisationDeSortieModal
import com.hasnaoui.geddoc.models.DemandeDApprovisionnementModal
import com.hasnaoui.geddoc.models.DemandeModal
import com.hasnaoui.geddoc.models.RequisitionsHeureModal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DocumentApi {
    @GET("/")
    fun getDocument(@Query("user_id") user_id: Int,@Query("document_id") document_id: Int) : Call<ArrayList<DemandeModal>>

    @GET("/get_heure_sup")
    fun getRequisitionHeure(@Query("record_id") record_id: Int) : Call<RequisitionsHeureModal>

    @GET("/get_autorisation_de_sortie")
    fun getAutorisationDeSortie(@Query("record_id") record_id: Int) : Call<AutorisationDeSortieModal>

    @GET("/get_demande_dappro")
    fun getDemadeDapro(@Query("record_id") record_id: Int) : Call<DemandeDApprovisionnementModal>
}