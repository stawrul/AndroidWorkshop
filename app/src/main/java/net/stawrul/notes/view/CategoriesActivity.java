package net.stawrul.notes.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import net.stawrul.notes.R;
import net.stawrul.notes.business.NotesController;
import net.stawrul.notes.model.Category;

import java.util.Random;

public class CategoriesActivity extends ListActivity {

    private ArrayAdapter<Category> adapter;
    private int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final NotesController notesController = new NotesController();

        final ListView listView = getListView();
        adapter = new ArrayAdapter<Category>(this, android.R.layout.simple_list_item_activated_1,
                android.R.id.text1, notesController.getCategories());
        listView.setAdapter(adapter);

        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        final ActionMode.Callback callback = new ActionMode.Callback() {

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.category, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.delete_category) {
                    final View view = listView.getChildAt(selectedPosition);

                    view.animate().alpha(0).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            final int originalHeight = view.getHeight();
                            final ViewGroup.LayoutParams params = view.getLayoutParams();

                            ValueAnimator animator = ValueAnimator.ofInt(originalHeight, 1);

                            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    int height = (Integer) animation.getAnimatedValue();
                                    params.height = height;
                                    view.setLayoutParams(params);
                                }
                            });

                            animator.addListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    view.setAlpha(1);
                                    params.height = originalHeight;
                                    view.setLayoutParams(params);

                                    Category category = (Category) listView.getItemAtPosition(selectedPosition);
                                    notesController.deleteCategory(category);
                                    adapter.notifyDataSetChanged();
                                    mode.finish();
                                }
                            });
                            animator.start();

                        }
                    });
                } else if (id == R.id.edit_category) {
                    Intent intent = new Intent(CategoriesActivity.this, EditCategoryActivity.class);
                    Category category = (Category) listView.getItemAtPosition(selectedPosition);
                    intent.putExtra(EditCategoryActivity.EXTRA_CATEGORY_ID, category.getId());
                    startActivity(intent);
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                listView.setItemChecked(selectedPosition, false);
            }
        };
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActionMode(callback);
                listView.setItemChecked(position, true);
                selectedPosition = position;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.categories, menu);
        return true;
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
