package com.fractaldev.solidexample.data.databse

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fractaldev.solidexample.data.databse.dao.UserDao
import com.fractaldev.solidexample.data.databse.model.User

@Database(
    entities = [User::class],
    version = 1
)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

}