package com.example.appinterface.Adapter.Proveedores

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Modelos.Proveedores.Proveedores
import com.example.appinterface.R

class ProveedoresAdapter(
    private val proveedores: MutableList<Proveedores>,
    private val listener: OnProveedorClickListener
) : RecyclerView.Adapter<ProveedoresAdapter.ProveedorViewHolder>() {
    fun getProveedores(): List<Proveedores> {
        return proveedores
    }


    interface OnProveedorClickListener {
        fun onConsultar(proveedor: Proveedores)
        fun onEditar(proveedor: Proveedores)
        fun onEliminar(proveedor: Proveedores)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProveedorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_proveedores, parent, false)
        return ProveedorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProveedorViewHolder, position: Int) {
        val proveedor = proveedores[position]
        holder.bind(proveedor, listener)
    }

    override fun getItemCount(): Int = proveedores.size

    fun updateData(newList: List<Proveedores>) {
        proveedores.clear()
        proveedores.addAll(newList)
        notifyDataSetChanged()
        Log.d("ProveedoresAdapter", "Lista de proveedores actualizada. Total: ${proveedores.size}")
    }

    class ProveedorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvId: TextView = itemView.findViewById(R.id.tvId)
        private val tvUsuariosP: TextView = itemView.findViewById(R.id.tvUsuariosP)
        private val tvNombres: TextView = itemView.findViewById(R.id.tvNombres)
        private val tvDescripcionP: TextView = itemView.findViewById(R.id.tvDescripcionP)
        private val btnEditarP: ImageButton = itemView.findViewById(R.id.btnEditarP)
        private val btnEliminarP: ImageButton = itemView.findViewById(R.id.btnEliminarP)
        private val rowLayout: LinearLayout = itemView.findViewById(R.id.rowLayout)

        fun bind(proveedor: Proveedores, listener: OnProveedorClickListener) {
            tvId.text = proveedor.id.toString()
            tvUsuariosP.text = proveedor.usuarios_id.toString()
            tvNombres.text = proveedor.nombre
            tvDescripcionP.text = proveedor.descripcion


            rowLayout.setOnClickListener {
                Log.d("ProveedoresAdapter", "Click en fila proveedor ID: ${proveedor.id}")
                listener.onConsultar(proveedor)
            }


            btnEditarP.setOnClickListener {
                Log.d("ProveedoresAdapter", "Editar proveedor ID: ${proveedor.id}")
                listener.onEditar(proveedor)
            }

            btnEliminarP.setOnClickListener {
                Log.d("ProveedoresAdapter", "Eliminar proveedor ID: ${proveedor.id}")
                listener.onEliminar(proveedor)
            }
        }
    }
}
