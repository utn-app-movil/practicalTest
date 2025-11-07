package cr.ac.utn.practicaltest

import Controller.SponsorController
import Entity.Sponsor
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import  Util.Util

class SponsorActivity : AppCompatActivity() {
    private lateinit var sponsorController: SponsorController
    private lateinit var txtId: EditText
    private lateinit var txtNameSponsor: EditText
    private lateinit var rgSupportType: RadioGroup
    private lateinit var rbEconomic: RadioButton
    private lateinit var rbMaterial: RadioButton
    private lateinit var rbService: RadioButton
    private lateinit var txtContactName: EditText
    private lateinit var txtContactEmail: EditText
    private lateinit var txtContactPhone: EditText
    private lateinit var txtSupportDetail: EditText
    private lateinit var btnSave: ImageButton
    private lateinit var btnEdit: ImageButton
    private lateinit var btnDelete: ImageButton
    private lateinit var btnCancel: ImageButton
    private lateinit var btnSearch: ImageButton
    private var isEditMode: Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sponsor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainSponsor)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        sponsorController= SponsorController(this)
        txtId=findViewById(R.id.txtId_sponsor)
        txtNameSponsor=findViewById(R.id.txtCompanyName_sponsor)
        rgSupportType=findViewById(R.id.rgSupportType_sponsor)
        rbEconomic=findViewById(R.id.rbEconomic)
        rbMaterial=findViewById(R.id.rbMaterial)
        rbService=findViewById(R.id.rbService)
        txtContactName=findViewById(R.id.txtContactName_sponsor)
        txtContactEmail=findViewById(R.id.txtContactEmail_sponsor)
        txtContactPhone=findViewById(R.id.txtContactPhone_sponsor)
        txtSupportDetail=findViewById(R.id.txtSupportDetail_sponsor)
        btnSave=findViewById(R.id.save_button)
        btnEdit=findViewById(R.id.edit_button)
        btnDelete=findViewById(R.id.delete_button)
        btnCancel=findViewById(R.id.cancel_button)
        btnSearch=findViewById(R.id.btnSearchSponsor)


        btnSave.setOnClickListener(View.OnClickListener{view ->
            Util.showDialogCondition(this,getString(R.string.TextSaveActionQuestion),::saveSponsor)
        })

        btnCancel.setOnClickListener(View.OnClickListener{view ->
            if (isEditMode){
                Util.showDialogCondition(this,getString(R.string.TextDeleteActionQuestion),::deleteSponsor)
            }
            cleanScreenSponsor()
        })

        btnSearch.setOnClickListener(View.OnClickListener{view ->
            val id = txtId.text.toString().trim()
            if (id.isNotEmpty()) {
                searchSponsor(id)
            } else {
                Toast.makeText(this, getString(R.string.MsgIdRequired_Esteban), Toast.LENGTH_SHORT).show()
                txtId.requestFocus()
            }
        })

        btnCancel.setOnClickListener(View.OnClickListener{view ->
            cleanScreenSponsor()
        })
    }
    private fun invalidationData(): Boolean {
        val idOk = txtId.text.trim().isNotEmpty()
        val nameOk = txtNameSponsor.text.trim().isNotEmpty()
        val typeOk = when (rgSupportType.checkedRadioButtonId) {
            R.id.rbEconomic, R.id.rbMaterial, R.id.rbService -> true
            else -> false
        }
        val contactNameOk = txtContactName.text.trim().isNotEmpty()
        val contactEmailOk = android.util.Patterns.EMAIL_ADDRESS
            .matcher(txtContactEmail.text.trim()).matches()
        return  idOk &&
                nameOk &&
                typeOk &&
                contactNameOk &&
                contactEmailOk
    }
    private fun saveSponsor() {
        try {
            val sponsor= Sponsor()
            if (!invalidationData()) {
                Toast.makeText(this, getString(R.string.MsgInvalidData_Esteban), Toast.LENGTH_LONG).show()
                return
            }
            if (sponsorController.getUserById(txtId.text.toString().trim()) != null) {
                Toast.makeText(this, getString(R.string.MsgDuplicated_Esteban), Toast.LENGTH_LONG).show()
                return
            }
            val type = when (rgSupportType.checkedRadioButtonId) {
                R.id.rbEconomic -> "ECONOMICO"
                R.id.rbMaterial -> "MATERIAL"
                R.id.rbService  -> "SERVICIO"
                else -> {
                    Toast.makeText(this, getString(R.string.MsgTypeRequired_Esteban), Toast.LENGTH_LONG).show()
                    return
                }
            }
            sponsor.id = txtId.text.toString().trim()
            sponsor.nameSponsor = txtNameSponsor.text.toString().trim()
            sponsor.type= type
            sponsor.contactName = txtContactName.text.toString().trim()
            sponsor.contactEmail = txtContactEmail.text.toString().trim()
            sponsor.contactPhone = txtContactPhone.text.toString().trim()
            sponsor.detail = txtSupportDetail.text.toString().trim()
            if (!isEditMode){
                sponsorController.addSponsor(sponsor)
            }else
                sponsorController.updateSponsor(sponsor)
            cleanScreenSponsor()
            txtId.isEnabled = true
        }catch (e: Exception){
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        }
    }
    private fun deleteSponsor() {
        try {
            sponsorController.removeSponsor(txtId.text.toString().trim())
            cleanScreenSponsor()
            Toast.makeText(
                this, getString(R.string.MsgDeleteSuccess),
                Toast.LENGTH_LONG
            ).show()
        }catch (e: Exception){
            Toast.makeText(
                this, getString(R.string.ErrorMsgRemove),
                Toast.LENGTH_LONG
            ).show()
        }
    }
    private fun searchSponsor(id: String) {
        try {
            val sponsor = sponsorController.getUserById(id)
            if (sponsor != null){
                isEditMode = true
                txtId.setText(sponsor.id)
                txtNameSponsor.setText(sponsor.nameSponsor)
                when (sponsor.type) {
                    "ECONOMICO" -> rbEconomic.isChecked = true
                    "MATERIAL" -> rbMaterial.isChecked = true
                    "SERVICIO" -> rbService.isChecked = true
                }
                txtContactName.setText(sponsor.contactName)
                txtContactEmail.setText(sponsor.contactEmail)
                txtContactPhone.setText(sponsor.contactPhone)
                txtSupportDetail.setText(sponsor.detail)
            } else{
                Toast.makeText(this, getString(R.string.MsgDataNoFound), Toast.LENGTH_LONG).show()
            }
        }catch (e: Exception){
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        }
    }

    private fun cleanScreenSponsor() {
        // Campos de texto
        txtId.setText("")
        txtNameSponsor.setText("")
        txtContactName.setText("")
        txtContactEmail.setText("")
        txtContactPhone.setText("")
        txtSupportDetail.setText("")
        rgSupportType.clearCheck()
        rbEconomic.isChecked = false
        rbMaterial.isChecked = false
        rbService.isChecked  = false
        isEditMode = false
        txtId.requestFocus()
    }

}