package com.example.naftal


import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class adapter_Dispatcheur: FirestoreRecyclerAdapter<model_Dispatcheurs,Dispatcheurs_View_Holder> {


    var context:Context? = null
    constructor(context: Context,options: FirestoreRecyclerOptions<model_Dispatcheurs>) : super(options){
        this.context = context
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Dispatcheurs_View_Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_dispatcheur,parent,false)
        return Dispatcheurs_View_Holder(view)
    }

    override fun onBindViewHolder(
        holder: Dispatcheurs_View_Holder,
        position: Int,
        model: model_Dispatcheurs
    ) {
        val Tv_Dispatcheur_ID = holder.itemView.findViewById<TextView>(R.id.Dispatcheur_ID)
        val Tv_Adresse_Dispatcheur = holder.itemView.findViewById<TextView>(R.id.Adresse_Dis)
        val Tv_Nom_Dispatcheur = holder.itemView.findViewById<TextView>(R.id.Nom_Dispatcheur)
        val Tv_Prenom_Dispatcheur = holder.itemView.findViewById<TextView>(R.id.Prenom_Dispatcheur)

        Tv_Dispatcheur_ID.text = model.Dispatcheur_ID
        Tv_Adresse_Dispatcheur.text = model.Adresse
        Tv_Nom_Dispatcheur.text = model.Nom
        Tv_Prenom_Dispatcheur.text = model.Prénom
        val dialog = AlertDialog.Builder(context)

        holder.itemView.setOnLongClickListener(object :View.OnLongClickListener
        {
            override fun onLongClick(v: View?): Boolean {
                Log.d("po",position.toString())
                val view:View = View.inflate(context,R.layout.alert_delet_dispatcheur,null)
                val dispatcheur_id = view.findViewById<TextView>(R.id.Dispatcheur_ID)
                val dispatcheur_adresse = view.findViewById<TextView>(R.id.Adresse_Dis)
                val Nom_dispatcheur = view.findViewById<TextView>(R.id.Nom_Dispatcheur)
                val Prenom_dispatcheur = view.findViewById<TextView>(R.id.Prenom_Dispatcheur)
                val bu_ok = view.findViewById<Button>(R.id.bu_envoyer)
                val bu_no = view.findViewById<Button>(R.id.bu_sortir)
                dispatcheur_id.setText(model.Dispatcheur_ID)
                Nom_dispatcheur.setText(model.Nom)
                Prenom_dispatcheur.setText(model.Prénom)
                dispatcheur_adresse.setText(model.Adresse)
                val a = dialog.setView(view).create()
                a.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                a.show()
                bu_ok.setOnClickListener {
                    notifyDataSetChanged()
                    snapshots.getSnapshot(position).reference.delete()
                    notifyItemRemoved(holder.adapterPosition)
                    notifyItemRangeRemoved(holder.adapterPosition,itemCount)
                    a.cancel()
                }
                bu_no.setOnClickListener {
                    a.hide()
                }
                return true
            }

        })
        setAnimation(holder.itemView,position)
    }
    fun setAnimation(viewToAnimation: View, position: Int)
    {

        val animation: Animation = AnimationUtils.loadAnimation(
            context,
            if (position > -1) R.anim.down_from_top else R.anim.down_from_top
        )
        viewToAnimation.startAnimation(animation)

    }
    override fun onDataChanged() {
        notifyDataSetChanged()
        super.onDataChanged()
    }

}
