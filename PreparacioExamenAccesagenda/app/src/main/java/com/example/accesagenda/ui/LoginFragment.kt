package com.example.accesagenda.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController // Importación para manejar la navegación
import com.example.accesagenda.R
import com.example.accesagenda.databinding.FragmentLoginBinding // Importación de la clase de binding generada

/**
 * Fragmento que gestiona la vista de inicio de sesión o bienvenida.
 * Actualmente, solo maneja la transición a la pantalla de inicio (HomeFragment).
 */
class LoginFragment : Fragment(R.layout.fragment_login) {

    // 1. View Binding: Variable backing (privada y mutable) para la instancia del binding.
    private var _binding: FragmentLoginBinding? = null

    // Propiedad calculada (pública e inmutable) para acceder al binding de forma segura.
    // Solo se debe llamar entre onViewCreated y onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 2. Inicializa el View Binding: Vincula el layout (fragment_login.xml) a la clase de binding.
        _binding = FragmentLoginBinding.bind(view)

        // 3. Configuración del Listener de Clic para el botón principal.
        binding.btnContinue.setOnClickListener {
            // Utiliza el NavController para ejecutar la acción de navegación definida en el grafo
            // desde LoginFragment hacia HomeFragment.
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
    }

    // 4. Limpieza: Es crucial anular la referencia del binding en onDestroyView.
    // Esto previene las fugas de memoria, ya que la vista (y por lo tanto el binding)
    // sobreviven menos tiempo que el propio Fragmento.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}