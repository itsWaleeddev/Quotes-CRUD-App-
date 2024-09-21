package com.example.poetryapp.models;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

public class PoetryInfo {

    private String Poetry_data;

    private String Poet_name;

    private String Data_Time;

    private String Id;

    public PoetryInfo(String poet_name, String poetry_data) {
        Poet_name = poet_name;
        Poetry_data = poetry_data;
    }

    @Override
    public String toString() {
        return "PoetryInfo{" +
                "Poetry_data='" + Poetry_data + '\'' +
                ", Poet_name='" + Poet_name + '\'' +
                ", Data_Time='" + Data_Time + '\'' +
                ", Id='" + Id + '\'' +
                '}';
    }

    public String getPoetry_data() {
        return this.Poetry_data;
    }

    public void setPoetry_data(String Poetry_data) {
        this.Poetry_data = Poetry_data;
    }

    public String getPoet_name() {
        return this.Poet_name;
    }

    public void setPoet_name(String Poet_name) {
        this.Poet_name = Poet_name;
    }

    public String getData_Time() {
        return this.Data_Time;
    }

    public void setData_Time(String Data_Time) {
        this.Data_Time = Data_Time;
    }

    public String getId() {
        return this.Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
}

