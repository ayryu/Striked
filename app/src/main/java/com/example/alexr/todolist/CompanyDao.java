package com.example.alexr.todolist;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface CompanyDao {

    @Insert
    void insert(Company company);

    @Query("DELETE FROM company_table")
    void deleteAll();

    @Query("SELECT * from company_table ORDER BY id DESC")
    LiveData<List<Company>> getAllCompanies();

    @Query("DELETE FROM company_table WHERE id=:id")
    void delete(int id);
}
