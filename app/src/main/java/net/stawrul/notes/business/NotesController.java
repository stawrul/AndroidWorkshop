package net.stawrul.notes.business;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.AsyncTask;
import net.stawrul.notes.R;
import net.stawrul.notes.model.Category;
import net.stawrul.notes.model.Note;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class NotesController {

    public List<Category> getCategories() {
        return getCategories(null, new String[0]);
    }

    private List<Category> getCategories(String selection, String[] selectionArgs) {
        SQLiteDatabase db = DBHelper.instance().getReadableDatabase();
        Cursor cursor = db.query("categories", new String[]{"_id", "name", "icon", "type"}, selection, selectionArgs, null, null, null);

        List<Category> categories = new ArrayList<Category>();
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

    public void addCategory(final String categoryName) {
        SQLiteDatabase db = DBHelper.instance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", categoryName);
        values.put("type", Category.Type.USER_DEFINE.ordinal());
        values.put("icon", 0);
        final long id = db.insert("categories", null, values);

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL("http://10.0.2.2:9999/categories");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());

                    ObjectMapper mapper = new ObjectMapper();
                    String json = mapper.writeValueAsString(new Category((int) id, categoryName, Category.Type.USER_DEFINE, 0));

                    outputStreamWriter.write(json);
                    outputStreamWriter.close();

                    conn.getResponseCode();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

        };

        task.execute();

    }

    public void deleteCategory(Category category) {
        //UPDATE DB
    }

    public Category findCategoryById(int categoryId) {
        List<Category> categories = getCategories("_id = ?", new String[]{String.valueOf(categoryId)});

        if (!categories.isEmpty()) {
            return categories.get(0);
        }

        return null;
    }

    public void saveCategory(Category category) {
        //UPDATE
    }

    public void toggleStar(Note note) {
        //UPDATE DB
    }

    public List<Note> findNotesByCategoryId(int categoryId) {

        SQLiteDatabase db = DBHelper.instance().getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables("notes JOIN notes_categories ON note_id = _id");
        builder.appendWhere("category_id = " + categoryId);

        Cursor cursor = builder.query(db, new String[]{"_id", "title", "content", "creation_date", "starred"},
                null, null, null, null, null);

        List<Note> notes = new ArrayList<Note>();
        while (cursor.moveToNext()) {
            Note note = new Note(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), new Date(cursor.getInt(3) * 1000), cursor.getInt(4) != 0);
            notes.add(note);
        }

        return notes;
    }

    public Note findNoteById(int noteId) {
        SQLiteDatabase db = DBHelper.instance().getReadableDatabase();
        Cursor cursor = db.query("notes", new String[]{"_id", "title", "content", "creation_date", "starred"}, "_id = ?",
                new String[]{Integer.toString(noteId)}, null, null, null);

        if (cursor.moveToNext()) {
            return new Note(cursor.getInt(0), cursor.getString(1), cursor.getString(2), new Date(cursor.getInt(3) * 1000),
                    cursor.getInt(4) != 0);
        }

        return null;
    }

    public void addNote(String title, String content) {
        SQLiteDatabase db = DBHelper.instance().getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        values.put("creation_date", System.currentTimeMillis() / 1000);
        values.put("starred", 0);
        db.insert("notes", null, values);
    }
}
