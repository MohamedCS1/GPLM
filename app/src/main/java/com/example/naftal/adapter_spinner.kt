package com.example.naftal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class adapter_spinner(val context:Context, var info_cauffeur: ArrayList<chauffeur>):BaseAdapter() {
    override fun getCount(): Int {
        return info_cauffeur.size
    }

    override fun getItem(position: Int): Any {
        return info_cauffeur.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()

    }


    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view:View
        val vh :ItemHolder
        if (convertView == null)
        {
            view = inflater.inflate(R.layout.costum_spinner,parent,false)
            vh = ItemHolder(view)
            view?.tag = vh

        }
        else
        {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.name!!.text = info_cauffeur[position].Nom
        vh.prenom!!.text = info_cauffeur[position].Prenom
        vh.current!!.text = info_cauffeur[position].current

        return view
    }


    private class ItemHolder(row:View?)
    {

        var name:TextView? = null
        var prenom:TextView? = null
        var current:TextView? = null

        init {

            name = row?.findViewById(R.id.spinner_name) as TextView
            prenom = row.findViewById(R.id.spinner_prenom) as TextView
            current = row.findViewById(R.id.spinner_number_current) as TextView

        }
    }

}