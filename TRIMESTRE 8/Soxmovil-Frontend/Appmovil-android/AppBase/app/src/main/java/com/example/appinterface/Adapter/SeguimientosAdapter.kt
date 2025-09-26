package com.example.appinterface.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Api.Proveedores
import com.example.appinterface.Usuarios
import com.example.appinterface.R
import com.example.appinterface.Seguimientos

class SeguimientosAdapter(private val seguimientos: List<Seguimientos>) :
    RecyclerView.Adapter<SeguimientosAdapter.SeguimientosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeguimientosViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_seguimientos, parent, false)
        return SeguimientosViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeguimientosViewHolder, position: Int) {
        val seguimiento =seguimientos[position]
        holder.bind(seguimiento)
    }

    override fun getItemCount(): Int = seguimientos.size

    class SeguimientosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val Mercancia: TextView = itemView.findViewById(R.id.Mercancia)
        private val EstadoS: TextView = itemView.findViewById(R.id.EstadoS)

        fun bind(seguimiento: Seguimientos) {
            Mercancia.text = "${seguimiento.mercancias_id}"
            EstadoS.text = seguimiento.estado
        }
    }
}