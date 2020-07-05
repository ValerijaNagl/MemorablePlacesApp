package rs.raf.projekat2.valerija_nagl_rn682018.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {

    private var title : String? = null
    private var content : String? = null

    fun getTitle() : String? {
        return title
    }

    fun setTitle(text : String ) {
        title = text
    }

    fun getContent() : String? {
        return content
    }

    fun setContent(text : String ) {
        content = text
    }


}