package com.devisv.javalin.demo02;

public class StatusDao {

    public String status;

    public StatusDao(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
