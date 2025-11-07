package cr.ac.utn.practicaltest

import Data.MemoryDataManager
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rent)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtId = findViewById<EditText>(R.id.txtId)
        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtDescripcion = findViewById<EditText>(R.id.txtDescripcion)
        val txtPrecio = findViewById<EditText>(R.id.txtPrecio)
        val chkDisponible = findViewById<CheckBox>(R.id.chkDisponible)
        val tvResultado = findViewById<TextView>(R.id.tvResultado)

        val btnAgregar = findViewById<Button>(R.id.btnAgregar)
        val btnActualizar = findViewById<Button>(R.id.btnActualizar)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)
        val btnMostrar = findViewById<Button>(R.id.btnMostrar)

        btnAgregar.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val descripcion = txtDescripcion.text.toString()
            val precio = txtPrecio.text.toString().toDoubleOrNull()
            val disponible = chkDisponible.isChecked

            if (nombre.isEmpty() || descripcion.isEmpty() || precio == null) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                MemoryDataManager.agregarEquipo(nombre, descripcion, precio, disponible)
                Toast.makeText(this, "Equipo agregado", Toast.LENGTH_SHORT).show()
                limpiarCampos(txtId, txtNombre, txtDescripcion, txtPrecio, chkDisponible)
            }
        }

        btnActualizar.setOnClickListener {
            val id = txtId.text.toString().toIntOrNull()
            val nombre = txtNombre.text.toString()
            val descripcion = txtDescripcion.text.toString()
            val precio = txtPrecio.text.toString().toDoubleOrNull()
            val disponible = chkDisponible.isChecked

            if (id == null || nombre.isEmpty() || descripcion.isEmpty() || precio == null) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val exito = MemoryDataManager.actualizarEquipo(id, nombre, descripcion, precio, disponible)
                if (exito) {
                    Toast.makeText(this, "Equipo actualizado", Toast.LENGTH_SHORT).show()
                    limpiarCampos(txtId, txtNombre, txtDescripcion, txtPrecio, chkDisponible)
                } else {
                    Toast.makeText(this, "No se encontró el ID", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnEliminar.setOnClickListener {
            val id = txtId.text.toString().toIntOrNull()
            if (id == null) {
                Toast.makeText(this, "Ingrese un ID válido", Toast.LENGTH_SHORT).show()
            } else {
                val exito = MemoryDataManager.eliminarEquipo(id)
                if (exito) {
                    Toast.makeText(this, "Equipo eliminado", Toast.LENGTH_SHORT).show()
                    limpiarCampos(txtId, txtNombre, txtDescripcion, txtPrecio, chkDisponible)
                } else {
                    Toast.makeText(this, "No se encontró el ID", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnMostrar.setOnClickListener {
            val lista = MemoryDataManager.obtenerEquipos()
            if (lista.isEmpty()) {
                tvResultado.text = "No hay equipos registrados"
            } else {
                val texto = lista.joinToString("\n\n") {
                    "ID: ${it.id}\n" +
                            "Nombre: ${it.nombre}\n" +
                            "Descripción: ${it.descripcion}\n" +
                            "Precio: ₡${it.precio}\n" +
                            "Disponible: ${if (it.disponible) "Sí" else "No"}"
                }
                tvResultado.text = texto
            }
        }
    }

    private fun limpiarCampos(id: EditText, nombre: EditText, desc: EditText, precio: EditText, chk: CheckBox) {
        id.text.clear()
        nombre.text.clear()
        desc.text.clear()
        precio.text.clear()
        chk.isChecked = false
    }
}