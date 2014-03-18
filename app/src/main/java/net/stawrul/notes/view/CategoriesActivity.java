package net.stawrul.notes.view;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import net.stawrul.notes.R;
import net.stawrul.notes.business.NotesController;
import net.stawrul.notes.model.Category;

import java.util.Random;

public class CategoriesActivity extends ListActivity {

    private ArrayAdapter<Category> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NotesController notesController = new NotesController();

        ListView listView = getListView();
        adapter = new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, notesController.getCategories());
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.categories, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add_category) {
            Intent intent = new Intent(this, EditCategoryActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
