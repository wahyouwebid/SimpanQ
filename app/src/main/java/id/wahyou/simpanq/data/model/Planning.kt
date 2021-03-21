package id.wahyou.simpanq.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Planning(
        @PrimaryKey(autoGenerate = true)
        val id : Int = 0,
        val title : String,
        val location : String,
        val date : String,
        val startTime : String,
        val endTime : String,
        val notes : String,
): Parcelable
