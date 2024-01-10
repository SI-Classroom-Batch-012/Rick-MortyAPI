package com.example.rickandmortyapi.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.rickandmortyapi.data.local.LocalDatabase
import com.example.rickandmortyapi.data.model.LocalModel
import com.example.rickandmortyapi.data.model.RemoteModel
import com.example.rickandmortyapi.data.remote.RAMAPI
import kotlin.Exception

const val TAG = "Repository"

class Repository(private val db: LocalDatabase, private val api: RAMAPI) {

    private val _listCharacters = MutableLiveData<List<RemoteModel>>()
    val listCharacters: LiveData<List<RemoteModel>>
        get() = _listCharacters

    private val _randomCharacter = MutableLiveData<RemoteModel>()
    val randomCharacter: LiveData<RemoteModel>
        get() = _randomCharacter

    private val _localCharacterList = MutableLiveData<List<LocalModel>>()
    val localCharacterList: LiveData<List<LocalModel>>
        get() = _localCharacterList

    suspend fun loadRandomCharacter() {
        val maxImages = 826
        try {
            val randomCharacter = api.retrofitService.getRandomImage((0 until maxImages).random())
            _randomCharacter.postValue(randomCharacter)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    suspend fun loadCharacterList(imageIDs: String) {
        try {
            _listCharacters.postValue(api.retrofitService.getImageList(imageIDs))
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    suspend fun loadLocalCharacters() {
        try {
            _localCharacterList.postValue(db.dao.getAll())
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    fun saveCharacterToDB(remoteModel: RemoteModel) {
        try {
            val localModel = LocalModel(
                remoteModel.id,
                remoteModel.name,
                remoteModel.species,
                remoteModel.gender,
                remoteModel.image
            )
            db.dao.insertItem(localModel)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    fun deleteCharacterFromDB(remoteModel: RemoteModel) {
        try {
            db.dao.deleteByID(remoteModel.id)
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }


}