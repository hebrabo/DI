package com.example.proyectocoffeeshops

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// La clase MainActivity es la pantalla principal de nuestra aplicación
class MainActivity : AppCompatActivity() {

    // Declaramos las variables que vamos a usar más adelante
    private lateinit var recyclerCoffee: RecyclerView   // El RecyclerView para mostrar la lista de cafés
    private lateinit var coffeeAdapter: CoffeeAdapter   // El adaptador que conectará los datos con el RecyclerView
    private lateinit var listaCoffee: List<CoffeeShop>  // Lista de objetos CoffeeShop que se mostrarán

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Se indica el layout XML que se usará para esta actividad

        // Configurar Toolbar (barra superior de la app)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar) // Establecemos la Toolbar como ActionBar
        supportActionBar?.title = "Coffee Shops" // Ponemos el título de la barra

        // Inicializar RecyclerView
        recyclerCoffee = findViewById(R.id.recyclerCoffee)
        recyclerCoffee.layoutManager = LinearLayoutManager(this)
        // LinearLayoutManager organiza los items en una lista vertical

        // Creamos una lista de ejemplo con objetos CoffeeShop
        listaCoffee = listOf(
            CoffeeShop("Antico Caffè Greco", 4.5f, "St. Italy, Rome", R.drawable.images),
            CoffeeShop("Coffee Room", 4.0f, "St. Germany, Berlin", R.drawable.images1),
            CoffeeShop("Coffee Ibiza", 4.8f, "St. Colon, Madrid", R.drawable.images2),
            CoffeeShop("Pudding Coffee Shop", 4.5f, "St. Diagonal, Barcelona", R.drawable.images3),
            CoffeeShop("L'Express", 4.0f, "St. Picadilly Circus, London", R.drawable.images4),
            CoffeeShop("Coffee Corner", 4.8f, "St. Àngel Guimerà, Valencia", R.drawable.images5),
            CoffeeShop("Sweet Cup", 4.8f, "St. Kinkerstraat, Amsterdam", R.drawable.images6)
        )
        // Cada CoffeeShop tiene: nombre, rating (Float), ubicación y un recurso de imagen

        // Inicializamos el adaptador con la lista
        coffeeAdapter = CoffeeAdapter(listaCoffee)
        recyclerCoffee.adapter = coffeeAdapter
        // Conectamos el adaptador al RecyclerView para que muestre los datos
    }
}
