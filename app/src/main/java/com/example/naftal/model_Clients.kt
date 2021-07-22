package com.example.naftal

import com.google.firebase.firestore.FieldValue
import java.sql.Timestamp

class model_Clients {

    var Client_ID:String? = null
    var Adresse:String? = null
    var Raison_Sociale:String? = null
    var Passwor:String? = null
    var Date_and_Time:String? = null

    constructor(
        Client_ID:String, Raison_Sociale:String, Adresse:String,
        Passwor:String, Date_and_Time:String

    )
    {
        this.Client_ID = Client_ID
        this.Adresse = Adresse
        this.Raison_Sociale = Raison_Sociale
        this.Passwor = Passwor
        this.Date_and_Time = Date_and_Time
    }
    constructor(){}

}