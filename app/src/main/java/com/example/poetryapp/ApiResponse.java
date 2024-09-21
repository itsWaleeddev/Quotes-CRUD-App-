package com.example.poetryapp;

import com.example.poetryapp.models.Data;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

public class ApiResponse  {
  private List<Data> data;

  private String message;

  private String status;

  public List<Data> getData() {
    return this.data;
  }

  public void setData(List<Data> data) {
    this.data = data;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
