package sheridan.android.assignment3_fifa.data

import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class TeamEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "name")
    var teamName: String,

    @ColumnInfo(name = "continent")
    var continentName: String,

    @ColumnInfo(name = "played")
    var playedGames: Int,

    @ColumnInfo(name = "won")
    var wonGames: Int,

    @ColumnInfo(name = "lost")
    var lostGames: Int,

    @ColumnInfo(name = "draw")
    var drawGames: Int,
    )
