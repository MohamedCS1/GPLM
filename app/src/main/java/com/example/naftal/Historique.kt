package com.example.naftal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Historique : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.historique)

        val p = PrefManager()
        p.PrefManager(this)
        val t_qui = p.get_t_qui()

        val rv = findViewById<RecyclerView>(R.id.rv_historique)

        val lm = LinearLayoutManager(this)

        rv.layoutManager = lm

        val db = Firebase.firestore

        if (t_qui == "Dispatcheur"){

            val ID = p.get_Dispatcheur_id()

            val query = db.collection("Commande").whereEqualTo("valide_par", ID).orderBy("data_validation_dispatcheur",Query.Direction.DESCENDING)

            var option = FirestoreRecyclerOptions.Builder<model_Commande>().setQuery(query,model_Commande::class.java)
                .setLifecycleOwner(null).build()

            val adapter = adapter_h_dispatcheur(this,option)
            rv.adapter = adapter


            adapter.startListening()

        }

        else if (t_qui == "Chauffeur"){

            val ID = p.get_Chauffeur_id()

            val query = db.collection("Commande").whereEqualTo("livraion_valider_par" , ID)

            var option = FirestoreRecyclerOptions.Builder<model_Commande>().setQuery(query,model_Commande::class.java)
                .setLifecycleOwner(null).build()

            val adapter = adapter_h_chauffeur(this,option)

            rv.adapter = adapter


            adapter.startListening()

        }

       else if (t_qui == "Client"){

           val ID = p.get_client_id()

            val query = db.collection("Commande").whereEqualTo("client_ID" , ID).orderBy("date_time",Query.Direction.DESCENDING)
            var option = FirestoreRecyclerOptions.Builder<model_Commande>().setQuery(query,model_Commande::class.java)
                .setLifecycleOwner(null).build()

            val adapter = adapter_h_client(this,option)

            rv.adapter = adapter

            adapter.startListening()

       }

    }
}