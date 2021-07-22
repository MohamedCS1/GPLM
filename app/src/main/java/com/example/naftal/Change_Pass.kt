package com.example.naftal

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


class Change_Pass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.change_password)

        val bu_change = findViewById<Button>(R.id.bu_change_pass)

        val ancien_pass = findViewById<EditText>(R.id.tv_ancien_pass)

        val nv_pass0 = findViewById<EditText>(R.id.tv_nv_pass0)

        val nv_pass1 = findViewById<EditText>(R.id.tv_nv_pass1)

        bu_change.setOnClickListener {
            Log.d("pass","${ancien_pass.text}  ${nv_pass0.text}  ${nv_pass1.text}")
        }

    }

    }


