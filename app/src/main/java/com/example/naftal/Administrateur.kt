package com.example.naftal

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.royrodriguez.transitionbutton.TransitionButton

class Administrateur : AppCompatActivity() {

    val db = firebase_db()
    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.administrateur)

        val p = PrefManager()
        p.PrefManager(this)

        db.isTimeAutomatic(this)

        val ID = p.get_Admin_id()
        val Password = p.get_Admin_pass()

        Log.d("info","$ID $Password")


        val tool_bar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar2)
        tool_bar.setBackgroundColor(getColor(R.color.color_action_bar_client))
        tool_bar.title = "Admin "
        setSupportActionBar(tool_bar)

        requireNotNull(supportActionBar!!.setDisplayHomeAsUpEnabled(true))

        val bu_client = findViewById<TransitionButton>(R.id.bu_client)
        val bu_chauffer = findViewById<TransitionButton>(R.id.bu_chauffer)
        val bu_Dispatcheur = findViewById<TransitionButton>(R.id.bu_Dispatcheur)

        bu_client.setOnClickListener {
                bu_client.startAnimation()
                val handler = Handler()
                handler.postDelayed({
                    val isSuccessful_Client = true
                    if (isSuccessful_Client)
                    {

                        bu_Dispatcheur.isVisible = false
                        bu_chauffer.isVisible = false
                        bu_client.stopAnimation(TransitionButton.StopAnimationStyle.EXPAND){

                            val intent = Intent(baseContext, rv_admin::class.java)
                            intent.putExtra("you_are", "Client")

                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                            startActivity(intent)
                            finish()
                        }
                    }
                },2500)
            }




        bu_Dispatcheur.setOnClickListener {
            bu_Dispatcheur.startAnimation()
            val handler = Handler()
            handler.postDelayed({
                val isSuccessful_Client = true
                if (isSuccessful_Client)
                {
                    bu_client.isVisible = false
                    bu_chauffer.isVisible = false
                    bu_Dispatcheur.stopAnimation(TransitionButton.StopAnimationStyle.EXPAND) {
                        val intent = Intent(baseContext, rv_admin::class.java)
                        intent.putExtra("you_are", "Dispatcheur")
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        startActivity(intent)
                        finish()
                    }
                }
            },2500)
        }


        bu_chauffer.setOnClickListener {
            bu_chauffer.startAnimation()
            val handler = Handler()
            handler.postDelayed({
                val isSuccessful_Client = true
                if (isSuccessful_Client)
                {
                    bu_Dispatcheur.isVisible = false
                    bu_client.isVisible = false
                    bu_chauffer.stopAnimation(TransitionButton.StopAnimationStyle.EXPAND) {
                        val intent = Intent(baseContext, rv_admin::class.java)
                        intent.putExtra("you_are", "chauffer")
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                        startActivity(intent)
                        finish()
                    }
                }
            },2500)
        }

        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_admin,menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            val dialog = AlertDialog.Builder(this)
            val view: View = View.inflate(this,R.layout.alert_log_out,null)
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

        if (item.itemId == R.id.change_pass)
        {
            val intent = Intent(this,Change_Pass::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}