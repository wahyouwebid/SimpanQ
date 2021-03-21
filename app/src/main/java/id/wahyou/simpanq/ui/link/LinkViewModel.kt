package id.wahyou.simpanq.ui.link

import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent
import com.jakewharton.rxbinding3.widget.textChangeEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import id.wahyou.simpanq.data.model.Link
import id.wahyou.simpanq.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class LinkViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val getState : MutableLiveData<List<Link>> by lazy {
        MutableLiveData<List<Link>>()
    }

    val deleteState : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getData() {
        repository.getLink(getState)
    }

    fun deleteData(data : Link) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteLink(data, deleteState)
            withContext(Dispatchers.Main){
                deleteState.postValue(true)
            }
        }
    }

    fun setupSearch(editText: EditText){
        editText.textChangeEvents()
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<TextViewTextChangeEvent>() {
                    override fun onNext(t: TextViewTextChangeEvent) {
                        val keyword = t.text.toString()
                        if(keyword.trim{it <= ' '}.isNotEmpty() && keyword.trim{it <= ' '}.length >= 2) {
                            repository.searchLink(keyword, getState)
                        }else{
                            repository.getLink(getState)
                        }
                    }

                    override fun onError(e: Throwable) {

                    }

                    override fun onComplete() {

                    }
                })
                .let { return@let repository.getDisposible()::add }
    }
}