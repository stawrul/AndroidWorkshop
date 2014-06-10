package net.stawrul.notes.model;

import java.util.ArrayList;
import java.util.List;

public class Category {
    public enum Type{
        NORMAL,
        STARRED,
        ARCHIVE,
        USER_DEFINE
    }

    int id;
    List<Note> notes;
    String name;
    int icon;
    Type type;

    public Category() {
    }

    public Category(int id, String name, Type type, int icon) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.icon = icon;
        this.notes = new ArrayList<Note>();
    }

    public int getId() {
        return id;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {

        this.type = type;
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    @Override
    public String toString() {
        return name;
    }
}
