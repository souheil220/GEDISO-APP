package com.hasnaoui.geddoc.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hasnaoui.geddoc.RetrofitHelper
import com.hasnaoui.geddoc.data.DocumentApi
import com.hasnaoui.geddoc.models.DemandeApproOwner
import com.hasnaoui.geddoc.models.DemandeDApprovisionnementModal
import com.hasnaoui.geddoc.models.DemandeDapproDetailModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DemandeDApprovisionnementViewModal : ViewModel() {
    var demandedaproListMutableLiveData: MutableLiveData<ArrayList<DemandeDapproDetailModel>> =
        MutableLiveData<ArrayList<DemandeDapproDetailModel>>()
    var demandedaproOwnerMutableLiveData: MutableLiveData<DemandeApproOwner> =
    MutableLiveData<DemandeApproOwner>()
    var arrayListDetailDemandeAppro: ArrayList<DemandeDapproDetailModel> =
        ArrayList()
    lateinit var demandeApproOwner: DemandeApproOwner
    fun getDemandeDApro(record_id: Int) {
        val documentApi = RetrofitHelper.getInstance().create(DocumentApi::class.java)
        documentApi.getDemadeDapro(record_id)
            .enqueue(object : Callback<DemandeDApprovisionnementModal> {
                override fun onResponse(
                    call: Call<DemandeDApprovisionnementModal>,
                    response: Response<DemandeDApprovisionnementModal>
                ) {


                    if (response.body() != null) {
                        val list: MutableList<Int> = response.body()!!.data.keys.toMutableList()
                        var lastElement: Int = response.body()!!.data.keys.toMutableList().last()

//                        Initialize DemandeAppro Owner
                        demandeApproOwner = DemandeApproOwner(
                            date_de_la_demande = response.body()!!.data[lastElement]?.get("date_de_la_demande"),
                            id = response.body()!!.data[lastElement]?.get("id")!!.toInt(),
                            record_id =  response.body()!!.data[lastElement]?.get("record_id")!!.toInt(),
                            user = response.body()!!.data[lastElement]?.get("user"),

                        )
                        list.removeLast()
                        for (key in list) {
                            arrayListDetailDemandeAppro.add(
                                DemandeDapproDetailModel(
                                    date_souhaite_de_la_reception = response.body()!!.data[key]?.get(
                                        "date_souhaite_de_la_reception"
                                    ),
                                    designation_article = response.body()!!.data[key]?.get("designation_article"),
                                    la_quantite_demande = response.body()!!.data[key]?.get("la_quantite_demande")!!.toDouble()
                                        .toInt(),
                                    centre_de_cout = response.body()!!.data[key]?.get("centre_de_cout"),
                                    um = response.body()!!.data[key]?.get("um")
                                )
                            )

                        }
                    }
                    demandedaproOwnerMutableLiveData.value = demandeApproOwner
                    demandedaproListMutableLiveData.value = arrayListDetailDemandeAppro
                }

                override fun onFailure(call: Call<DemandeDApprovisionnementModal>, t: Throwable) {
                    Log.e("Error", "${t.message}")
                }
            })
    }

}