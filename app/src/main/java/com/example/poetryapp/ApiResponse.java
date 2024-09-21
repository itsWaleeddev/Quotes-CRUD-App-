package com.example.poetryapp;

import com.example.poetryapp.models.PoetryInfo;

import java.util.List;

public class ApiResponse {
    private List<PoetryInfo> data;

    private String message;

    private String status;

    @Override
    public String toString() {
        return "ApiResponse{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public List<PoetryInfo> getData() {
        return this.data;
    }

    public void setData(List<PoetryInfo> data) {
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
