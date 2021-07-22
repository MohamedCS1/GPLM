package com.example.naftal

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.royrodriguez.transitionbutton.TransitionButton


@RequiresApi(Build.VERSION_CODES.M)
class Login : AppCompatActivity() {
    val p = PrefManager()
    var noInternet:Boolean? = null
    @RequiresApi(Build.VERSION_CODES.M)
    fun check_Internet()
    {
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


        val nc = cm.getNetworkCapabilities(cm.activeNetwork)

        val downSpeed = (nc?.linkDownstreamBandwidthKbps)?.div(1000)

        val upSpeed = (nc?.linkUpstreamBandwidthKbps)?.div(1000)

        Log.d("internt","${downSpeed}  ${upSpeed}")

        noInternet = upSpeed == null || downSpeed == null
    }
    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        check_Internet()
        val transitionButton = findViewById<TransitionButton>(R.id.transition_button)
        val text_client_id = findViewById<EditText>(R.id.Text_client_id)
        val text_password = findViewById<EditText>(R.id.Text_Password)
        val bu_show_pass = findViewById<ImageButton>(R.id.bu_show_pass)
        if (noInternet == true){Toast.makeText(this,"Vous n'avez pas de connexion Internet",Toast.LENGTH_LONG).show()}
        val db = firebase_db()
        var Client_data = arrayListOf<prsn>()
        var Administrateur_data = arrayListOf<prsn>()
        var Dispatcheur_data = arrayListOf<prsn>()
        var Chauffeur_data = arrayListOf<prsn>()

        val th = Thread{
            Client_data = db.get_data_client()
            Administrateur_data = db.get_data_admin()
            Dispatcheur_data = db.get_data_dispatcheur()
            Chauffeur_data = db.get_data_chauffeur()
        }
        th.start()

        var bol:Boolean = true
        bu_show_pass.setOnClickListener{

            if (bol == false)
            {

                text_password.transformationMethod = PasswordTransformationMethod.getInstance()
                bu_show_pass.setImageDrawable(this.getDrawable(R.drawable.ic_no_show_pass))
                bol = true
            }
            else
            {
                text_password.transformationMethod = HideReturnsTransformationMethod.getInstance()
                bu_show_pass.setImageDrawable(this.getDrawable(R.drawable.ic_show_pass))
                bol = false
            }

        }

        transitionButton.setOnClickListener {
            val t = text_client_id.text.toString().toCharArray()
            check_Internet()
            if (noInternet == true){
                Toast.makeText(this,"Vous n'avez pas de connexion Internet",Toast.LENGTH_LONG).show()
            }
            else if (text_password.text.isEmpty() || text_client_id.text.isEmpty()){
                Toast.makeText(this,"Entrez votre ID et votre mot de passe",Toast.LENGTH_LONG).show()
            }
            else if (t[0] == 'N' && t[1] == 'A' && t[2] == 'F' && t[3] == 'T')
            {
                transitionButton.startAnimation()
                val handler = Handler()
                handler.postDelayed({
                    val isSuccessful_Admin = db.Exist_admin(text_client_id.text.toString(),text_password.text.toString(),Administrateur_data)
                    val isSuccessful_Dispatcheur = db.Exist_Dispatcheur(text_client_id.text.toString(),text_password.text.toString(),Dispatcheur_data)
                    val isSuccessful_Chauffeur = db.Exist_Chauffeur(text_client_id.text.toString(),text_password.text.toString(),Chauffeur_data)
                    if (isSuccessful_Admin) {

                        p.PrefManager(this)
                        p.insert_Admin("Admin",db.info_login[0].prsn_id.toString(),db.info_login[0].password.toString(),true)
                        val intent = Intent(baseContext, Administrateur::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        startActivity(intent)
                        Administrateur_data.clear()

                        finish()

                    } else if (isSuccessful_Dispatcheur)
                    {

                        p.PrefManager(this)
                        p.insert_dispatcheur("Dispatcheur",db.info_login[0].prsn_id.toString(),db.info_login[0].password.toString(),db.info_login[0].Nom.toString(),db.info_login[0].Prenom.toString(),db.info_login[0].Adresse.toString(),true)
                        val intent = Intent(baseContext, Dispatcheur::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        startActivity(intent)
                        Dispatcheur_data.clear()
                        finish()
                    }
                    else if (isSuccessful_Chauffeur)
                    {

                        p.PrefManager(this)
                        p.insert_Chauffeur("Chauffeur",db.info_login[0].prsn_id.toString(),db.info_login[0].password.toString(),db.info_login[0].Nom.toString(),db.info_login[0].Prenom.toString(),db.info_login[0].Adresse.toString(),true)
                        val intent = Intent(baseContext, Activity_chauffeur::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        startActivity(intent)
                        Chauffeur_data.clear()
                        finish()
                    }
                    else
                    {
                        Toast.makeText(this,"ID ou le mot de passe est incorrect",Toast.LENGTH_SHORT).show()
                        transitionButton.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null)
                    }
                }, 2500)
            }
            else
            {
                transitionButton.startAnimation()
                val handler = Handler()
                handler.postDelayed({
                    val isSuccessful_Client = db.Exist_client(text_client_id.text.toString(), text_password.text.toString(), Client_data)
                    if (isSuccessful_Client)
                    {
                        p.PrefManager(this)
                        p.insert_Client("Client",db.info_login[0].prsn_id.toString(),db.info_login[0].password.toString(),db.info_login[0].Adresse.toString(),true)
                        val intent = Intent(baseContext, Activity_Client::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        startActivity(intent)
                        Client_data.clear()
                        finish()
                    }
                    else
                    {
                        Toast.makeText(this,"ID ou le mot de passe est incorrect",Toast.LENGTH_SHORT).show()
                        transitionButton.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null)
                    }
                },2500)
            }

        }

        val mot_passe_oublie = findViewById<TextView>(R.id.mot_passe_oublie)
        mot_passe_oublie.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val view:View = View.inflate(this,R.layout.forget_pass,null)
            val a = builder.create()
            a.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            a.setView(view, 0, 0, 0, 100)
            a.show()
        }
    }

    override fun onStart() {
        val p = PrefManager()
        p.PrefManager(this)
        val x = p.get_t_qui()

        if (x == "Client")
        {
            val intent = Intent(baseContext, Activity_Client::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }
        else if (x == "Admin")
        {
            val intent = Intent(baseContext, Administrateur::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }
        else if (x == "Chauffeur")
        {
            val intent = Intent(baseContext, Activity_chauffeur::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }
        else if (x == "Dispatcheur")
        {
            val intent = Intent(baseContext, Dispatcheur::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }


        super.onStart()
    }

}