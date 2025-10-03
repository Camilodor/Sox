package com.example.appinterface.Adapter.TipoPago

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.R
import com.example.appinterface.Modelos.TipoPago.TipoPago

class TipoPagoAdapter(
    private val tipospago: MutableList<TipoPago>,
    private val listener: OnTipopagoClickListener
) : RecyclerView.Adapter<TipoPagoAdapter.TipopagoViewHolder>() {
    fun getTIpospago(): List<TipoPago> {
        return tipospago
    }


    interface OnTipopagoClickListener {
        fun onConsultar(tipopago: TipoPago)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipopagoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tipospago, parent, false)
        return TipopagoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TipopagoViewHolder, position: Int) {
        val tipopago = tipospago[position]
        holder.bind(tipopago, listener)
    }

    override fun getItemCount(): Int = tipospago.size

    fun updateData(newList: List<TipoPago>) {
        tipospago.clear()
        tipospago.addAll(newList)
        notifyDataSetChanged()
        Log.d("TipoPagoAdapter", "Lista de tipos de pago actualizada. Total: ${tipospago.size}")
    }

    class TipopagoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvId: TextView = itemView.findViewById(R.id.tvId)
        private val tvNombresP: TextView = itemView.findViewById(R.id.tvNombresP)
        private val rowLayout: LinearLayout = itemView.findViewById(R.id.rowLayout)

        fun bind(tipopago: TipoPago, listener: OnTipopagoClickListener) {
            tvId.text = tipopago.id.toString()
            tvNombresP.text = tipopago.nombre


            rowLayout.setOnClickListener {
                Log.d("TIpoPagoAdapter", "Click en fila Tipo de pago ID: ${tipopago.id}")
                listener.onConsultar(tipopago)
            }

        }
    }
}
