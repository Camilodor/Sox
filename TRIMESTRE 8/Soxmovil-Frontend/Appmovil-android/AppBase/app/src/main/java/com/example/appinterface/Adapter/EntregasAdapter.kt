package com.example.appinterface.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Api.Mercancias
import com.example.appinterface.Despachos
import com.example.appinterface.Entregas
import com.example.appinterface.R

class EntregasAdapter (private val entregas: List<Entregas>) :
    RecyclerView.Adapter<EntregasAdapter.EntregasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntregasViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entregas, parent, false)
        return EntregasViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntregasViewHolder, position: Int) {
        val entrega = entregas[position]
        holder.bind(entrega)
    }

    override fun getItemCount(): Int = entregas.size

    class EntregasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val NombreE: TextView = itemView.findViewById(R.id.NombreE)
        private val Estado: TextView = itemView.findViewById(R.id.Estado)

        fun bind(entrega: Entregas) {
            NombreE.text = entrega.nombre_recibe
            Estado.text = entrega.estado_entrega
        }
    }
}
