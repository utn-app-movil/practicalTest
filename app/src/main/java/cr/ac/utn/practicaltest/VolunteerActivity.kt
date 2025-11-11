package cr.ac.utn.practicaltest

import Controller.PersonController
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

class VolunteerActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener{
    //System variables
    private lateinit var volunteerController: VolunteerController
    private lateinit var personController: PersonController
    private var IsEditMode: Boolean=false
    private lateinit var menuitemDelete: MenuItem
    //-----------------------------------------------------------
    //Volunteer variables
    private lateinit var editTextIDV:  EditText
    private lateinit var spinnerVType: Spinner
    private lateinit var spinnerVTime: Spinner
    private lateinit var lbDate: TextView
    private var day: Int=0
    private var month: Int=0
    private var year: Int=0
    private lateinit var editTextIdPerson: EditText
    //-----------------------------------------------------------
    //Person variables
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
        volunteerController = VolunteerController(this)
        personController = PersonController(this)
        //VOLUNTEER
        editTextIDV = findViewById<EditText>(R.id.editTextIdV)
        spinnerVType = findViewById<Spinner>(R.id.spinnerVType)
        spinnerVTime = findViewById<Spinner>(R.id.spinnerVTime)
        lbDate = findViewById<TextView>(R.id.lbDate)
        editTextIdPerson = findViewById<EditText>(R.id.editTextIdPerson)
        //PERSON
        editTextName = findViewById<EditText>(R.id.editTextName)
        editTextFLName = findViewById<EditText>(R.id.editTextFLName)
        editTextSLName = findViewById<EditText>(R.id.editTextSLName)
        editTextPhone = findViewById<EditText>(R.id.editTextPhone)
        editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        editTextState = findViewById<EditText>(R.id.editTextState)
        editTextDistrict = findViewById<EditText>(R.id.editTextDistrict)
        editTextAddress = findViewById<EditText>(R.id.editTextAddress)

        //-----------------------------------------------------------------------
        //SPINNER TYPE OF VOLUNTEER
        val spinnerVType = spinnerVType
        val optionsVol = arrayOf("Apoyo en albergues", "Charlas a comunidades.", "Limpieza de playas.", "Ayuda a adultos mayores.", "Tutorías", "Reforestación")

        val spinnerVNSetText = ArrayAdapter(this, android.R.layout.simple_spinner_item, optionsVol)
        spinnerVNSetText.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerVType.adapter = spinnerVNSetText
        //-----------------------------------------------------------------------
        //SPINNER TIME OF VOLUNTEER
        val spinnerVTime = spinnerVTime
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======
        //Voluntariados existentes
>>>>>>> Stashed changes
=======
        //Voluntariados existentes
>>>>>>> Stashed changes
        val optionsVolTime = arrayOf("8:00 p.m.", "9:00 p.m.", "10:00 p.m.", "1:00 p.m.", "2:00 p.m.")

        val spinnerVTSetText = ArrayAdapter(this, android.R.layout.simple_spinner_item, optionsVolTime)
        spinnerVTSetText.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerVTime.adapter = spinnerVTSetText
        //-----------------------------------------------------------------------


        val btnSelectDate = findViewById<ImageButton>(R.id.btnDateV)
        btnSelectDate.setOnClickListener(View.OnClickListener{ view ->
            showDatePickerDialog()
        })

        ResetDate()

