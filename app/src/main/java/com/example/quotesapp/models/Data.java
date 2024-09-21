package com.example.quotesapp.models;

import java.lang.String;

public class Data {

    private String Quote;

    private String Author_Name;

    private String Data_Time;

    private String Id;

    @Override
    public String toString() {
        return "Data{" +
                "Quote='" + Quote + '\'' +
                ", Author_Name='" + Author_Name + '\'' +
                ", Data_Time='" + Data_Time + '\'' +
                ", Id='" + Id + '\'' +
                '}';
    }

    public String getQuote() {
        return Quote;
    }

    public void setQuote(String quote) {
        Quote = quote;
    }

    public String getAuthor_Name() {
        return Author_Name;
    }

    public void setAuthor_Name(String author_Name) {
        Author_Name = author_Name;
    }

    public String getData_Time() {
        return Data_Time;
    }

    public void setData_Time(String data_Time) {
        Data_Time = data_Time;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}

