package com.example.alexr.todolist;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Company.class}, version = 1)
public abstract class CompanyRoomDatabase extends RoomDatabase {
    public abstract CompanyDao companyDao();

    private static CompanyRoomDatabase INSTANCE;
    //when the modifier static is added to a variable, it belongs to the class
    //and not to any particular instance

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final CompanyDao mDao;

        PopulateDbAsync(CompanyRoomDatabase db) {
            mDao = db.companyDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Company company = new Company(0,"Hello");
            mDao.insert(company);
            company = new Company(0,"World");
            mDao.insert(company);
            return null;
        }
    }

    public static class DeleteEntryAsync extends AsyncTask<Integer, Void, Void> {

        private final CompanyDao mDao;

        DeleteEntryAsync(CompanyRoomDatabase db) {
            mDao = db.companyDao();
        }

        @Override
        protected Void doInBackground(final Integer...params) {
            mDao.delete(params[0]);
            return null;
        }
    }

    public static CompanyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CompanyRoomDatabase.class) {
                if (INSTANCE == null) {
                    //this is where the database is created
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CompanyRoomDatabase.class, "company_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
