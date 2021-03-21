package id.wahyou.simpanq.ui.link.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.wahyou.simpanq.data.model.Link
import id.wahyou.simpanq.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditLinkViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val state : MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }

    fun updateLink(data : Link) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.updateLink(data, state)
            withContext(Dispatchers.Main){
                state.postValue(true)
            }
        }
    }
}