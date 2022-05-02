package sheridan.android.assignment3_fifa.data

import androidx.room.*

@Dao
interface TeamDao {
    @Query("SELECT * FROM teams")
    fun getAllTeams(): List<TeamEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(team : TeamEntity)

    @Query("SELECT * FROM teams WHERE name LIKE :str")
    fun findData(str:String): List<TeamEntity>

    @Query("SELECT * FROM teams ORDER BY continent")
    fun getContinentData(): List<TeamEntity>

    @Query("SELECT * FROM teams ORDER BY name")
    fun getNameData(): List<TeamEntity>

    @Query("SELECT * FROM teams ORDER BY won + won + won + draw DESC")
    fun getPoints(): List<TeamEntity>

    @Query("DELETE FROM teams WHERE name=:str")
    fun delete(str: String)

}