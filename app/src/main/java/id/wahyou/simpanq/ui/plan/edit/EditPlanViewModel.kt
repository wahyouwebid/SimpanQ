package id.wahyou.simpanq.ui.plan.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.wahyou.simpanq.data.model.Planning
import id.wahyou.simpanq.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditPlanViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val state : MutableLiveData<Boolean> by lazy {
        MutableLiveData()
    }

    fun updatePlan(data : Planning) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.updatePlan(data, state)
            withContext(Dispatchers.Main){
                state.postValue(true)
            }
        }
    }
}