package com.example.myapplication;

import java.util.Date;
import java.util.UUID;


public class Crime {
    private final UUID id;
    private String title;
    private Date mDate;
    private boolean mSolved;

    public Crime() {
        // Генерирование уникального идентификатора
        id = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return mDate;
    }
    public void setDate(Date date) {
        mDate = date;
    }
    public boolean isSolved() {
        return mSolved;
    }
    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
