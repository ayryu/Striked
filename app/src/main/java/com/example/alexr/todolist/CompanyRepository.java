package com.example.alexr.todolist;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class CompanyRepository {

    private CompanyDao companyDao;
    private LiveData<List<Company>> allCompanies;

    CompanyRepository(Application application) {
        CompanyRoomDatabase db = CompanyRoomDatabase.getDatabase(application);
        companyDao = db.companyDao();
        allCompanies = companyDao.getAllCompanies();
    }

    private static class insertAsyncTask extends AsyncTask<Company, Void, Void> {

        private CompanyDao mAsyncTaskDao;

        insertAsyncTask(CompanyDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Company... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    LiveData<List<Company>> getAllCompanies() {
        return allCompanies;
    }

    public void insert (Company company) {

        new insertAsyncTask(companyDao).execute(company);
    }


}
