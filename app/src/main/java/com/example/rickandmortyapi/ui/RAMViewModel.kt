package com.example.rickandmortyapi.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapi.data.Repository
import com.example.rickandmortyapi.data.local.getDatabase
import com.example.rickandmortyapi.data.model.RemoteModel
import com.example.rickandmortyapi.data.remote.RAMAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RAMViewModel(app: Application): AndroidViewModel(app) {

    private val database = getDatabase(app)
    private val repository = Repository(database, RAMAPI)
    private var currentPage = 0

    val randomCharacter = repository.randomCharacter
    val characterList = repository.listCharacters
    val localCharacterList = repository.localCharacterList

    init {
        getAllFromDB()
        getRandomCharacter()
        getCharacterList()
    }

    fun getRandomCharacter() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.loadRandomCharacter()
        }
    }

    fun getPrevPage() {
        if(currentPage > 0) {
            currentPage--
            getCharacterList()
        }
    }

    fun getNextPage() {
        if(currentPage < 81) {
            currentPage++
            getCharacterList()
        }
    }

    fun isFavorite(id: Int): Boolean {
        val char = localCharacterList.value?.find {
            it.localID == id
        }
        return char != null
    }



    fun saveCharacterToDB(remoteModel: RemoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveCharacterToDB(remoteModel)
            getAllFromDB()
        }
    }

    fun deleteCharacterFromDB(remoteModel: RemoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCharacterFromDB(remoteModel)
            getAllFromDB()
        }
    }

    private fun getAllFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.loadLocalCharacters()
        }
    }

    private fun getCharacterList() {
        val characterString = ((0 + currentPage * 10) until (9 + currentPage * 10))
            .joinToString()
            .replace(" ", "")
        viewModelScope.launch(Dispatchers.IO) {
            repository.loadCharacterList(characterString)
        }
    }
}