package net.stawrul.notes.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import net.stawrul.notes.R;
import net.stawrul.notes.business.NotesController;
import net.stawrul.notes.model.Category;

public class EditCategoryActivity extends Activity {

    public static final String EXTRA_CATEGORY_ID = "category_id";
    private Category category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final NotesController notesController = new NotesController();

        setContentView(R.layout.activity_edit_category);

        Button button = (Button) findViewById(R.id.button);
        final TextView categoryText = (TextView) findViewById(R.id.categoryName);

        int categoryId = getIntent().getIntExtra(EXTRA_CATEGORY_ID, -1);

        if (categoryId != -1) {
            category = notesController.findCategoryById(categoryId);
            categoryText.setText(category.getName());
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryName = categoryText.getText().toString();
                if (category != null) {
                    category.setName(categoryName);
                    notesController.saveCategory(category);
                } else {
                    notesController.addCategory(categoryName);
                }
                finish();
            }
        });
    }
}
