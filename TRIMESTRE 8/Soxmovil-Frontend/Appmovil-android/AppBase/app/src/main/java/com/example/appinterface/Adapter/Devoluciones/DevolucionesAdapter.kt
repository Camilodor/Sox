package com.example.appinterface.Adapter.Devoluciones

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Modelos.Devoluciones.Devoluciones
import com.example.appinterface.R

class DevolucionesAdapter(
    private val devoluciones: MutableList<Devoluciones>,
    private val listener: OnDevolucionClickListener
) : RecyclerView.Adapter<DevolucionesAdapter.DevolucionViewHolder>() {
    fun getDevoluciones(): List<Devoluciones> {
        return devoluciones
    }


    interface OnDevolucionClickListener {
        fun onConsultar(devolucion: Devoluciones)
        fun onEditar(devolucion: Devoluciones)
        fun onEliminar(devolucion: Devoluciones)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevolucionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_devoluciones, parent, false)
        return DevolucionViewHolder(view)
    }

    override fun onBindViewHolder(holder: DevolucionViewHolder, position: Int) {
        val devolucion = devoluciones[position]
        holder.bind(devolucion, listener)
    }

    override fun getItemCount(): Int = devoluciones.size

    fun updateData(newList: List<Devoluciones>) {
        devoluciones.clear()
        devoluciones.addAll(newList)
        notifyDataSetChanged()
        Log.d("DevolucionAdapter", "Lista de devoluciones actualizada. Total: ${devoluciones.size}")
    }

    class DevolucionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvId: TextView = itemView.findViewById(R.id.tvId)
        private val tvMercanciaD: TextView = itemView.findViewById(R.id.tvMercanciaD)
        private val tvDespachosD: TextView = itemView.findViewById(R.id.tvDespachosD)
        private val tvUsuariosD: TextView = itemView.findViewById(R.id.tvUsuariosD)
        private val btnEditarD: ImageButton = itemView.findViewById(R.id.btnEditarD)
        private val btnEliminarD: ImageButton = itemView.findViewById(R.id.btnEliminarD)
        private val rowLayout: LinearLayout = itemView.findViewById(R.id.rowLayout)

        fun bind(devolucion: Devoluciones, listener: OnDevolucionClickListener) {
            tvId.text = devolucion.id.toString()
            tvMercanciaD.text = devolucion.mercancias_id.toString()
            tvDespachosD.text = devolucion.despachos_id.toString()
            tvUsuariosD.text = devolucion.usuarios_id.toString()


            rowLayout.setOnClickListener {
                Log.d("DevolucionesAdapter", "Click en fila devolucion ID: ${devolucion.id}")
                listener.onConsultar(devolucion)
            }


            btnEditarD.setOnClickListener {
                Log.d("DevolucionesAdapter", "Editar devolucion ID: ${devolucion.id}")
                listener.onEditar(devolucion)
            }

            btnEliminarD.setOnClickListener {
                Log.d("DevolucionesAdapter", "Eliminar Devolucion ID: ${devolucion.id}")
                listener.onEliminar(devolucion)
            }
        }
    }
}
