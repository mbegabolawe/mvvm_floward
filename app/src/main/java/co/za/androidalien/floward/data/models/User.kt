package co.za.androidalien.floward.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class User(
    val albumId: Int,
    @PrimaryKey
    val userId: Int,
    val name: String,
    val url: String,
    val thumbnailUrl: String,
    var postCount: Int
) {
    constructor(): this(-1,-1, "", "", "", 0)
}
