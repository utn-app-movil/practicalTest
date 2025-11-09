package cr.ac.utn.practicaltest

import Controller.ServiceController
import Entity.Service
import Util.Util
import Util.ServiceAdapter
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ServiceActivity : AppCompatActivity() {

    private lateinit var controller: ServiceController
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ServiceAdapter
    private lateinit var btnAdd: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var edtName: EditText
    private lateinit var edtDescription: EditText
    private lateinit var edtPrice: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)


        controller = ServiceController(this)


        edtName = findViewById(R.id.edtServiceName)
        edtDescription = findViewById(R.id.edtServiceDescription)
        edtPrice = findViewById(R.id.edtServicePrice)
        btnAdd = findViewById(R.id.btnAddService)
        btnUpdate = findViewById(R.id.btnUpdateService)
        btnDelete = findViewById(R.id.btnDeleteService)
        recyclerView = findViewById(R.id.recyclerService)


        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ServiceAdapter(controller.getAllServices().toMutableList()) { selectedService ->
            edtName.setText(selectedService.name)
            edtDescription.setText(selectedService.description)
            edtPrice.setText(selectedService.price.toString())
        }
        recyclerView.adapter = adapter


        btnAdd.setOnClickListener {
            if (edtName.text.isEmpty() || edtDescription.text.isEmpty() || edtPrice.text.isEmpty()) {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val newService = Service(
                    id = (0..1000).random(),
                    name = edtName.text.toString(),
                    description = edtDescription.text.toString(),
                    price = edtPrice.text.toString().toDouble()
                )
                controller.addService(newService)
                clearFields()
                refreshRecycler()
                Toast.makeText(this, "Servicio agregado correctamente", Toast.LENGTH_SHORT).show()
            }
        }


        btnUpdate.setOnClickListener {
            if (edtName.text.isEmpty() || edtDescription.text.isEmpty() || edtPrice.text.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos antes de actualizar", Toast.LENGTH_SHORT).show()
            } else {
                val updatedService = Service(
                    id = controller.findServiceIdByName(edtName.text.toString()),
                    name = edtName.text.toString(),
                    description = edtDescription.text.toString(),
                    price = edtPrice.text.toString().toDouble()
                )
                controller.updateService(updatedService)
                clearFields()
                refreshRecycler()
                Toast.makeText(this, "Servicio actualizado correctamente", Toast.LENGTH_SHORT).show()
            }
        }


        btnDelete.setOnClickListener {
            val name = edtName.text.toString()
            if (name.isEmpty()) {
                Toast.makeText(this, "Ingrese el nombre del servicio a eliminar", Toast.LENGTH_SHORT).show()
            } else {
                controller.deleteServiceByName(name)
                clearFields()
                refreshRecycler()
                Toast.makeText(this, "Servicio eliminado correctamente", Toast.LENGTH_SHORT).show()
            }
        }


        val btnBack: Button = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            Util.openActivity(this, MainActivity::class.java)
            finish()
        }

    }

    private fun refreshRecycler() {
        adapter.updateData(controller.getAllServices())
    }

    private fun clearFields() {
        edtName.setText("")
        edtDescription.setText("")
        edtPrice.setText("")
    }
}
