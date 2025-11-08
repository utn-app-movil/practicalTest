package cr.ac.utn.practicaltest

import Controller.VolunteerController
import Entity.Person
import Entity.Volunteer
import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.lang.Exception
import java.time.LocalDate

class VolunteerActivity : AppCompatActivity() {
    private lateinit var volunteerController: VolunteerController
    private var IsEditMode: Boolean=false
    private lateinit var menuitemDelete: MenuItem

    //Volunteer variables
    private lateinit var editTextIDV:  EditText
    private lateinit var spinnerVType: Spinner
    private lateinit var spinnerVTime: Spinner
    private lateinit var lbDate: TextView
    private var day: Int=0
    private var month: Int=0
    private var year: Int=0

    //Person variables
    private lateinit var editTextID: EditText
    private lateinit var editTextName: EditText
    private lateinit var editTextFLName: EditText
    private lateinit var editTextSLName: EditText
    private lateinit var editTextPhone: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextState: EditText
    private lateinit var editTextDistrict: EditText
    private lateinit var editTextAddress: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_volunteer)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //INITIALIZE THE VARIABLES
        editTextIDV = findViewById<EditText>(R.id.editTextIDV)
        spinnerVType = findViewById<Spinner>(R.id.spinnerVType)
        spinnerVTime = findViewById<Spinner>(R.id.spinnerVTime)
        lbDate = findViewById<TextView>(R.id.lbDate)

        editTextID = findViewById<EditText>(R.id.editTextID)
        editTextName = findViewById<EditText>(R.id.editTextName)
        editTextFLName = findViewById<EditText>(R.id.editTextFLName)
        editTextSLName = findViewById<EditText>(R.id.editTextSLName)
        editTextPhone = findViewById<EditText>(R.id.editTextPhone)
        editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        editTextState = findViewById<EditText>(R.id.editTextState)
        editTextDistrict = findViewById<EditText>(R.id.editTextDistrict)
        editTextAddress = findViewById<EditText>(R.id.editTextAddress)

        //SPINNER TYPE OF VOLUNTEER
        val spinnerVType = spinnerVType
        //Voluntariados existentes
        val options = arrayOf("Apoyo en albergues", "Charlas a comunidades.", "Limpieza de playas.", "Ayuda a adultos mayores.", "Tutorías", "Reforestación")

        val spinnerSetText = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        spinnerSetText.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerVType.adapter = spinnerSetText

        //-----------------------------------------------------------------------
        //SPINNER TIME OF VOLUNTEER
        val spinnerVTime = spinnerVTime
        //Voluntariados existentes
        val options2 = arrayOf("8:00 p.m.", "9:00 p.m.", "10:00 p.m.", "1:00 p.m.", "2:00 p.m.")

        val spinnerSetText2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, options2)
        spinnerSetText2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerVTime.adapter = spinnerSetText2

        val btnSelectDate = findViewById<ImageButton>(R.id.btnDateV)
        btnSelectDate.setOnClickListener(View.OnClickListener{ view ->
            showDatePickerDialog()
        })
    }

    private fun getDateString(dayValue: Int, monthValue: Int, yearValue: Int): String{
        //Si el dato es menor a 10 le pone un 0 delante, si no, no.
        return "${if (dayValue < 10) "0" else ""}$dayValue/${if (monthValue < 10) "0" else ""}$monthValue/$yearValue"
    }

    fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        lbDate.text = getDateString(dayOfMonth, month-1, year)
    }

    private fun ResetDate(){
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)+1
        day = calendar.get(Calendar.DAY_OF_MONTH)

    }

    /*
    private fun searchPerson(id: String){
        try {
            val person = personController.getById(
                txtID.text.trim().toString())
            if (person != null){
                IsEditMode=true
                txtID.isEnabled=false
                txtName.setText(person.Name)
                txtFLastName.setText(person.FLastName)
                txtSLastName.setText(person.SLastName)
                txtEmail.setText(person.Email)
                txtPhone.setText(person.Phone.toString())
                txtProvince.setText(person.Province.Name)
                txtState.setText(person.State)
                txtDistrict.setText(person.District)
                txtAddress.setText(person.Address)
                lbBirthdate.setText(
                    getDateString(person.Birthdate.dayOfMonth
                        , person.Birthdate.month.value
                        , person.Birthdate.year)
                )
                menuitemDelete.isVisible = IsEditMode
                day= person.Birthdate.dayOfMonth
                month = person.Birthdate.month.value
                year = person.Birthdate.year
            }else{
                Toast.makeText(this
                    , getString(R.string.MsgDataNotFound)
                    , Toast.LENGTH_LONG).show()
            }
        }catch (e: java.lang.Exception){
            Toast.makeText(this, e.message.toString()
                , Toast.LENGTH_LONG).show()
        }
    }
    */

    private fun showDatePickerDialog(){
        val datePickerDialog = DatePickerDialog(this, this, year, month-1, day)
        datePickerDialog.show()
    }

    private fun cleanScreen(){
        IsEditMode=false
        ResetDate()
        editTextID.isEnabled=true
        editTextID.setText("")
        editTextName.setText("")
        editTextFLName.setText("")
        editTextSLName.setText("")
        editTextPhone.setText("")
        editTextEmail.setText("")
        editTextState.setText("")
        editTextDistrict.setText("")
        editTextAddress.setText("")
        invalidateOptionsMenu()
    }

    fun isValidatedData(): Boolean{
        val dateparse= Util.Util.parseStringToDateModern(lbDate.text.toString(), "dd/MM/yyyy")
        return editTextID.text.trim().isNotEmpty() && editTextName.text.trim().isNotEmpty()
                && editTextFLName.text.trim().isNotEmpty() && editTextSLName.text.trim().isNotEmpty()
                && editTextEmail.text.trim().isNotEmpty() && lbDate.text.trim().isNotEmpty()
                && editTextState.text.trim().isNotEmpty() && editTextDistrict.text.trim().isNotEmpty()
                && editTextAddress.text.trim().isNotEmpty() && (editTextPhone.text.trim().isNotEmpty()
                && editTextPhone.text.trim().length >= 8 && editTextPhone.text.toString()?.toInt()!! != null && editTextPhone.text.toString().trim() != "0")
                && dateparse != null
    }

    fun saveVolunteer(){
        try {
            if (isValidatedData()){
                if (volunteerController.getById(editTextID.text.toString()) != null && !IsEditMode){
                    Toast.makeText(this, "The info is duplicated"
                        , Toast.LENGTH_LONG).show()
                }else{
                    val volunteer = Volunteer()
                    volunteer.ID = editTextIDV.text.toString()
                    volunteer.Name = spinnerVType.selectedItem.toString()
                    val date =Util.Util.parseStringToDateModern(lbDate.text.toString(), "dd/MM/yyyy")
                    volunteer.Date = LocalDate.of(date?.year!!, date.month.value, date?.dayOfMonth!!)
                    volunteer.Hour = spinnerVTime.selectedItem.toString()

                    val person = Person()
                    person.ID = editTextID.text.toString()
                    person.Name = editTextName.text.toString()
                    person.FLastName = editTextFLName.text.toString()
                    person.SLastName = editTextSLName.text.toString()
                    person.Email = editTextEmail.text.toString()
                    person.Phone = editTextPhone.text.toString()?.toInt()!!
                    person.State = editTextState.text.toString()
                    person.District = editTextDistrict.text.toString()
                    person.Address= editTextAddress.text.toString()
                    volunteer.personList.add(person)

                    if (!IsEditMode)
                        volunteerController.addVolunteer(volunteer)
                    else
                        volunteerController.updateVolunteer(volunteer)

                    cleanScreen()
                    Toast.makeText(this, R.string.MsgSaveSuccess
                        , Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Falta información"
                    , Toast.LENGTH_LONG).show()
            }
        }catch (e: java.lang.Exception){
            Toast.makeText(this, e.message.toString()
                , Toast.LENGTH_LONG).show()
        }
    }

    fun deleteVolunteer(){
        try {
            volunteerController.removeVolunteer(editTextID.text.trim().toString())
            cleanScreen()
            Toast.makeText(this, getString(R.string.MsgDeleteSuccess)
                , Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            Toast.makeText(this, e.message.toString()
                , Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_crud, menu)
        menuitemDelete = menu!!.findItem(R.id.mnu_delete)
        menuitemDelete.isVisible = IsEditMode
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mnu_save -> {
                if (IsEditMode){
                    Util.Util.showDialogCondition(this
                        , "Desea guardar la info?"
                        , { saveVolunteer() })
                }else
                    saveVolunteer()
                true
            }
            R.id.mnu_delete -> {
                Util.Util.showDialogCondition(this
                    , "Desea guardar la información?"
                    ,{ deleteVolunteer() })
                return true
            }
            R.id.mnu_cancel -> {
                cleanScreen()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}