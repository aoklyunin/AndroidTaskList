package com.example.myapplication.data;

import java.util.UUID;


public class Crime {
    private final UUID mId;
    private String title;
    private String text;
    private boolean mSolved;

    public Crime() {
        this(UUID.randomUUID());
    }

    public Crime(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    @Override
    public String toString() {
        return "Crime{" +
                "mId=" + mId +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", mSolved=" + mSolved +
                '}';
    }
}
