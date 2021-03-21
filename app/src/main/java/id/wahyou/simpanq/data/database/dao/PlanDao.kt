package id.wahyou.simpanq.data.database.dao

import androidx.room.*
import id.wahyou.simpanq.data.model.Planning
import io.reactivex.Single

@Dao
interface PlanDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data : Planning) : Single<Long>

    @Update
    fun update(data : Planning) : Single<Int>

    @Query("SELECT * FROM planning ORDER BY id DESC")
    fun get() : Single<List<Planning>>

    @Query("SELECT * FROM planning WHERE title LIKE :query OR location LIKE :query ORDER BY id DESC")
    fun search(query : String) : Single<List<Planning>>

    @Query("SELECT * FROM planning WHERE date LIKE :query ORDER BY id DESC")
    fun getByDate(query : String) : Single<List<Planning>>

    @Delete
    fun delete(data : Planning) :Single<Int>
}