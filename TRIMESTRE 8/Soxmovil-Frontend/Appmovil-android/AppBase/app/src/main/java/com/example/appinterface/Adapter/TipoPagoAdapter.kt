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
import com.example.appinterface.TipoPago

class TipoPagoAdapter(private val tipospago: List<TipoPago>) :
    RecyclerView.Adapter<TipoPagoAdapter.TipoPagoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipoPagoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tipospago, parent, false)
        return TipoPagoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TipoPagoViewHolder, position: Int) {
        val tipopago = tipospago[position]
        holder.bind(tipopago)
    }

    override fun getItemCount(): Int = tipospago.size

    class TipoPagoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tipoid: TextView = itemView.findViewById(R.id.tipoid)
        private val NombreT: TextView = itemView.findViewById(R.id.NombreT)

        fun bind(tipopago: TipoPago) {
            tipoid.text = "${tipopago.id}"
            NombreT.text = tipopago.nombre
        }
    }
}