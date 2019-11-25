package com.example.alexr.todolist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "company_table")
public class Company {

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @PrimaryKey(autoGenerate = true)
    private int id;

    public Company(int id, @NonNull String name) {
        this.name = name;
        this.id = id;
    }

    public int getId () {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
