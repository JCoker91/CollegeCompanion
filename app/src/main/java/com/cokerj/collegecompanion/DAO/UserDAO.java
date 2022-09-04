package com.cokerj.collegecompanion.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cokerj.collegecompanion.Entity.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users ORDER BY userId ASC")
    List<User> getAllUsers();

    @Query("SELECT * FROM users WHERE UPPER(userName) = UPPER(:userName) LIMIT 1")
    User checkUserNameAvailability(String userName);

    @Query("SELECT * FROM users WHERE UPPER(userName) = UPPER(:userName) and userPassword = :userPassword LIMIT 1")
    User testLogIn(String userName, String userPassword);

    @Query("SELECT * FROM users WHERE loggedIn = 1 LIMIT 1")
    User getLoggedInUser();

    @Query("UPDATE users SET loggedIn = 0 WHERE loggedIn = 1")
    void logOutUser();

    @Query("UPDATE users SET loggedIn = 1 WHERE userId = :userId")
    void logInUser(int userId);
}
