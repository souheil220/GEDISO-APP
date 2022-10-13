package com.hasnaoui.geddoc.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hasnaoui.geddoc.RetrofitHelper
import com.hasnaoui.geddoc.data.DocumentApi
import com.hasnaoui.geddoc.models.AutorisationDeSortieModal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AutorisationDeSortieViewModal: ViewModel() {
    var autorisationDeSortieViewModalMutableLiveData: MutableLiveData<AutorisationDeSortieModal> = MutableLiveData<AutorisationDeSortieModal>()

    fun getAutorisationDeSortie(record_id:Int){
        val documentApi = RetrofitHelper.getInstance().create(DocumentApi::class.java)
        documentApi.getAutorisationDeSortie(record_id).enqueue(object : Callback<AutorisationDeSortieModal> {
            override fun onResponse(
                call: Call<AutorisationDeSortieModal>,
                response: Response<AutorisationDeSortieModal>
            ) {
                autorisationDeSortieViewModalMutableLiveData.value = response.body()
            }

            override fun onFailure(call: Call<AutorisationDeSortieModal>, t: Throwable) {
                Log.e("Error","${t.message}")
            }
        })
    }
}