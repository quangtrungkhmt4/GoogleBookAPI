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
import com.github.ybq.android.spinkit.style.Circle;

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
            viewRow = inflater.inflate(layout,viewGroup,false);

            ViewHolder holder = new ViewHolder();
            holder.tvTitle = viewRow.findViewById(R.id.tvTitleBook);
            holder.tvAuthor = viewRow.findViewById(R.id.tvAuthorBook);
            holder.tvDesc = viewRow.findViewById(R.id.tvDescBook);
            holder.imBook = viewRow.findViewById(R.id.imBooks);

            viewRow.setTag(holder);
        }
        ItemBook item = arrItem.get(i);
        ViewHolder holder = (ViewHolder) viewRow.getTag();
        holder.tvTitle.setText(item.getTitle());
        holder.tvAuthor.setText(item.getAuthor());
        holder.tvDesc.setText(item.getDesc());
        Glide.with(context).load(item.getSmallImage()).into(holder.imBook);

        return viewRow;
    }
}
