package com.hasnaoui.geddoc.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hasnaoui.geddoc.RetrofitHelper
import com.hasnaoui.geddoc.data.DocumentApi
import com.hasnaoui.geddoc.models.RequisitionsHeureModal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequisitionHeureViewModal : ViewModel(){
    var requisitionHeureViewModalMutableLiveData:MutableLiveData<RequisitionsHeureModal> = MutableLiveData<RequisitionsHeureModal>()

    fun getRequisitionHeure(record_id:Int){

        val documentApi = RetrofitHelper.getInstance().create(DocumentApi::class.java)
        documentApi.getRequisitionHeure(record_id).enqueue(object :
            Callback<RequisitionsHeureModal> {
            override fun onResponse(
                call: Call<RequisitionsHeureModal>,
                response: Response<RequisitionsHeureModal>
            ) {
                requisitionHeureViewModalMutableLiveData.value = response.body()
            }

            override fun onFailure(call: Call<RequisitionsHeureModal>, t: Throwable) {
                Log.e("Error","${t.message}")
            }
        })
    }
}