package com.example.quang.library.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quang.library.R;
import com.example.quang.library.model.ItemBook;

import java.util.ArrayList;

public class ListViewBooksAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<ItemBook> arrItem;

    public ListViewBooksAdapter(Context context, int layout, ArrayList<ItemBook> arrItem) {
        this.context = context;
        this.layout = layout;
        this.arrItem = arrItem;
    }

    private class ViewHolder{
        TextView tvTitle;
        de.hdodenhof.circleimageview.CircleImageView imBook;
        TextView tvAuthor;
        TextView tvDesc;
    }

    @Override
    public int getCount() {
        return arrItem.size();
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
            if (inflater != null) {
                viewRow = inflater.inflate(layout,viewGroup,false);
            }

            ViewHolder holder = new ViewHolder();
            if (viewRow != null) {
                holder.tvTitle = viewRow.findViewById(R.id.tvTitleBook);
            }
            if (viewRow != null) {
                holder.tvAuthor = viewRow.findViewById(R.id.tvAuthorBook);
            }
            if (viewRow != null) {
                holder.tvDesc = viewRow.findViewById(R.id.tvDescBook);
            }
            if (viewRow != null) {
                holder.imBook = viewRow.findViewById(R.id.imBooks);
            }

            if (viewRow != null) {
                viewRow.setTag(holder);
            }
        }
        ItemBook item = arrItem.get(i);
        ViewHolder holder = null;
        if (viewRow != null) {
            holder = (ViewHolder) viewRow.getTag();
        }
        if (holder != null) {
            holder.tvTitle.setText(item.getTitle());
        }
        if (holder != null) {
            holder.tvAuthor.setText(item.getAuthor());
        }
        if (holder != null) {
            holder.tvDesc.setText(item.getDesc());
        }
        if (holder != null) {
            Glide.with(context).load(item.getSmallImage()).into(holder.imBook);
        }

        return viewRow;
    }
}
