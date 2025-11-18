package cr.ac.utn.practicaltest

import Controller.TournamentController
import Entity.Tournament
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ClipDescription
import android.content.DialogInterface
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.util.Calendar

class EntertainmentActivity: AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var txtId: EditText
    private lateinit var txtTitle: EditText
    private lateinit var txtDescription: EditText
    private lateinit var txtDate: TextView
    private lateinit var txtPrize: EditText
    private var isEditMode: Boolean=false
    private var year = 0
    private var month = 0
    private var day = 0
    private lateinit var tournamentController: TournamentController
    private lateinit var menuitemDelete: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tournaments)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tournamentController = TournamentController(this)
        txtId= findViewById<EditText>(R.id.txtId_tournament)
        txtTitle= findViewById<EditText>(R.id.txtTitle_tournament)
        txtDescription= findViewById<EditText>(R.id.txtDescription_tournament)
        txtDate= findViewById<EditText>(R.id.lbFecha_tournament)
        txtPrize= findViewById<EditText>(R.id.txtPrize_tournament)

        ResetDate()

        val btnSelectDate = findViewById<ImageButton>(R.id.btnSelectDate_tournament)
        btnSelectDate.setOnClickListener(View.OnClickListener{view ->
            showDatePickerDialog()
        })

        val btnSearch = findViewById<ImageButton>(R.id.btnSearchId_tournament)
        btnSearch.setOnClickListener(View.OnClickListener{view ->
            searchTournament(txtId.text.trim().toString())
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_crud, menu)
        menuitemDelete = menu!!.findItem(R.id.mnu_delete)
        if (isEditMode)
            menuitemDelete.isVisible = true
        else
            menuitemDelete.isVisible =false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.mnu_save -> {
                if (isEditMode){
                    Util.Util.showDialogCondition(this
                        , getString(R.string.TextSaveActionQuestion)
                        , { saveTournament() })
                }else
                    saveTournament()
                true
            }
            R.id.mnu_delete -> {
                Util.Util.showDialogCondition(this
                    , getString(R.string.TextDeleteActionQuestion)
                    ,{ deleteTournament() })
                return true
            }
            R.id.mnu_cancel -> {
                cleanScreen()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun searchTournament(id: String){
        try {
            val tournament = tournamentController.getById(id)
            if (tournament != null){
                isEditMode = true
                txtId.setText(tournament.ID.toString())
                txtId.isEnabled=false
                txtTitle.setText(tournament.Title)
                txtDescription.setText(tournament.Description)
                txtDate.setText(getDateFormatString(tournament.Date.dayOfMonth, tournament.Date.month.value, tournament.Date.year, ))
                txtPrize.setText(tournament.Prize)
                year = tournament.Date.year
                month = tournament.Date.month.value
                day = tournament.Date.dayOfMonth
                menuitemDelete.isVisible = true
            }else
                Toast.makeText(this, R.string.MsgDataNoFound
                    , Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            cleanScreen()
            Toast.makeText(this, e.message.toString(),Toast.LENGTH_LONG).show()
        }
    }

    fun cleanScreen(){
        ResetDate()
        isEditMode = false
        txtId.isEnabled=true
        txtId.setText("")
        txtTitle.setText("")
        txtDescription.setText("")
        txtDate.text = ""
        txtPrize.setText("")
        invalidateOptionsMenu()
    }

    private fun ResetDate(){
        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(this, this, year, month, day)
        datePickerDialog.show()
    }

    fun getDateFormatString(dayOfMonth: Int, monthValue: Int, yearValue: Int): String{
        return "${if (dayOfMonth < 10) "0" else "" }$dayOfMonth/${if (monthValue < 10) "0" else "" }$monthValue/$yearValue"
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // Month is 0-indexed in Calendar, so add 1 for display
        txtDate.text = getDateFormatString(dayOfMonth, month+1, year)
    }

    fun saveTournament(){
        try {
            if (tournamentController.getById(txtId.text.toString()) != null && !isEditMode){
                Toast.makeText(this, R.string.MsgDuplicateData
                    , Toast.LENGTH_LONG).show()
            }else {
                val tournament = Tournament()
                tournament.ID = txtId.text.toString()
                tournament.Title = txtTitle.text.toString()
                tournament.Description = txtDescription.text.toString()
                val date2 = Util.Util.parseStringToDateModern(txtDate.text.toString(), "dd/MM/yyyy")
                tournament.Date =
                    LocalDate.of(date2?.year!!, date2.month.value, date2?.dayOfMonth!!)
                tournament.Prize = txtPrize.text.toString()

                if (!isEditMode)
                    tournamentController.addTournament(tournament)
                else
                    tournamentController.updateTournament(tournament)

                cleanScreen()
                Toast.makeText(
                    this, R.string.MsgSaveSuccess, Toast.LENGTH_LONG
                ).show()
            }
        }catch (e: Exception){
                Toast.makeText(this, e.message.toString()
                    , Toast.LENGTH_LONG).show()
        }
    }

    fun deleteTournament(): Unit{
        try {
            tournamentController.removeTournament(txtId.text.toString())
            cleanScreen()
            Toast.makeText(this, R.string.MsgDeleteSuccess
                , Toast.LENGTH_LONG).show()
        }catch (e: Exception){
            Toast.makeText(this, e.message.toString()
                , Toast.LENGTH_LONG).show()
        }
    }


}