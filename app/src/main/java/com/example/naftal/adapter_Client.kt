package com.example.naftal

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions


class adapter_Client : FirestoreRecyclerAdapter<model_Clients, Clients_View_Holder>{

    var context:Context? = null
    constructor(context: Context, options: FirestoreRecyclerOptions<model_Clients>) : super(options) {
        this.context = context
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Clients_View_Holder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_client,parent,false)
            return Clients_View_Holder(view)
        }


        override fun onBindViewHolder(
            holder: Clients_View_Holder,
            position: Int,
            model: model_Clients
        ) {


            val adresse_client = holder.itemView.findViewById<TextView>(R.id.adresse_Client)
            val Client_ID = holder.itemView.findViewById<TextView>(R.id.Client_id)
            val R_S_Client = holder.itemView.findViewById<TextView>(R.id.R_S_Client)

            adresse_client.text = model.Adresse
            Client_ID.text = model.Client_ID
            R_S_Client.text = model.Raison_Sociale

            val dialog = AlertDialog.Builder(context)


            holder.itemView.setOnLongClickListener(object :View.OnLongClickListener
            {

                override fun onLongClick(v: View?): Boolean {
                    Log.d("position",position.toString())

                    val view:View = View.inflate(context,R.layout.alert_delet_client,null)
                    val client_id = view.findViewById<TextView>(R.id.Client_id)
                    val client_adresse = view.findViewById<TextView>(R.id.adresse_Client)
                    val bu_ok = view.findViewById<Button>(R.id.bu_envoyer)
                    val bu_no = view.findViewById<Button>(R.id.bu_sortir)
                    client_id.setText(model.Client_ID)
                    client_adresse.setText(model.Adresse)
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