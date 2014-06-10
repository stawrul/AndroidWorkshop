package net.stawrul.notes.view.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import net.stawrul.notes.R;
import net.stawrul.notes.model.Note;

import java.util.List;

public abstract class NoteArrayAdapter extends CustomLayoutArrayAdapter<Note> {

    public NoteArrayAdapter(Context context, List<Note> objects) {
        super(context, R.layout.list_item_with_icon_button, objects);
    }

    @Override
    protected void bindItemWithView(final Note item, final View view) {
        TextView text = (TextView) view.findViewById(R.id.text);
        text.setText(item.toString());
        ImageButton imageButton = (ImageButton) view.findViewById(R.id.icon);

        if (item.isStarred()) {
            imageButton.setImageResource(R.drawable.ic_favourite_dark);
        } else {
            imageButton.setImageResource(R.drawable.ic_star_dark);
        }

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteArrayAdapter.this.onClick(view, item);
            }
        });
    }

    abstract public void onClick(View view, Note note);
}
