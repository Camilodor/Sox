package com.example.appinterface.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appinterface.Usuarios
import com.example.appinterface.R

class UsuariosAdapter(private val usuarios: List<Usuarios>) :
    RecyclerView.Adapter<UsuariosAdapter.UsuarioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_usuarios, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = usuarios[position]
        holder.bind(usuario)
    }

    override fun getItemCount(): Int = usuarios.size

    class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        private val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)

        fun bind(usuario: Usuarios) {
            tvNombre.text = "${usuario.nombres} ${usuario.apellidos}"
            tvEmail.text = usuario.email
        }
    }
}
