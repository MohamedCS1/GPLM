package com.example.naftal


import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ramotion.fluidslider.FluidSlider
import java.text.SimpleDateFormat
import java.util.*


class Activity_Client : AppCompatActivity() {
    val db = firebase_db()

    @SuppressLint("SetTextI18n", "ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.M)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.client)

        val p = PrefManager()
        p.PrefManager(this)

        val df = Firebase.firestore

        val ID = p.get_client_id()
        val Password = p.get_client_pass()
        val adresse_client = p.get_client_Adresse()

        Log.d("info","$ID $Password $adresse_client")

        val tool_bar = findViewById<Toolbar>(R.id.toolbar1)
        tool_bar.setBackgroundColor(getColor(R.color.color_action_bar_client))
        tool_bar.title = "client"
        setSupportActionBar(tool_bar)
        requireNotNull(supportActionBar!!.setDisplayHomeAsUpEnabled(true))


        val tv_mon_station = findViewById<TextView>(R.id.Tv_mon_Station)
        tv_mon_station.setText(adresse_client)

        db.isTimeAutomatic(this)
        val slider = findViewById<FluidSlider>(R.id.fluidSlider)
        val tv_ctt = findViewById<TextView>(R.id.text_view_cntt)
        val max = 40
        val min = 0
        val total = max - min

        var quantity:Int? = null
        slider.positionListener = { pos ->
            slider.bubbleText = "${min + (total * pos).toInt()}"
            tv_ctt.text = "${min + (total * slider.position).toInt()}"
            quantity = min + (total * pos).toInt()
        }

        slider.position = 0.3f
        slider.startText = "$min"
        slider.endText = "$max"

        val dialog = AlertDialog.Builder(this)
        val view:View = View.inflate(this,R.layout.dialog_valide_demande,null)
        val adresse = view.findViewById<TextView>(R.id.adresse)
        val quantity_gpl = view.findViewById<TextView>(R.id.quantity_gpl)
        val date = view.findViewById<TextView>(R.id.date)
        val prix_total = view.findViewById<TextView>(R.id.prix_total)
        val date_livrasion = view.findViewById<TextView>(R.id.date_livrasion)
        val bu_envoyer = view.findViewById<Button>(R.id.bu_envoyer)
        val bu_sortir = view.findViewById<Button>(R.id.bu_sortir)


        val d = dialog.setView(view).create()
        d.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        d.setCancelable(false)
        val bu_demande = findViewById<Button>(R.id.bu_demande)
        val checkBox_to_day = findViewById<CheckBox>(R.id.checkBox_livrai_to_day)
        val tv_date_livrai = findViewById<TextView>(R.id.Tv_selection_date_livrai)
        checkBox_to_day.isChecked = true
        tv_date_livrai.isVisible = false
        checkBox_to_day.setOnClickListener()
        {
            tv_date_livrai.isVisible = !checkBox_to_day.isChecked

        }

        val cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd-MM-yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            tv_date_livrai.text = sdf.format(cal.time)

        }

        tv_date_livrai.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        bu_demande.setOnClickListener {
            val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            val currentTime: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            adresse.text = adresse_client
            if (tv_date_livrai.text.contains("Sélectionnez la date") && !checkBox_to_day.isChecked)
            {
                Toast.makeText(this,"Sélectionnez la date !!",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            else if (tv_ctt.text == "0")
            {
                Toast.makeText(this,"0T !!",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            else if (checkBox_to_day.isChecked)
            {
                date_livrasion.text = currentDate
                prix_total.text = "${((quantity!! * 1000) * 9.4)}0 Da"
                date.text = "$currentDate  $currentTime"
                quantity_gpl.text = "${min + (total * slider.position).toInt()} T"
                d.show()
            }
            else
            {
                date_livrasion.text = tv_date_livrai.text
                prix_total.text = "${((quantity!! * 1000) * 9.4)}0 Da"
                date.text = "$currentDate  $currentTime"
                quantity_gpl.text = "${min + (total * slider.position).toInt()} T"
                d.show()
            }

        }
        bu_sortir.setOnClickListener {
            d.cancel()
        }

        var document_rf:String? = null
        fun updat_pathe(documentReference:String)
        {
            df.collection("Commande").document(documentReference).update(
                mapOf("c_path" to documentReference)
            )

        }

        bu_envoyer.setOnClickListener {
            val currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            val currentTime: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            df.collection("Commande")
                .add(

                    hashMapOf(

                        "c_path" to "",
                        "client_ID" to ID,
                        "livraion_valider_par" to "",
                        "client_adresse" to adresse_client,
                        "c_gpl_demander" to tv_ctt.text,
                        "c_gpl_valide" to "",
                        "c_gpl_livres" to "",
                        "date_time" to "$currentDate $currentTime",
                        "date_de_livraison" to "${date_livrasion.text}",
                        "data_validation_dispatcheur" to "",
                        "data_validation_chauffeur" to "",
                        "state_commande" to "en attente...",
                        "valide_par" to "",
                        "livres_par" to "",
                        "prix" to "${prix_total.text}",
                        "c_livrai" to false

                    )
                )
                .addOnSuccessListener { documentReference ->
                    Toast.makeText(this,"La demande a été envoyée avec succès",Toast.LENGTH_LONG).show()
                    Log.d("fire", "DocumentSnapshot added with ID: ${documentReference.id}")
                    document_rf = documentReference.id.toString()
                    updat_pathe(document_rf!!)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error adding document",Toast.LENGTH_LONG).show()
                }

            d.hide()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            val dialog = AlertDialog.Builder(this)
            val view:View = View.inflate(this,R.layout.alert_log_out,null)
            val d = dialog.setView(view).create()
            d.show()
            val bu_yes = view.findViewById<Button>(R.id.bu_yes)
            val bu_No = view.findViewById<Button>(R.id.bu_No)
            bu_yes.setOnClickListener {
                val p = PrefManager()
                p.PrefManager(this)
                p.clearSession()
                val db = firebase_db()
                db.info_login.clear()
                val intent = Intent(this,Login::class.java)
                startActivity(intent)
                finish()
            }
            bu_No.setOnClickListener {
                d.hide()
            }
        }
        if (item.itemId == R.id.historique)
        {
            val intent = Intent(this,Historique::class.java)
            startActivity(intent)
        }


        if (item.itemId == R.id.change_pass)
        {
            val intent = Intent(this,Change_Pass::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

}
