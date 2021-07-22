package com.example.naftal

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class adapter_h_dispatcheur: FirestoreRecyclerAdapter<model_Commande, Commande_View_Holder> {

    var context: Context? = null
    constructor(context: Context, options: FirestoreRecyclerOptions<model_Commande>):super(options)
    {
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Commande_View_Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.c_h_dispatcheur,parent,false)
        return Commande_View_Holder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        holder: Commande_View_Holder,
        position: Int,
        model: model_Commande
    ) {
        val tv_quantite_demande = holder.itemView.findViewById<TextView>(R.id.tv_h_d_quantite_demande)
        val tv_quantite_valider = holder.itemView.findViewById<TextView>(R.id.tv_h_d_quantite_valider)
        val tv_date_demande = holder.itemView.findViewById<TextView>(R.id.tv_h_d_date_demande)
        val tv_state = holder.itemView.findViewById<TextView>(R.id.tv_h_d_state)
        val date_validation = holder.itemView.findViewById<TextView>(R.id.tv_dv_dispatcheur)

        date_validation.setText(model.data_validation_dispatcheur)
        tv_quantite_demande.setText(model.c_gpl_demander)
        tv_quantite_valider.setText("${model.c_gpl_valide} T")
        tv_date_demande.setText(model.date_de_livraison)
        tv_state.setText(model.state_commande)

    }

}