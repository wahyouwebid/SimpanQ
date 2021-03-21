package id.wahyou.simpanq.repository

import androidx.lifecycle.MutableLiveData
import id.wahyou.simpanq.data.database.RoomDB
import id.wahyou.simpanq.data.model.Link
import id.wahyou.simpanq.data.model.Planning
import id.wahyou.simpanq.repository.local.LocalRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val localRepository: LocalRepository
) : Repository {
    override fun addLink(data: Link, state: MutableLiveData<Boolean>) {
        localRepository.addLink(data, state)
    }

    override fun getLink(state: MutableLiveData<List<Link>>) {
        localRepository.getLink(state)
    }

    override fun searchLink(query: String, state: MutableLiveData<List<Link>>) {
        localRepository.searchLink(query, state)
    }

    override fun updateLink(data: Link, state: MutableLiveData<Boolean>) {
        localRepository.updateLink(data, state)
    }

    override fun deleteLink(data: Link, state: MutableLiveData<Boolean>) {
        localRepository.deleteLink(data, state)
    }

    override fun addPlan(data: Planning, state: MutableLiveData<Boolean>) {
        localRepository.addPlan(data, state)
    }

    override fun getPlan(state: MutableLiveData<List<Planning>>) {
        localRepository.getPlan(state)
    }

    override fun getPlanByDate(query: String, state: MutableLiveData<List<Planning>>) {
        localRepository.getPlanByDate(query, state)
    }

    override fun searchPlan(query: String, state: MutableLiveData<List<Planning>>) {
        localRepository.searchPlan(query, state)
    }

    override fun updatePlan(data: Planning, state: MutableLiveData<Boolean>) {
        localRepository.updatePlan(data, state)
    }

    override fun deletePlan(data: Planning, state: MutableLiveData<Boolean>) {
        localRepository.deletePlan(data, state)
    }

    override fun getDisposible(): CompositeDisposable {
        return localRepository.getDisposible()
    }

    override fun getDatabase(): RoomDB {
        return localRepository.getDatabase()
    }
}