package com.example.quang.library;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.quang.library.adapter.ListViewBooksAdapter;
import com.example.quang.library.model.ItemBook;
import com.example.quang.library.utils.DatabaseUtils;

import java.util.ArrayList;
import java.util.Objects;

public class BookMarkActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener {

    private Toolbar toolbar;
    private ListView lvBook;
    private ArrayList<ItemBook> arrBook;

    private DatabaseUtils databaseUtils;

    private ImageView imAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

        findID();
        loadData();
        initViews();
    }

    private void findID() {
        toolbar = findViewById(R.id.toolBarBm);
        lvBook = findViewById(R.id.lvBooksBm);
        imAdd = findViewById(R.id.imAddBm);
    }

    private void loadData() {
        databaseUtils = new DatabaseUtils(this);
        arrBook = databaseUtils.getBM();
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        }

        ListViewBooksAdapter adapter = new ListViewBooksAdapter(this, R.layout.item_listview_books, arrBook);
        lvBook.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        lvBook.setOnItemClickListener(this);
        lvBook.setOnItemLongClickListener(this);
        imAdd.setOnClickListener(this);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        initViews();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(BookMarkActivity.this,InfoBookActivity.class);
        intent.putExtra("InfoBook",arrBook.get(i));
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imAddBm:
                Intent intent = new Intent(BookMarkActivity.this,AddBookmarkActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Delete book")
                .setMessage("Are you sure you want to delete this book?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        databaseUtils.deleteBM(arrBook.get(i).getSmallImage());
                        loadData();
                        initViews();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        return true;
    }
}
