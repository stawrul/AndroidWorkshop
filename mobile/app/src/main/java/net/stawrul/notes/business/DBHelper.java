package net.stawrul.notes.business;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import net.stawrul.notes.App;
import net.stawrul.notes.R;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "notesdb.sqlite";
    public static final int DB_VERSION = 8;
    private static DBHelper instance;

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static DBHelper instance() {
        if (instance == null) {
            instance = new DBHelper(App.getContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String categoryTable = "CREATE TABLE categories (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "icon INTEGER, " +
                "type INTEGER" +
                ");";

        String notesTable = "CREATE TABLE notes (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "content TEXT, " +
                "creation_date INTEGER, " +
                "starred INTEGER " +
                ");";

        String notesCategoriesTable = "CREATE TABLE notes_categories (" +
                "note_id INTEGER," +
                "category_id INTEGER," +
                "FOREIGN KEY(note_id) REFERENCES notes(id)," +
                "FOREIGN KEY(category_id) REFERENCES categories(id)" +
                ");";

        String categories = "INSERT INTO categories (_id, name, icon, type) VALUES " +
                "(1, 'Notes new', " + R.drawable.ic_notes + " , 0), " +
                "(2, 'Starred', " + R.drawable.ic_starred + " , 1), " +
                "(3, 'Archive', " + R.drawable.ic_archive + " , 2); ";

        String notes = "INSERT INTO NOTES ('_id', 'title', 'content', 'creation_date', 'starred') VALUES" +
                "(1, 'First note with star', 'Sample content', strftime('%s','now'), 1), " +
                "(2, 'Second note', 'Another one', strftime('%s','now'), 0);";
        String rel = "INSERT INTO notes_categories VALUES " +
                "(1, 1), (1, 2)," +
                "(2, 1);";

        db.execSQL(categoryTable);
        db.execSQL(notesTable);
        db.execSQL(notesCategoriesTable);

        db.execSQL(categories);
        db.execSQL(notes);
        db.execSQL(rel);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE notes_categories");
        db.execSQL("DROP TABLE notes");
        db.execSQL("DROP TABLE categories");

        onCreate(db);
    }
}
