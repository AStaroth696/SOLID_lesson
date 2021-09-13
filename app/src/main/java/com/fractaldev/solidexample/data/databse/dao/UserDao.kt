package com.fractaldev.solidexample.data.databse.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fractaldev.solidexample.data.databse.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)

    @Query("SELECT * FROM users WHERE id = :id")
    fun getById(id: String): User

    @Query("SELECT * FROM users")
    fun getAll(): List<User>

    @Update
    fun update(user: User)

    @Query("DELETE FROM users WHERE id = :id")
    fun deleteById(id: String)

}