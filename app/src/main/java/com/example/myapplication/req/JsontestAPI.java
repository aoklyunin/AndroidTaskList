package com.example.myapplication.req;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Интерфейс API запросов
 */
public interface JsontestAPI {
    /**
     * Запрос на добавление задач на сервере
     * @param body - тело запроса
     * @return - ответ сервера
     */
    @POST("/add")
    Call<ServerResponse> add(@Body TasksRequest body);
}

