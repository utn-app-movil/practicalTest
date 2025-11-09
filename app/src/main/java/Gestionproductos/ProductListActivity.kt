package cr.ac.utn.practicaltest.Gestionproductos

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ProductListActivity : AppCompatActivity() {

    private val repository = ProductRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: setContentView(R.layout.activity_product_list)

        title = "Gestión de Productos"

        // TODO: Aquí luego cargaremos la lista en un RecyclerView
    }
}
