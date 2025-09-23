package com.example.appinterface.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Api.Proveedores
import com.example.appinterface.Usuarios
import com.example.appinterface.R

class ProveedoresAdapter(private val proveedores: List<Proveedores>) :
    RecyclerView.Adapter<ProveedoresAdapter.ProveedorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProveedorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_proveedores, parent, false)
        return ProveedorViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProveedorViewHolder, position: Int) {
        val proveedor = proveedores[position]
        holder.bind(proveedor)
    }

    override fun getItemCount(): Int = proveedores.size

    class ProveedorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val Nombre: TextView = itemView.findViewById(R.id.Nombre)
        private val Descripcion: TextView = itemView.findViewById(R.id.Descripcion)

        fun bind(proveedor: Proveedores) {
            Nombre.text = "${proveedor.nombre}"
            Descripcion.text = proveedor.descripcion
        }
    }
}
