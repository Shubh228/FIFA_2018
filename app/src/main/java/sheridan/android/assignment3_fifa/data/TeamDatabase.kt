package sheridan.android.assignment3_fifa.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(TeamEntity::class), version = 2)
abstract class TeamDatabase: RoomDatabase() {
    abstract fun teamDao(): TeamDao
    companion object {
        @Volatile
        private var INSTANCE: TeamDatabase? = null
        fun getInstance(context: Context): TeamDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    TeamDatabase::class.java,
                    "WorldCup2018DB.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as TeamDatabase
        }
    }
}