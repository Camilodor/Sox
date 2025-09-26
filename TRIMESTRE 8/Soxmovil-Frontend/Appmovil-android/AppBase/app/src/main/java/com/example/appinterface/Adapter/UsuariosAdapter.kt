package com.example.appinterface.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.R
import com.example.appinterface.Usuarios

class UsuariosAdapter(
    private val usuarios: MutableList<Usuarios>,
    private val listener: OnUsuarioClickListener
) : RecyclerView.Adapter<UsuariosAdapter.UsuarioViewHolder>() {
    fun getUsuarios(): List<Usuarios> {
        return usuarios // <- tu lista interna
    }


    interface OnUsuarioClickListener {
        fun onConsultar(usuario: Usuarios)   // Clic en la fila
        fun onEditar(usuario: Usuarios)      // Botón editar
        fun onEliminar(usuario: Usuarios)    // Botón eliminar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuarios, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = usuarios[position]
        holder.bind(usuario, listener)
    }

    override fun getItemCount(): Int = usuarios.size

    fun updateData(newList: List<Usuarios>) {
        usuarios.clear()
        usuarios.addAll(newList)
        notifyDataSetChanged()
        Log.d("UsuariosAdapter", "Lista de usuarios actualizada. Total: ${usuarios.size}")
    }

    class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvId: TextView = itemView.findViewById(R.id.tvId)
        private val tvUsuario: TextView = itemView.findViewById(R.id.tvUsuario)
        private val tvNombres: TextView = itemView.findViewById(R.id.tvNombres)
        private val tvDocumento: TextView = itemView.findViewById(R.id.tvDocumento)
        private val btnEditar: ImageButton = itemView.findViewById(R.id.btnEditar)
        private val btnEliminar: ImageButton = itemView.findViewById(R.id.btnEliminar)
        private val rowLayout: LinearLayout = itemView.findViewById(R.id.rowLayout)

        fun bind(usuario: Usuarios, listener: OnUsuarioClickListener) {
            tvId.text = usuario.id.toString()
            tvUsuario.text = usuario.nombre_usuario
            tvNombres.text = "${usuario.nombres} ${usuario.apellidos}"
            tvDocumento.text = usuario.numero_documento.toString()

            // --- Click en la fila completa para consultar ---
            rowLayout.setOnClickListener {
                Log.d("UsuariosAdapter", "Click en fila usuario ID: ${usuario.id}")
                listener.onConsultar(usuario)
            }

            // --- Acciones específicas ---
            btnEditar.setOnClickListener {
                Log.d("UsuariosAdapter", "Editar usuario ID: ${usuario.id}")
                listener.onEditar(usuario)
            }

            btnEliminar.setOnClickListener {
                Log.d("UsuariosAdapter", "Eliminar usuario ID: ${usuario.id}")
                listener.onEliminar(usuario)
            }
        }
    }
}
