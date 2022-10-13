package com.hasnaoui.geddoc.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hasnaoui.geddoc.RetrofitHelper
import com.hasnaoui.geddoc.data.DocumentApi
import com.hasnaoui.geddoc.models.DemandeModal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LesDemandesViewModal: ViewModel() {
    var demandeListMutableLiveData:MutableLiveData<ArrayList<DemandeModal>> = MutableLiveData<ArrayList<DemandeModal>>()
    var listValidateur:ArrayList<Int> = ArrayList<Int>()
    var listValidateurLiveData:MutableLiveData<ArrayList<Int>> =  MutableLiveData<ArrayList<Int>>()
    fun getDemande(document_id:Int){
        val documentApi = RetrofitHelper.getInstance().create(DocumentApi::class.java)
        documentApi.getDocument(2031,document_id).enqueue(object :Callback<ArrayList<DemandeModal>>{
            override fun onResponse(
                call: Call<ArrayList<DemandeModal>>,
                response: Response<ArrayList<DemandeModal>>
            ) {
                Log.e("response.body()",response.body().toString())
                response.body()?.get(0)?.validation_cycle?.forEach {
                    listValidateur.add(it.user_id)
                }
                listValidateurLiveData.value = listValidateur
              demandeListMutableLiveData.value = response.body()
            }

            override fun onFailure(call: Call<ArrayList<DemandeModal>>, t: Throwable) {
                Log.e("Error","${t.message}")
            }

        })

    }


}