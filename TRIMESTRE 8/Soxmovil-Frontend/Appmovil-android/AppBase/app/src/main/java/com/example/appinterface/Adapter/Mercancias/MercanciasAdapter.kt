package com.example.appinterface.Adapter.Mercancias

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Modelos.Mercancias.Mercancias
import com.example.appinterface.R

class MercanciasAdapter(
    private val mercancias: MutableList<Mercancias>,
    private val listener: OnMercanciaClickListener
) : RecyclerView.Adapter<MercanciasAdapter.MercanciaViewHolder>() {
    fun getMercancias(): List<Mercancias> {
        return mercancias
    }


    interface OnMercanciaClickListener {
        fun onConsultar(mercancias: Mercancias)
        fun onEditar(mercancias: Mercancias)
        fun onEliminar(mercancias: Mercancias)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MercanciaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mercancias, parent, false)
        return MercanciaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MercanciaViewHolder, position: Int) {
        val mercancia = mercancias[position]
        holder.bind(mercancia, listener)
    }

    override fun getItemCount(): Int = mercancias.size

    fun updateData(newList: List<Mercancias>) {
        mercancias.clear()
        mercancias.addAll(newList)
        notifyDataSetChanged()
        Log.d("MercanciasAdapter", "Lista de mercancias actualizada. Total: ${mercancias.size}")
    }

    class MercanciaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvId: TextView = itemView.findViewById(R.id.tvId)
        private val tvProveedores: TextView = itemView.findViewById(R.id.tvProveedores)
        private val tvUsuariosM: TextView = itemView.findViewById(R.id.tvUsuariosM)
        private val tvRemesa: TextView = itemView.findViewById(R.id.tvRemesa)
        private val btnEditarM: ImageButton = itemView.findViewById(R.id.btnEditarM)
        private val btnEliminarM: ImageButton = itemView.findViewById(R.id.btnEliminarM)
        private val rowLayout: LinearLayout = itemView.findViewById(R.id.rowLayout)

        fun bind(mercancia: Mercancias, listener: OnMercanciaClickListener) {
            tvId.text = mercancia.id.toString()
            tvProveedores.text = mercancia.proveedores_id.toString()
            tvUsuariosM.text = mercancia.usuarios_id.toString()
            tvRemesa.text = mercancia.numero_remesa


            rowLayout.setOnClickListener {
                Log.d("MercanciasAdapter", "Click en fila mercancia ID: ${mercancia.id}")
                listener.onConsultar(mercancia)
            }


            btnEditarM.setOnClickListener {
                Log.d("MercanciasAdapter", "Editar mercancia ID: ${mercancia.id}")
                listener.onEditar(mercancia)
            }

            btnEliminarM.setOnClickListener {
                Log.d("MercanciasAdapter", "Eliminar mercancia ID: ${mercancia.id}")
                listener.onEliminar(mercancia)
            }
        }
    }
}
