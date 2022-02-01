package com.interview.healthcare.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.interview.healthcare.models.User;

@Database(entities = User.class, exportSchema = false, version = 1) // tables and Version Details
public abstract class DatabaseClient extends RoomDatabase{

    public static DatabaseClient userDB;

    public abstract UserDao userDao();

    public static final String DB_NAME = "user_db"; // DatabaseName

    // Initializing Database
    public static synchronized DatabaseClient userDB(Context context) {
        if (userDB == null) {
            userDB = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseClient.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return userDB;
    }


    private static final RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
