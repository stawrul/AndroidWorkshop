package net.stawrul.notes.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import net.stawrul.notes.R;
import net.stawrul.notes.model.Category;

import java.util.List;

public class ItemWithIconAdapter extends ArrayAdapter<Category> {
    private LayoutInflater mInflater;
    List<Category> categories;

    public ItemWithIconAdapter(Context context, int resource, List<Category> objects) {
        super(context, resource, objects);

        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.categories = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = mInflater.inflate(R.layout.list_item_with_icon, parent, false);
        }

        TextView textView = (TextView) view.findViewById(R.id.text);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);

        textView.setText(categories.get(position).getName());
        imageView.setImageResource(categories.get(position).getIcon());

        return view;
    }
}
