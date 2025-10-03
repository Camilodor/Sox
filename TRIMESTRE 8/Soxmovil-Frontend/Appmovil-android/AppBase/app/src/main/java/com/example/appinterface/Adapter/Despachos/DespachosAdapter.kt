package com.example.appinterface.Adapter.Despachos

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Modelos.Despachos.Despachos
import com.example.appinterface.R

class DespachosAdapter(
    private val despachos: MutableList<Despachos>,
    private val listener: OnDespachoClickListener
) : RecyclerView.Adapter<DespachosAdapter.DespachoViewHolder>() {
    fun getDespachos(): List<Despachos> {
        return despachos
    }


    interface OnDespachoClickListener {
        fun onConsultar(despacho: Despachos)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DespachoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_despachos, parent, false)
        return DespachoViewHolder(view)
    }

    override fun onBindViewHolder(holder: DespachoViewHolder, position: Int) {
        val despacho = despachos[position]
        holder.bind(despacho, listener)
    }

    override fun getItemCount(): Int = despachos.size

    fun updateData(newList: List<Despachos>) {
        despachos.clear()
        despachos.addAll(newList)
        notifyDataSetChanged()
        Log.d("DespachosAdapter", "Lista de despachos actualizada . Total: ${despachos.size}")
    }

    class DespachoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvId: TextView = itemView.findViewById(R.id.tvId)
        private val tvMercanciaH: TextView = itemView.findViewById(R.id.tvMercanciaH)
        private val tvUsuariosH: TextView = itemView.findViewById(R.id.tvUsuariosH)
        private val tvTipopagoH: TextView = itemView.findViewById(R.id.tvTipopagoH)
        private val rowLayout: LinearLayout = itemView.findViewById(R.id.rowLayout)

        fun bind(despacho: Despachos, listener: OnDespachoClickListener) {
            tvId.text = despacho.id.toString()
            tvMercanciaH.text = despacho.mercancias_id.toString()
            tvUsuariosH.text = despacho.usuarios_id.toString()
            tvTipopagoH.text = despacho.tipo_pago_id.toString()

            rowLayout.setOnClickListener {
                Log.d("DespachosAdapter", "Click en fila despacho ID: ${despacho.id}")
                listener.onConsultar(despacho)
            }

        }
    }
}
