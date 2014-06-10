package net.stawrul.notes.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import net.stawrul.notes.R;
import net.stawrul.notes.business.NotesController;
import net.stawrul.notes.model.Note;

public class NoteActivity extends Activity {

    public static final String ARG_NOTE_ID = "note_id";
    private NotesController notesController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        int noteId = getIntent().getIntExtra(ARG_NOTE_ID, 0);

        notesController = new NotesController();
        Note note = notesController.findNoteById(noteId);

        EditText noteTitle = (EditText) findViewById(R.id.noteTitle);
        noteTitle.setText(note.getTitle());
        EditText noteBody = (EditText) findViewById(R.id.noteContent);
        noteBody.setText(note.getContent());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
