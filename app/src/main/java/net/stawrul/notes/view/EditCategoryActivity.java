package net.stawrul.notes.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import net.stawrul.notes.R;
import net.stawrul.notes.business.NotesController;

public class EditCategoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final NotesController notesController = new NotesController();

        setContentView(R.layout.activity_edit_category);

        Button button = (Button) findViewById(R.id.button);
        final TextView categoryText = (TextView) findViewById(R.id.categoryName);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName =  categoryText.getText().toString();
                notesController.addCategory(categoryName);

            }
        });
    }
}
