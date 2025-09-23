package com.example.appinterface.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Api.Mercancias
import com.example.appinterface.R

class MercanciasAdapter (private val mercancias: List<Mercancias>) :
    RecyclerView.Adapter<MercanciasAdapter.MercanciasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MercanciasViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mercancias, parent, false)
        return MercanciasViewHolder(view)
    }

    override fun onBindViewHolder(holder: MercanciasViewHolder, position: Int) {
        val mercancia = mercancias[position]
        holder.bind(mercancia)
    }

    override fun getItemCount(): Int = mercancias.size

    class MercanciasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val NombreM: TextView = itemView.findViewById(R.id.NombreM)
        private val Observaciones: TextView = itemView.findViewById(R.id.Observaciones)

        fun bind(mercancia: Mercancias) {
            NombreM.text = "${mercancia.numero_remesa} ${mercancia.destino_mercancia}"
            Observaciones.text = mercancia.observaciones
        }
    }
}
