package net.stawrul.notes.model;

import java.util.Date;

public class Note {
    int id;
    String title;
    String content;
    Date creationDate;
    boolean starred;

    public Note(int id, String title, String content, Date creationDate, boolean starred) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.starred = starred;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {

        this.starred = starred;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
