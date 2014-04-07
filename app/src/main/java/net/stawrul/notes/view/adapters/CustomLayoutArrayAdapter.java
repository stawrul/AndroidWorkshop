package net.stawrul.notes.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

abstract public class CustomLayoutArrayAdapter<T> extends ArrayAdapter<T> {
    LayoutInflater inflater;
    int layout;

    public CustomLayoutArrayAdapter(Context context, int layout, List<T> objects) {
        super(context, layout, objects);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layout = layout;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null ;//= convertView;

        if (view == null) {
            view = inflater.inflate(layout, parent, false);
        }

        T item = getItem(position);
        bindItemWithView(item, view);

        return view;
    }

    protected abstract void bindItemWithView(T item, View view);
}
