package com.hasnaoui.geddoc.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hasnaoui.geddoc.databinding.DemandeAutoSortieDesignBinding
import com.hasnaoui.geddoc.models.DemandeModal

class DemandeAutoSortieAdapter(context: Context) :
    RecyclerView.Adapter<DemandeAutoSortieAdapter.ViewHolder>() {
    private var demandeList: ArrayList<DemandeModal> = ArrayList()
    private var listeValidator: ArrayList<Int> = ArrayList()
    private val context = context
    var text_color = "#00FF00"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DemandeAutoSortieDesignBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val demande = demandeList[0].deg_documents

        holder.bindItem(demande)
//        holder.itemView.setOnClickListener {
//            if(demande.deg_documents[position][1] ==395.0){
//
//                val intent = Intent(it.context,RequisitionHeure::class.java).apply {
//                    putExtra("record_id", demande.deg_documents[position][0].toString())
//                    putExtra("etat", demande.deg_documents[position][2].toString())
//                }
//                it.context.startActivity(intent)
//            }
//            else if(demande.deg_documents[position][1] ==6.0){
//
//                val intent = Intent(it.context,AutorisationDeSortie::class.java).apply {
//                    putExtra("record_id", demande.deg_documents[position][0].toString())
//                    putExtra("etat",demande.deg_documents[position][2].toString())
//                }
//                it.context.startActivity(intent)
//            }
//            else if(demande.deg_documents[position][1]==10.0){
//
//                val intent = Intent(it.context,DemandeDApprovisionnement::class.java).apply {
//                    putExtra("record_id",demande.deg_documents[position][0].toString())
//                    putExtra("etat",demande.deg_documents[position][2].toString())
//                }
//                it.context.startActivity(intent)
//            }
//
//        }
    }

    override fun getItemCount(): Int {
        if (demandeList.size > 0) {
            return demandeList[0].deg_documents.size
        }
        return demandeList.size
    }

    fun setListValidateur(ListValidateur: ArrayList<Int>) {
        this.listeValidator = ListValidateur
        notifyDataSetChanged()
    }

    fun setList(demandeList: ArrayList<DemandeModal>) {
        this.demandeList = demandeList
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding: DemandeAutoSortieDesignBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(demande: ArrayList<ArrayList<Any>>) {
            val list_validateuer_ids = demande[adapterPosition].subList(1, 3).filterNotNull()
            val list_validateuer_names = ArrayList<String>()
                demandeList[0].validation_cycle.forEach { it.name?.let { it1 ->
                    list_validateuer_names.add(
                        it1
                    )
                } }
            Log.e("valid_cycle_viewModal", list_validateuer_names.toString())
            itemBinding.documentName.text = demande[adapterPosition][13].toString()

            var listofTextView: ArrayList<TextView> = arrayListOf(
                itemBinding.validateur1,
                itemBinding.validateur2
            )
            var listofImageView: ArrayList<ImageView> = arrayListOf(
                itemBinding.profileImage1,
                itemBinding.profileImage2
            )

            for (i in listeValidator.indices) {
                convertNulToString(
                    listeValidator[i], listofTextView[i], demande[adapterPosition][9],
                    list_validateuer_ids.last() as Double, listofImageView[i],list_validateuer_names[i]
                )
            }
            Log.e("DATE",demande[adapterPosition][7].toString())
            itemBinding.date.text = demande[adapterPosition][7].toString()
            when (demande[adapterPosition][9]) {
                "draft" -> {
                    itemBinding.documentStatus.text = "Brouillon"
                    itemBinding.card.setStrokeColor(ColorStateList.valueOf(Color.GRAY))
                }
                "refused" -> {
                    itemBinding.documentStatus.text = "Refusé"
                    itemBinding.card.setStrokeColor(ColorStateList.valueOf(Color.RED))
                }
                "locked" -> {
                    itemBinding.documentStatus.text = "Approuvé"
                    itemBinding.card.setStrokeColor(ColorStateList.valueOf(Color.GREEN))
                }
                else -> {
                    itemBinding.documentStatus.text = "Non déffiner"
                    itemBinding.card.setStrokeColor(ColorStateList.valueOf(Color.parseColor("#123569")))
                }
            }


        }
    }

    fun convertNulToString(
        id_validator: Any?,
        validateur: TextView,
        status: Any,
        last_validator: Double,
        imageView: ImageView,
        validator_name: String
    ) {

        if (id_validator == null) {
            validateur.setTextColor(Color.parseColor("#FF0000"))
            validateur.text = "null"
        } else {
            validateur.text = validator_name
            if (status == "locked") {
                validateur.setTextColor(Color.parseColor("#00FF00"))
            } else if (status == "refused") {

                if (id_validator == last_validator.toInt()) {
                    validateur.setTextColor(Color.parseColor("#FF0000"))
                    text_color = "#808080"
                } else {
                    validateur.setTextColor(Color.parseColor(text_color))
                }
            }
            val url = "http://10.1.12.69:5000/images/${id_validator}.png"
            Glide.with(context)
                .load(url)
                .listener(object : RequestListener<Drawable> {
                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: com.bumptech.glide.request.target.Target<Drawable>?,
                            dataSource: com.bumptech.glide.load.DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            Log.d("Listener", "OnResourceReady")
                            //do something when picture already loaded
                            return false
                        }

                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            e?.logRootCauses("Listener")
//                            Log.e("Listener", e.toString())
                            //do something if error loading
                            return false
                        }
                    }
                )
                .fitCenter()
                .circleCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.ic_launcher_background)
                .into(imageView)
        }
    }

}

