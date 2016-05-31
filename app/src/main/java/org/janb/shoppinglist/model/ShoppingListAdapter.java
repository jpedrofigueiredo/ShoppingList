package org.janb.shoppinglist.model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import org.janb.shoppinglist.R;

import java.util.List;

public class ShoppingListAdapter extends ArrayAdapter {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;
    private Context context;

    public ShoppingListAdapter(Context context, List<ShoppingListItem> items) {
        super(context, android.R.layout.simple_list_item_1, items);
        this.context = context;
    }

    private class ViewHolder{
        TextView titleText;
        TextView countText;
        CheckBox checkBox;
        View checkOverlay;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View viewToUse = null;
        final ShoppingListItem item = (ShoppingListItem)getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        int rowType = 0; // what???
        switch (rowType) {
            case TYPE_ITEM:
                if (convertView == null) {
                    viewToUse = mInflater.inflate(R.layout.list_row, parent, false);
                    holder = new ViewHolder();
                } else {
                    viewToUse = convertView;
                    holder = (ViewHolder) viewToUse.getTag();
                }

                holder.titleText = (TextView) viewToUse.findViewById(R.id.row_item_title);
                holder.countText = (TextView) viewToUse.findViewById(R.id.row_item_count);
                holder.checkBox = (CheckBox) viewToUse.findViewById(R.id.row_item_check);
                holder.checkOverlay = viewToUse.findViewById(R.id.row_item_check_overlay);

                holder.checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        item.toggleChecked();
                        if (item.isChecked()) {
                            Toast.makeText(context, context.getString(R.string.marked_for_deletion) + " " + item.getItemTitle(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                viewToUse.setTag(holder);
                holder.countText.setText(String.valueOf(item.getItemCount()));
                if (item.getItemCount() == 1) {
                    holder.countText.setText("");
                }
                holder.titleText.setText(item.getItemTitle());
                if (item.isChecked()) {
                    holder.checkBox.setChecked(true);
                }
            break;
            case TYPE_SEPARATOR:
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.list_category, parent, false);
                    holder = new ViewHolder();
                } else {
                    viewToUse = convertView;
                    holder = (ViewHolder) viewToUse.getTag();
                }
                holder.titleText = (TextView) convertView.findViewById(R.id.textSeparator);
                break;

        }



        return viewToUse;
    }


}
