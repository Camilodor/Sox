package com.example.appinterface.Adapter.Entregas

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.R
import com.example.appinterface.Modelos.Entregas.Entregas

class EntregasAdapter(
    private val entregas: MutableList<Entregas>,
    private val listener: OnEntregaClickListener
) : RecyclerView.Adapter<EntregasAdapter.EntregaViewHolder>() {
    fun getEntregas(): List<Entregas> {
        return entregas
    }


    interface OnEntregaClickListener {
        fun onConsultar(entregas: Entregas)
        fun onEditar(entregas: Entregas)
        fun onEliminar(entregas: Entregas)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntregaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entregas, parent, false)
        return EntregaViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntregaViewHolder, position: Int) {
        val usuario = entregas[position]
        holder.bind(usuario, listener)
    }

    override fun getItemCount(): Int = entregas.size

    fun updateData(newList: List<Entregas>) {
        entregas.clear()
        entregas.addAll(newList)
        notifyDataSetChanged()
        Log.d("EntregasAdapter", "Lista de entregas actualizada. Total: ${entregas.size}")
    }

    class EntregaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvId: TextView = itemView.findViewById(R.id.tvId)
        private val tvMercanciaE: TextView = itemView.findViewById(R.id.tvMercanciaE)
        private val tvDespachosE: TextView = itemView.findViewById(R.id.tvDespachosE)
        private val tvUsuariosE: TextView = itemView.findViewById(R.id.tvUsuariosE)
        private val btnEditarE: ImageButton = itemView.findViewById(R.id.btnEditarE)
        private val btnEliminarE: ImageButton = itemView.findViewById(R.id.btnEliminarE)
        private val rowLayout: LinearLayout = itemView.findViewById(R.id.rowLayout)

        fun bind(entrega: Entregas, listener: OnEntregaClickListener) {
            tvId.text = entrega.id.toString()
            tvMercanciaE.text = entrega.mercancias_id.toString()
            tvDespachosE.text = entrega.despachos_id.toString()
            tvUsuariosE.text = entrega.usuarios_id.toString()

            // --- Click en la fila completa para consultar ---
            rowLayout.setOnClickListener {
                Log.d("EntregasAdapter", "Click en fila entrega ID: ${entrega.id}")
                listener.onConsultar(entrega)
            }

            // --- Acciones específicas ---
            btnEditarE.setOnClickListener {
                Log.d("EntregasAdapter", "Editar entrega ID: ${entrega.id}")
                listener.onEditar(entrega)
            }

            btnEliminarE.setOnClickListener {
                Log.d("EntregasAdapter", "Eliminar usuario ID: ${entrega.id}")
                listener.onEliminar(entrega)
            }
        }
    }
}
