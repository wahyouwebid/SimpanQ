package id.wahyou.simpanq.repository

import androidx.lifecycle.MutableLiveData
import id.wahyou.simpanq.data.database.RoomDB
import id.wahyou.simpanq.data.model.Link
import id.wahyou.simpanq.data.model.Planning
import io.reactivex.disposables.CompositeDisposable

interface Repository {
    fun addLink(data : Link, state : MutableLiveData<Boolean>)
    fun getLink(state : MutableLiveData<List<Link>>)
    fun searchLink(query : String, state : MutableLiveData<List<Link>>)
    fun updateLink(data : Link, state : MutableLiveData<Boolean>)
    fun deleteLink(data : Link, state : MutableLiveData<Boolean>)

    fun addPlan(data : Planning, state : MutableLiveData<Boolean>)
    fun getPlan(state : MutableLiveData<List<Planning>>)
    fun getPlanByDate(query : String, state : MutableLiveData<List<Planning>>)
    fun searchPlan(query : String, state : MutableLiveData<List<Planning>>)
    fun updatePlan(data : Planning, state : MutableLiveData<Boolean>)
    fun deletePlan(data : Planning, state : MutableLiveData<Boolean>)

    fun getDisposible() : CompositeDisposable
    fun getDatabase() : RoomDB
}