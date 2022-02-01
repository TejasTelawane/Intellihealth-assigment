package com.interview.healthcare.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.interview.healthcare.database.DatabaseClient;
import com.interview.healthcare.database.UserDao;
import com.interview.healthcare.models.User;

// Local Database repository
public class AuthRepository {

    private UserDao userDao;

    public AuthRepository(Application application) {
        DatabaseClient userDatabase = DatabaseClient.userDB(application);
        userDao = userDatabase.userDao();

    }

    public void registerUser(User user) throws Exception {
        new InsertUser(userDao).execute(user).get();
    }

    public User loginUser(String mobile, String pass) throws Exception {
        return new FetchUser(userDao).execute(mobile, pass).get();
    }

    public Boolean CheckUser(String mobile) throws Exception {
        return new CheckUser(userDao).execute(mobile).get();
    }

    private static class InsertUser extends AsyncTask<User, Void, Void> {
        private final UserDao userDao;

        public InsertUser(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    private static class FetchUser extends AsyncTask<String, Void, User> {
        private final UserDao userDao;

        public FetchUser(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected User doInBackground(String... strings) {
            return userDao.findUser(strings[0], strings[1]);
        }
    }

    private static class CheckUser extends AsyncTask<String, Void, Boolean> {
        private final UserDao userDao;

        public CheckUser(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            User user = userDao.findUserByPhone(strings[0]);
            return user != null;
        }
    }
}
