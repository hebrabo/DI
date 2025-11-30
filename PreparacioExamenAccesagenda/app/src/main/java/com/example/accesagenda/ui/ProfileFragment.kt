package com.example.accesagenda.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.accesagenda.R
import com.example.accesagenda.databinding.FragmentProfileBinding // Importación de la clase de binding

/**
 * Fragmento que gestiona la vista del perfil del usuario y sus ajustes.
 * Es el destino de la pestaña de perfil en la barra de navegación inferior.
 */
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    // 1. View Binding: Variable backing (privada y mutable) para la instancia del binding.
    private var _binding: FragmentProfileBinding? = null

    // Propiedad calculada para acceder al binding de forma segura entre onViewCreated y onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 2. Inicializa el View Binding: Vincula el layout (fragment_profile.xml) a la clase de binding.
        _binding = FragmentProfileBinding.bind(view)

        // 3. Lógica del Perfil (Implementación futura):

        // Aquí se cargarían los datos del usuario (nombre, foto).
        // Ejemplo: binding.tvUserName.text = "Nombre del Usuario"

        // Aquí se configurarían los listeners para los botones de ajustes o logout.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 4. Limpieza: Es crucial anular la referencia del binding para prevenir
        // fugas de memoria cuando la vista del fragmento es destruida.
        _binding = null
    }
}