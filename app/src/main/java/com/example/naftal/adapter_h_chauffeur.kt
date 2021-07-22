package com.example.naftal

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class adapter_h_chauffeur: FirestoreRecyclerAdapter<model_Commande, Commande_View_Holder> {

    var context: Context? = null
    constructor(context: Context, options: FirestoreRecyclerOptions<model_Commande>):super(options)
    {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Commande_View_Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.c_h_chauffeur,parent,false)
        return Commande_View_Holder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: Commande_View_Holder,
        position: Int,
        model: model_Commande
    ) {
        val tv_quantite_demande = holder.itemView.findViewById<TextView>(R.id.tv_c_quantite_demande)
        val tv_quantite_livree = holder.itemView.findViewById<TextView>(R.id.tv_h_quantite_livree)
        val tv_date_demande = holder.itemView.findViewById<TextView>(R.id.tv_h_c_date_demande)
        val tv_data_livree = holder.itemView.findViewById<TextView>(R.id.date_livrasion)

        tv_quantite_demande.setText(model.c_gpl_demander)
        tv_quantite_livree.setText("${model.c_gpl_livres} T")
        tv_date_demande.setText(model.date_de_livraison)
        tv_data_livree.setText(model.data_validation_chauffeur)

    }

}