package com.example.myapplication.req;

import com.google.gson.annotations.SerializedName;

/**
 * Ответ сервера
 */
public class ServerResponse {
    /**
     * Статус
     */
    @SerializedName("status")
    private String status;

    /**
     * Получить статус
     *
     * @return - статус
     */
    public String getStatus() {
        return status;
    }

    /**
     * Задать статус
     *
     * @param status - статус
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
