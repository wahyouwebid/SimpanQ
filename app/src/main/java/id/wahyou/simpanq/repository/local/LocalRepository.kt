package id.wahyou.simpanq.repository.local

import androidx.lifecycle.MutableLiveData
import id.wahyou.simpanq.data.database.RoomDB
import id.wahyou.simpanq.data.model.Link
import id.wahyou.simpanq.data.model.Planning
import id.wahyou.simpanq.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepository @Inject constructor(
    private val db : RoomDB,
    private val disposable: CompositeDisposable
) : Repository {

    override fun addLink(data: Link, state: MutableLiveData<Boolean>) {
        db.link().insert(data)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .toFlowable()
            .subscribe()
            .let { return@let disposable::add }
    }

    override fun getLink(state: MutableLiveData<List<Link>>) {
        db.link().get()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toFlowable()
            .subscribe(state::postValue)
            .let { return@let disposable::add }
    }

    override fun searchLink(query : String, state: MutableLiveData<List<Link>>) {
        db.link().search("%$query%")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toFlowable()
                .subscribe(state::postValue)
                .let { return@let disposable::add }
    }

    override fun updateLink(data: Link, state: MutableLiveData<Boolean>) {
        db.link().update(data)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .toFlowable()
            .subscribe()
            .let { return@let disposable::add }
    }

    override fun deleteLink(data: Link, state: MutableLiveData<Boolean>) {
        db.link().delete(data)
            .map { true }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toFlowable()
            .subscribe(state::postValue)
            .let { return@let disposable::add }
    }

    override fun addPlan(data: Planning, state: MutableLiveData<Boolean>) {
        db.plan().insert(data)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .toFlowable()
            .subscribe()
            .let { return@let disposable::add }
    }

    override fun getPlan(state: MutableLiveData<List<Planning>>) {
        db.plan().get()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toFlowable()
            .subscribe(state::postValue)
            .let { return@let disposable::add }
    }

    override fun getPlanByDate(query: String, state: MutableLiveData<List<Planning>>) {
        db.plan().getByDate("%$query%")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toFlowable()
            .subscribe(state::postValue)
            .let { return@let disposable::add }
    }

    override fun searchPlan(query: String, state: MutableLiveData<List<Planning>>) {
        db.plan().search("%$query%")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toFlowable()
            .subscribe(state::postValue)
            .let { return@let disposable::add }
    }

    override fun updatePlan(data: Planning, state: MutableLiveData<Boolean>) {
        db.plan().update(data)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .toFlowable()
            .subscribe()
            .let { return@let disposable::add }
    }

    override fun deletePlan(data: Planning, state: MutableLiveData<Boolean>) {
        db.plan().delete(data)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .toFlowable()
            .subscribe()
            .let { return@let disposable::add }
    }

    override fun getDisposible(): CompositeDisposable {
        return disposable
    }

    override fun getDatabase(): RoomDB {
        return db
    }

}