package com.example.alexr.todolist;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class CompanyViewModel extends AndroidViewModel {
    private CompanyRepository Repository;
    private LiveData<List<Company>> allCompanies;
    public CompanyViewModel(Application application) {
        super(application);
        Repository = new CompanyRepository(application);
        allCompanies = Repository.getAllCompanies();

    }

    LiveData<List<Company>> getAllCompanies() {
        return allCompanies;
    }

    public void insert (Company company) {
        Repository.insert(company);
    }

}