        val btnSearchIdV = findViewById<ImageButton>(R.id.btnSearchIdV)
        btnSearchIdV.setOnClickListener(View.OnClickListener{ view ->
            searchVolunteer(editTextIDV.text.trim().toString())
        })
    }

    fun saveVolunteer(){
        try {
            if (isValidatedData()){
                if (volunteerController.getVolunteerById(editTextIDV.text.toString()) != null && !IsEditMode){
<<<<<<< Updated upstream
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
                    Toast.makeText(this, "The info is duplicated"
                        , Toast.LENGTH_LONG).show()
                }else{
                    val volunteer = Volunteer()
                    volunteer.ID = editTextIDV.text.toString()
                    volunteer.Name = spinnerVType.selectedItem.toString()
                    val date =Util.Util.parseStringToDateModern(lbDate.text.toString(), "dd/MM/yyyy")
                    volunteer.Date = LocalDate.of(date?.year!!, date.month.value, date?.dayOfMonth!!)
                    volunteer.Hour = spinnerVTime.selectedItem.toString()
                    volunteer.IdPerson = editTextIdPerson.text.toString()

                    val person = Person()
                    person.ID = editTextIdPerson.text.toString()
<<<<<<< Updated upstream
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
                    person.Name = editTextName.text.toString()
                    person.FLastName = editTextFLName.text.toString()
                    person.SLastName = editTextSLName.text.toString()
                    person.Email = editTextEmail.text.toString()
                    person.Phone = editTextPhone.text.toString()?.toInt()!!
                    person.State = editTextState.text.toString()
                    person.District = editTextDistrict.text.toString()
                    person.Address= editTextAddress.text.toString()
                    volunteer.personList.add(person)
                    if (!IsEditMode) {
                        volunteerController.addVolunteer(volunteer)
                        personController.addPerson(person)
                    }else {
                        volunteerController.updateVolunteer(volunteer)
                        personController.updatePerson(person)
                    }
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
            volunteerController.removeVolunteer(editTextIDV.text.trim().toString())
            personController.removePerson(editTextIdPerson.text.trim().toString())
<<<<<<< Updated upstream
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
            cleanScreen()
            Toast.makeText(this, getString(R.string.MsgDeleteSuccess)
                , Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            Toast.makeText(this, e.message.toString()
                , Toast.LENGTH_LONG).show()
        }
    }

    private fun getDateString(dayValue: Int, monthValue: Int, yearValue: Int): String{
        return "${if (dayValue < 10) "0" else ""}$dayValue/${if (monthValue < 10) "0" else ""}$monthValue/$yearValue"
    }

    private fun ResetDate(){
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)+1
        day = calendar.get(Calendar.DAY_OF_MONTH)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        lbDate.text = getDateString(dayOfMonth, month-1, year)
    }

    private fun searchVolunteer(id: String){
        try {
            val volunteer = volunteerController.getVolunteerById(
                editTextIDV.text.trim().toString())
            if (volunteer != null){
                IsEditMode=true
                editTextIdPerson.isEnabled=false

                val positionType = (spinnerVType.adapter as ArrayAdapter<String>).getPosition(volunteer.Name)
                spinnerVType.setSelection(positionType)

                lbDate.setText(
                    getDateString(volunteer.Date.dayOfMonth
                        , volunteer.Date.month.value
                        , volunteer.Date.year)
                )
                menuitemDelete.isVisible = IsEditMode
                day = volunteer.Date.dayOfMonth
                month = volunteer.Date.month.value
                year = volunteer.Date.year

                val positionTime = (spinnerVTime.adapter as ArrayAdapter<String>).getPosition(volunteer.Hour)
                spinnerVTime.setSelection(positionTime)

                //ESTO DEBE EXTRAERSE DE LA LISTA
                editTextIdPerson.setText(volunteer.IdPerson)

                val personData = personController.getById(volunteer.IdPerson)
                if (personData!=null){
                    editTextFLName.setText(personData.FLastName)
                    editTextSLName.setText(personData.SLastName)
                    editTextEmail.setText(personData.Email)
                    editTextPhone.setText(personData.Phone.toString())
                    editTextState.setText(personData.State)
                    editTextDistrict.setText(personData.District)
                    editTextAddress.setText(personData.Address)
                }
            }else{
                Toast.makeText(this
                    , "Info not found"
                    , Toast.LENGTH_LONG).show()
            }
        }catch (e: java.lang.Exception){
            Toast.makeText(this, e.message.toString()
                , Toast.LENGTH_LONG).show()
        }
    }

    private fun showDatePickerDialog(){
        val datePickerDialog = DatePickerDialog(this, this, year, month - 1, day)
        datePickerDialog.show()
    }

    fun isValidatedData(): Boolean{
        val dateparse= Util.Util.parseStringToDateModern(lbDate.text.toString(), "dd/MM/yyyy")
        return editTextIDV.text.trim().isNotEmpty() && editTextIdPerson.text.trim().isNotEmpty()
                && editTextName.text.trim().isNotEmpty() && editTextFLName.text.trim().isNotEmpty()
                && editTextEmail.text.trim().isNotEmpty() && editTextState.text.trim().isNotEmpty()
                && lbDate.text.trim().isNotEmpty() && editTextDistrict.text.trim().isNotEmpty()
                && editTextDistrict.text.trim().isNotEmpty() && editTextAddress.text.trim().isNotEmpty()
                && (editTextPhone.text.trim().isNotEmpty() && editTextPhone.text.trim().length >= 8
                && editTextPhone.text.toString()?.toInt()!! != null && editTextPhone.text.toString().trim() != "0")
                && dateparse != null
    }

    private fun cleanScreen(){
        IsEditMode=false
        editTextIDV.isEnabled=true
        editTextIDV.setText("")
        spinnerVType.setSelection(0)
        ResetDate()
        spinnerVTime.setSelection(0)
        editTextIdPerson.setText("")
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
                    , "Desea borrar la información?"
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
<<<<<<< Updated upstream
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
=======

>>>>>>> Stashed changes
}