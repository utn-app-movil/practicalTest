package Controller

import Data.IDataManager
import Data.IDataManagerSponsor
import Data.MemoryDataManagerSponsor
import Entity.Sponsor
import android.content.Context
import cr.ac.utn.practicaltest.R
import kotlin.collections.remove

class SponsorController {
    private var dataManager: IDataManagerSponsor = MemoryDataManagerSponsor
    private var context: Context

    constructor(context: Context) {
        this.context = context
    }

    // Add Sponsor
    fun addSponsor(sponsor: Sponsor) {
        try {
            dataManager.add(sponsor)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgAdd))
        }
    }

    // Update Sponsor
    fun updateSponsor(sponsor: Sponsor) {
        try {
            dataManager.update(sponsor)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgUpdate))
        }
    }

    // Remove Sponsor
    fun removeSponsor(id: String){
        try {
            val result = dataManager.getById(id)
            if (result == null) {
                throw Exception(context.getString(R.string.ErrorMsgGetById))
            }
            dataManager.remove(id)
        }catch (e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgRemove))
        }
    }

    // Get Sponsor By ID
    fun getUserById(id: String): Sponsor ?{
        try {
            return dataManager.getById(id)
        }catch (e: Exception){
            throw Exception(context.getString(R.string.ErrorMsgGetById))
        }
    }

    // Get by Name
    fun getSponsorByName(name: String): Sponsor ? {
        try {
            return dataManager.getByName(name)
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgGetById))
        }
    }

    // Get All
    fun getAllSponsors(): List<Sponsor> {
        try {
            return dataManager.getAll()
        } catch (e: Exception) {
            throw Exception(context.getString(R.string.ErrorMsgGetAll))
        }
    }

}