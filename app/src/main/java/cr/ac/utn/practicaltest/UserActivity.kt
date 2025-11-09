package cr.ac.utn.practicaltest

import Controller.UserController
import Entity.User
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UserActivity : AppCompatActivity() {
    lateinit var txtID: EditText
    lateinit var txtName: EditText
    lateinit var txtFLastName: EditText
    lateinit var txtSLastName: EditText
    lateinit var txtPassword: EditText
    lateinit var txtEmail: EditText
    lateinit var listView: ListView

    private lateinit var userController: UserController
    private var isEditMode: Boolean = false

    private lateinit var menuItemDelete: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.TableLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userController = UserController(this)

        txtID = findViewById<EditText>(R.id.txtID_user)
        txtName = findViewById<EditText>(R.id.txtName_user)
        txtFLastName = findViewById<EditText>(R.id.txtFLastName_user)
        txtSLastName = findViewById<EditText>(R.id.txtSLastName_user)
        txtPassword = findViewById<EditText>(R.id.txtPassword_user)
        txtEmail = findViewById<EditText>(R.id.txtEmail_user)
        listView = findViewById<ListView>(R.id.listView_user)

        val btnSearch = findViewById<ImageButton>(R.id.btnSearchId_user)
        btnSearch.setOnClickListener {
            searchUser(txtID.text.trim().toString())
        }

        loadUsers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_crud, menu)
        menuItemDelete= menu!!.findItem(R.id.mnu_delete)
        menuItemDelete.isVisible = isEditMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mnu_save ->{
                if (isEditMode){
                    Util.Util.showDialogCondition(this,
                        getString(R.string.TextSaveActionQuestion),
                        { saveUser() })
                } else {
                    saveUser()
                }

                return true
            }

            R.id.mnu_delete ->{
                Util.Util.showDialogCondition(this,
                    getString(R.string.TextDeleteActionQuestion),
                    { deleteUser() })

                return true
            }

            R.id.mnu_cancel ->{
                cleanScreen()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    fun isValidationData(): Boolean {
        return txtID.text.trim().isNotEmpty() && txtName.text.trim().isNotEmpty()
                && txtFLastName.text.trim().isNotEmpty() && txtSLastName.text.trim().isNotEmpty()
                && txtEmail.text.trim().isNotEmpty() && txtPassword.text.trim().isNotEmpty()
    }

    private fun cleanScreen() {
        isEditMode = false
        txtID.isEnabled = true
        txtID.setText("")
        txtName.setText("")
        txtFLastName.setText("")
        txtSLastName.setText("")
        txtEmail.setText("")
        txtPassword.setText("")

        invalidateOptionsMenu()
    }

    private fun searchUser(id: String){
        try {
            val user = userController.getById(id)
            if (user != null) {
                isEditMode = true
                txtID.setText(user.ID.toString())
                txtID.isEnabled = false
                txtName.setText(user.Name)
                txtFLastName.setText(user.FLastName)
                txtSLastName.setText(user.SLastName)
                txtEmail.setText(user.Email)
                txtPassword.setText(user.Password.toString())

                menuItemDelete.isVisible = true
            } else {
                Toast.makeText(this, getString(R.string.MsgDataNoFound),
                    Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            cleanScreen()
            Toast.makeText(this, e.message.toString(),
                Toast.LENGTH_LONG).show()
        }
    }

    fun loadUsers() {
        try {
            val listUsers = userController.getAll()
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,
                listUsers)
            listView.adapter = adapter

            listView.setOnItemClickListener { parent, view, position, id ->
                val userSelected = listUsers[position]
                val userID = userSelected.toString().split(" - ")[0]
                searchUser(userID)
            }
        } catch (e: Exception) {
            Toast.makeText(
                this, e.message.toString(),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun saveUser(){
        try {
            if (isValidationData()) {
                if (userController.getById(txtID.text.toString().trim()) != null
                    && !isEditMode){
                    Toast.makeText(this, getString(R.string.MsgDuplicateDate),
                        Toast.LENGTH_LONG).show()
                } else {
                    val user = User()
                    user.ID = txtID.text.toString()
                    user.Name = txtName.text.toString()
                    user.FLastName = txtFLastName.text.toString()
                    user.SLastName = txtSLastName.text.toString()
                    user.Email = txtEmail.text.toString()
                    user.Password = txtPassword.text.toString()

                    if (!isEditMode)
                        userController.addUser(user)
                    else
                        userController.updateUser(user)

                    cleanScreen()
                    loadUsers()

                    Toast.makeText(this, getString(R.string.MsgSaveSuccess),
                        Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.MsgIncompleteData),
                    Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception){
            Toast.makeText(this, e.message.toString(),
                Toast.LENGTH_LONG).show()
        }
    }

    fun deleteUser(): Unit{
        try {
            userController.removeUser(txtID.text.toString())
            cleanScreen()
            loadUsers()
            Toast.makeText(this, getString(R.string.MsgDeleteSuccess),
                Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(),
                Toast.LENGTH_LONG).show()
        }
    }
}