package com.example.appinterface.Adapter.Seguimientos

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.R
import com.example.appinterface.Modelos.Seguimientos.Seguimientos

class SeguimientosAdapter(
    private val seguimientos: MutableList<Seguimientos>,
    private val listener: OnSeguimientoClickListener
) : RecyclerView.Adapter<SeguimientosAdapter.SeguimientoViewHolder>() {
    fun getUsuarios(): List<Seguimientos> {
        return seguimientos
    }


    interface OnSeguimientoClickListener {
        fun onConsultar(seguimiento: Seguimientos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeguimientoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_seguimientos, parent, false)
        return SeguimientoViewHolder(view)
    }

    override fun onBindViewHolder(holder: SeguimientoViewHolder, position: Int) {
        val seguimiento = seguimientos[position]
        holder.bind(seguimiento, listener)
    }

    override fun getItemCount(): Int = seguimientos.size

    fun updateData(newList: List<Seguimientos>) {
        seguimientos.clear()
        seguimientos.addAll(newList)
        notifyDataSetChanged()
        Log.d("SeguimientosAdapter", "Lista de seguimientos actualizada. Total: ${seguimientos.size}")
    }

    class SeguimientoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvId: TextView = itemView.findViewById(R.id.tvId)
        private val tvMercanciasS: TextView = itemView.findViewById(R.id.tvMercanciaS)
        private val tvEstadoS: TextView = itemView.findViewById(R.id.tvEstadoS)
        private val tvObservacionesS: TextView = itemView.findViewById(R.id.tvObservacionesS)
        private val rowLayout: LinearLayout = itemView.findViewById(R.id.rowLayout)

        fun bind(seguimiento: Seguimientos, listener: OnSeguimientoClickListener) {
            tvId.text = seguimiento.id.toString()
            tvMercanciasS.text = seguimiento.mercancias_id.toString()
            tvEstadoS.text = seguimiento.estado
            tvObservacionesS.text = seguimiento.observaciones

            rowLayout.setOnClickListener {
                Log.d("SeguimientosAdapter", "Click en fila seguimiento ID: ${seguimiento.id}")
                listener.onConsultar(seguimiento)
            }



        }
    }
}
