package com.example.quang.library;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.quang.library.adapter.ListViewBooksAdapter;
import com.example.quang.library.model.ItemBook;
import com.example.quang.library.utils.DatabaseUtils;

import java.util.ArrayList;
import java.util.Objects;

public class FavoriteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Toolbar toolbar;
    private ListView lvBook;
    private ArrayList<ItemBook> arrBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        findID();
        loadData();
        initViews();
    }

    private void findID() {
        toolbar = findViewById(R.id.toolBarFav);
        lvBook = findViewById(R.id.lvBooksFav);
    }

    private void loadData() {
        DatabaseUtils databaseUtils = new DatabaseUtils(this);
        arrBook = databaseUtils.getFav();
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(FavoriteActivity.this,InfoBookActivity.class);
        intent.putExtra("InfoBook",arrBook.get(i));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        initViews();
    }
}
