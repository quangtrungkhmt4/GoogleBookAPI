package com.example.quang.library;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quang.library.model.ItemBook;
import com.example.quang.library.utils.DatabaseUtils;

public class InfoBookActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imBook;
    private ViewGroup viewGroup;
    private TextView tvTitle;
    private TextView tvAuthor;
    private TextView tvDesc;

    private TextView title;
    private TextView author;
    private TextView desc;

    private Toolbar toolbar;
    private ImageView imFav;
    private boolean checkFav;
    private DatabaseUtils databaseUtils;
    private ItemBook itemBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_book);

        findID();
        initViews();
        loadData();
    }

    private void findID() {
        viewGroup = findViewById(R.id.ln);
        toolbar = findViewById(R.id.toolBarInfo);
        imFav = findViewById(R.id.imFavInfo);
    }

    private void loadData() {
        Intent intent = getIntent();
        itemBook = (ItemBook) intent.getSerializableExtra("InfoBook");
        tvTitle.setText(itemBook.getTitle());
        tvAuthor.setText(itemBook.getAuthor());
        tvDesc.setText(itemBook.getDesc());
        Glide.with(this).load(itemBook.getLargeImage()).into(imBook);

        databaseUtils = new DatabaseUtils(this);
        boolean checkExists = databaseUtils.checkExistsFav(itemBook);
        if (checkExists){
            imFav.setImageResource(R.drawable.icon_fav);
            checkFav = true;
        }else {
            imFav.setImageResource(R.drawable.icon_fav_uncheck);
            checkFav = false;
        }

    }

    private void initViews() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        imFav.setOnClickListener(this);

        imBook = new ImageView(this);
        tvTitle = new TextView(this);
        tvAuthor = new TextView(this);
        tvDesc = new TextView(this);
        title = new TextView(this);
        author = new TextView(this);
        desc = new TextView(this);

        Display display = getWindowManager().getDefaultDisplay();
        int screenHeight = display.getHeight();
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , screenHeight/4);
        imBook.setLayoutParams(lp);
        viewGroup.addView(imBook);
        imBook.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imBook.setImageResource(R.drawable.book);

        ViewGroup.LayoutParams lpText = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);

        tvTitle.setLayoutParams(lpText);
        tvAuthor.setLayoutParams(lpText);
        tvDesc.setLayoutParams(lpText);
        title.setLayoutParams(lpText);
        author.setLayoutParams(lpText);
        desc.setLayoutParams(lpText);

        title.setTextColor(Color.WHITE);
        author.setTextColor(Color.WHITE);
        desc.setTextColor(Color.WHITE);
        tvTitle.setTextColor(Color.WHITE);
        tvAuthor.setTextColor(Color.WHITE);
        tvDesc.setTextColor(Color.WHITE);

        title.setText("Title");
        title.setTextSize(20);
        author.setText("Author");
        author.setTextSize(20);
        desc.setText("Description");
        desc.setTextSize(20);

        viewGroup.addView(title);
        viewGroup.addView(tvTitle);
        viewGroup.addView(author);
        viewGroup.addView(tvAuthor);
        viewGroup.addView(desc);
        viewGroup.addView(tvDesc);

        ViewGroup.MarginLayoutParams marginTvTitle = (ViewGroup.MarginLayoutParams) tvTitle.getLayoutParams();
        marginTvTitle.setMargins(50, 20, 0, 0);

        ViewGroup.MarginLayoutParams marginTitle = (ViewGroup.MarginLayoutParams) title.getLayoutParams();
        marginTitle.setMargins(20, 20, 0, 0);

        ViewGroup.MarginLayoutParams marginTvAuthor = (ViewGroup.MarginLayoutParams) tvAuthor.getLayoutParams();
        marginTvAuthor.setMargins(50, 20, 0, 0);

        ViewGroup.MarginLayoutParams marginAuthor = (ViewGroup.MarginLayoutParams) author.getLayoutParams();
        marginAuthor.setMargins(20, 20, 0, 0);

        ViewGroup.MarginLayoutParams marginTvDesc = (ViewGroup.MarginLayoutParams) tvDesc.getLayoutParams();
        marginTvDesc.setMargins(50, 20, 0, 0);

        ViewGroup.MarginLayoutParams marginDesc = (ViewGroup.MarginLayoutParams) desc.getLayoutParams();
        marginDesc.setMargins(20, 20, 0, 0);


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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imFavInfo:
                if (!checkFav){
                    databaseUtils.insertFAV(itemBook);
                    imFav.setImageResource(R.drawable.icon_fav);
                }else {
                    databaseUtils.deleteFAV(itemBook.getSelfLink());
                    imFav.setImageResource(R.drawable.icon_fav_uncheck);
                }
                break;
        }
    }
}
