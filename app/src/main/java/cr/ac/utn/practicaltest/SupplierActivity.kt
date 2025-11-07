package cr.ac.utn.practicaltest

import adapter.SupplierAdapter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import controller.SupplierController
import cr.ac.utn.practicaltest.databinding.ActivitySupplierBinding
import model.Supplier
import utils.Validators

class SupplierActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySupplierBinding
    private val supplierController = SupplierController()
    private lateinit var supplierAdapter: SupplierAdapter
    private val validators = Validators()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySupplierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.buttonCreate.setOnClickListener {
            val supplier = getSupplierFromForm()
            if (supplier != null) {
                supplierController.createSupplier(supplier)
                Toast.makeText(this, R.string.MsgSaveSuccess, Toast.LENGTH_SHORT).show()
                clearForm()
                updateRecyclerView()
            }
        }

        binding.buttonRead.setOnClickListener {
            val id = binding.editTextId.text.toString().toIntOrNull()
            if (id != null) {
                val supplier = supplierController.getSupplier(id)
                if (supplier != null) {
                    fillForm(supplier)
                } else {
                    Toast.makeText(this, R.string.MsgDataNoFound, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please enter a valid ID", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonUpdate.setOnClickListener {
            val supplier = getSupplierFromForm()
            if (supplier != null) {
                supplierController.updateSupplier(supplier)
                Toast.makeText(this, R.string.MsgSaveSuccess, Toast.LENGTH_SHORT).show()
                clearForm()
                updateRecyclerView()
            }
        }

        binding.buttonDelete.setOnClickListener {
            val id = binding.editTextId.text.toString().toIntOrNull()
            if (id != null) {
                supplierController.deleteSupplier(id)
                Toast.makeText(this, R.string.MsgDeleteSuccess, Toast.LENGTH_SHORT).show()
                clearForm()
                updateRecyclerView()
            } else {
                Toast.makeText(this, "Please enter a valid ID", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        supplierAdapter = SupplierAdapter(emptyList())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SupplierActivity)
            adapter = supplierAdapter
        }
        updateRecyclerView()
    }

    private fun updateRecyclerView() {
        supplierAdapter.updateData(supplierController.getAllSuppliers())
    }

    private fun getSupplierFromForm(): Supplier? {
        val id = binding.editTextId.text.toString().toIntOrNull()
        val name = binding.editTextName.text.toString()
        val phone = binding.editTextPhone.text.toString()
        val email = binding.editTextEmail.text.toString()
        val address = binding.editTextAddress.text.toString()
        val description = binding.editTextDescription.text.toString()

        if (id == null || name.isEmpty()) {
            Toast.makeText(this, "Please enter at least an ID and a Name", Toast.LENGTH_SHORT).show()
            return null
        }

        if (!validators.validateEmail(email)) {
            binding.editTextEmail.error = "Invalid Email"
            return null
        }

        return Supplier(id, name, phone, email, address, description)
    }

    private fun fillForm(supplier: Supplier) {
        binding.editTextId.setText(supplier.id.toString())
        binding.editTextName.setText(supplier.name)
        binding.editTextPhone.setText(supplier.phone)
        binding.editTextEmail.setText(supplier.email)
        binding.editTextAddress.setText(supplier.address)
        binding.editTextDescription.setText(supplier.description)
    }

    private fun clearForm() {
        binding.editTextId.text.clear()
        binding.editTextName.text.clear()
        binding.editTextPhone.text.clear()
        binding.editTextEmail.text.clear()
        binding.editTextAddress.text.clear()
        binding.editTextDescription.text.clear()
        binding.editTextEmail.error = null
    }
}