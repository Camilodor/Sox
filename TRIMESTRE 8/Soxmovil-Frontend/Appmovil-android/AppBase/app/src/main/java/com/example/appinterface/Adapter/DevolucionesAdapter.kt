package com.example.appinterface.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Api.Mercancias
import com.example.appinterface.Despachos
import com.example.appinterface.Devoluciones
import com.example.appinterface.Entregas
import com.example.appinterface.R

class DevolucionesAdapter (private val devoluciones: List<Devoluciones>) :
    RecyclerView.Adapter<DevolucionesAdapter.DevolucionesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevolucionesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_devoluciones, parent, false)
        return DevolucionesViewHolder(view)
    }

    override fun onBindViewHolder(holder: DevolucionesViewHolder, position: Int) {
        val devolucion = devoluciones[position]
        holder.bind(devolucion)
    }

    override fun getItemCount(): Int = devoluciones.size

    class DevolucionesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val Motivo: TextView = itemView.findViewById(R.id.Motivo)
        private val EstadoD: TextView = itemView.findViewById(R.id.EstadoD)

        fun bind(devolucion: Devoluciones) {
            Motivo.text = devolucion.motivo_devolucion
            EstadoD.text = devolucion.estado_devolucion
        }
    }
}
