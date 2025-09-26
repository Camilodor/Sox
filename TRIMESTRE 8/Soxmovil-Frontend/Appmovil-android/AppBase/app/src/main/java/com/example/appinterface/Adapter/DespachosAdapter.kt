package com.example.appinterface.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Api.Mercancias
import com.example.appinterface.Despachos
import com.example.appinterface.R

class DespachosAdapter (private val despachos: List<Despachos>) :
    RecyclerView.Adapter<DespachosAdapter.DespachosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DespachosViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_despachos, parent, false)
        return DespachosViewHolder(view)
    }

    override fun onBindViewHolder(holder: DespachosViewHolder, position: Int) {
        val despacho = despachos[position]
        holder.bind(despacho)
    }

    override fun getItemCount(): Int = despachos.size

    class DespachosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val Placa: TextView = itemView.findViewById(R.id.Placa)
        private val Observaciones: TextView = itemView.findViewById(R.id.Observaciones)

        fun bind(despacho: Despachos) {
            Placa.text = "${despacho.vehiculos_id} ${despacho.mercancias_id}"
            Observaciones.text = despacho.observaciones_mer
        }
    }
}
