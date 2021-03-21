package id.wahyou.simpanq.data.database.dao

import androidx.room.*
import id.wahyou.simpanq.data.model.Link
import io.reactivex.Single

@Dao
interface LinkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data : Link) : Single<Long>

    @Update
    fun update(data : Link) : Single<Int>

    @Query("SELECT * FROM link ORDER BY id DESC")
    fun get() : Single<List<Link>>

    @Query("SELECT * FROM link WHERE title LIKE :query OR content LIKE :query ORDER BY id DESC")
    fun search(query : String) : Single<List<Link>>

    @Delete
    fun delete(data : Link) :Single<Int>
}