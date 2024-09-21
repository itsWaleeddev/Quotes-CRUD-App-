package com.example.quotesapp;

import java.io.Serializable;
import java.lang.String;

public class DeleteResponse  {
  private String message;

  private String status;

  @Override
  public String toString() {
    return "DeleteResponse{" +
            "message='" + message + '\'' +
            ", status='" + status + '\'' +
            '}';
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
