package net.stawrul.notes.business;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import net.stawrul.notes.R;
import net.stawrul.notes.model.Category;
import net.stawrul.notes.model.Note;

import java.util.*;

public class NotesController {
    static List<Category> categories;
    static List<Note> notes;

//    WeakHashMap<String, Collection> cache;

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

        Category archiveCategory = new Category(2, "ArchiveXYZ", Category.Type.ARCHIVE, R.drawable.ic_archive);
        categories.add(archiveCategory);

        notes.addAll(notesCategory.getNotes());
        notes.addAll(starrtedCategory.getNotes());
    }

    public List<Note> getNotes() {
        return notes;
    }

    public List<Category> getCategories() {
        SQLiteDatabase db = DBHelper.instance().getReadableDatabase();
        Cursor cursor = db.query("categories", new String[]{"_id", "name", "icon", "type"}, null, null, null, null, null);

        List<Category> categories = new ArrayList<Category>();
//        cache.put("CATEGORIES", categories);
        while (cursor.moveToNext()) {
            String name = cursor.getString(1);
            int id = cursor.getInt(0);
            int icon = cursor.getInt(2);
            Category.Type[] types = Category.Type.values();
            Category.Type type = types[cursor.getInt(3)];

            Category category = new Category(id, name, type, icon);
            categories.add(category);
        }

        return categories;
    }

    public void addCategory(String categoryName) {
        SQLiteDatabase db = DBHelper.instance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", categoryName);
        values.put("type", Category.Type.USER_DEFINE.ordinal());
        values.put("icon", 0);
        db.insert("categories", null, values);

        Category category = new Category(categories.size(), categoryName, Category.Type.USER_DEFINE, 0);
        categories.add(category);

//        cache.get("CATEGORIES")
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

    public List<Note> findNotesByCategoryId(int categoryId) {

        SQLiteDatabase db = DBHelper.instance().getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables("notes JOIN notes_categories ON note_id = _id");
        builder.appendWhere("category_id = " + categoryId);

        Cursor cursor = builder.query(db, new String[]{"_id", "title", "content", "creation_date", "starred"},
                null, null, null, null, null);

        List<Note> notes1 = new ArrayList<Note>();
        while (cursor.moveToNext()) {
            Note note = new Note(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), new Date(cursor.getInt(3) * 1000), cursor.getInt(4) != 0);
            notes1.add(note);
        }

        return notes1;
    }
}
