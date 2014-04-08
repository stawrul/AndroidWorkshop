package net.stawrul.notes.business;

import android.database.sqlite.SQLiteDatabase;
import net.stawrul.notes.R;
import net.stawrul.notes.model.Category;
import net.stawrul.notes.model.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotesController {
    static List<Category> categories;
    static List<Note> notes;

    static {
        categories = new ArrayList<Category>();
        notes = new ArrayList<Note>();

        Category notesCategory = new Category(0, "Notes", Category.Type.NORMAL, R.drawable.ic_notes);

        Note note = new Note(0, "Pierwsze", "Waldemar Korłub", new Date(), false);
        notesCategory.addNote(note);
        note = new Note(1, "Druga notatka", "Inna notatka", new Date(), false);
        notesCategory.addNote(note);
        note = new Note(2, "Trzecia", "Jeszcze inna tresc", new Date(), false);
        notesCategory.addNote(note);

        categories.add(notesCategory);

        Category starrtedCategory = new Category(1, "Starred", Category.Type.STARRED, R.drawable.ic_starred);
        note = new Note(3, "Oznaczona gwiazdka jeden", "Inna notatka", new Date(), true);
        starrtedCategory.addNote(note);
        note = new Note(4, "Oznaczona gwiazdka dwa", "Inna notatka", new Date(), true);
        starrtedCategory.addNote(note);
        categories.add(starrtedCategory);

        Category archiveCategory = new Category(2, "Archive", Category.Type.ARCHIVE, R.drawable.ic_archive);
        categories.add(archiveCategory);

        notes.addAll(notesCategory.getNotes());
        notes.addAll(starrtedCategory.getNotes());
    }

    public List<Note> getNotes() {
        return notes;
    }

    public List<Category> getCategories() {
        SQLiteDatabase db = DBHelper.instance().getReadableDatabase();
        return categories;
    }

    public void addCategory(String categoryName) {
        Category category = new Category(categories.size(), categoryName, Category.Type.USER_DEFINE, 0);
        categories.add(category);
    }

    public void deleteCategory(Category category) {
        categories.remove(category);
    }

    public Category findCategoryById(int categoryId) {
        return categories.get(categoryId);
    }

    public void saveCategory(Category category) {
        //UPDATE
    }

    public void toggleStar(Note note) {
        note.setStarred(!note.isStarred());

        if (note.isStarred()) {
            categories.get(1).addNote(note);
        } else {
            categories.get(1).getNotes().remove(note);
        }
    }
}
