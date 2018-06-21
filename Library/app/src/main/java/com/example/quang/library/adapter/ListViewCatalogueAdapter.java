package com.example.quang.library.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quang.library.R;

import java.util.ArrayList;

public class ListViewCatalogueAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private String[] arrItem;

    public ListViewCatalogueAdapter(Context context, int layout, String[] arrItem) {
        this.context = context;
        this.layout = layout;
        this.arrItem = arrItem;
    }

    private class ViewHolder{
        TextView tvCata;
    }

    @Override
    public int getCount() {
        return arrItem.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewRow = view;
        if(viewRow == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewRow = inflater.inflate(layout,viewGroup,false);

            ViewHolder holder = new ViewHolder();
            holder.tvCata = viewRow.findViewById(R.id.tvCata);

            viewRow.setTag(holder);
        }
        String item = arrItem[i];
        ViewHolder holder = (ViewHolder) viewRow.getTag();
        holder.tvCata.setText(item);
        return viewRow;
    }
}
