package com.interview.healthcare.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.interview.healthcare.models.User;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("select * from User where phoneNo = :phoneNo AND password = :password ")
    User findUser(String phoneNo, String password);

    @Query("select * from User where phoneNo = :phoneNo ")
    User findUserByPhone(String phoneNo);

}