package com.example.quang.library;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quang.library.model.ItemBook;
import com.example.quang.library.utils.DatabaseUtils;

import java.util.Objects;

public class InfoBookActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imBook;
    private ViewGroup viewGroup;
    private TextView tvTitle;
    private TextView tvAuthor;
    private TextView tvDesc;
    private Button btnRead;

    private Toolbar toolbar;
    private ImageView imFav;
    private boolean checkFav;
    private boolean checkBookmark;
    private DatabaseUtils databaseUtils;
    private ItemBook itemBook;
    private ImageView imAddBookmark;
    private String path;

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
        imAddBookmark = findViewById(R.id.imAddBmInfo);
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

        boolean checkExistsBm = databaseUtils.checkExistsBm(itemBook);
        if (checkExistsBm){
            imAddBookmark.setImageResource(R.drawable.ic_remove_bookmark);
            checkExistsBm = true;
        }else {
            imAddBookmark.setImageResource(R.drawable.ic_add_bookmark);
            checkExistsBm = false;
        }
        path = itemBook.getSelfLink();
        if(path.contains(".pdf")){
            btnRead.setVisibility(View.VISIBLE);
        }else {
            btnRead.setVisibility(View.INVISIBLE);
        }

    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    private void initViews() {
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        }

        imBook = new ImageView(this);
        tvTitle = new TextView(this);
        tvAuthor = new TextView(this);
        tvDesc = new TextView(this);
        TextView title = new TextView(this);
        TextView author = new TextView(this);
        TextView desc = new TextView(this);
        btnRead = new Button(this);

        imFav.setOnClickListener(this);
        imAddBookmark.setOnClickListener(this);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoBookActivity.this,PdfViewActivity.class);
                intent.putExtra("path",path);
                startActivity(intent);
            }
        });

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
        ViewGroup.LayoutParams lpButton = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        btnRead.setLayoutParams(lpButton);
        btnRead.setBackgroundResource(R.drawable.custom_button_read);
        btnRead.setText("READ");
        btnRead.setTextColor(R.color.colorPrimaryDark);

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
        viewGroup.addView(btnRead);

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

        ViewGroup.MarginLayoutParams marginButton = (ViewGroup.MarginLayoutParams) btnRead.getLayoutParams();
        marginButton.setMargins(50, 20, 50, 0);


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
                    checkFav = true;
                }else {
                    databaseUtils.deleteFAV(itemBook.getSelfLink());
                    imFav.setImageResource(R.drawable.icon_fav_uncheck);
                    checkFav = false;
                }
                break;
            case R.id.imAddBmInfo:
                if (!checkBookmark){
                    databaseUtils.insertBM(itemBook);
                    imAddBookmark.setImageResource(R.drawable.ic_remove_bookmark);
                    checkBookmark = true;
                }else {
                    databaseUtils.deleteBM(itemBook.getSmallImage());
                    imAddBookmark.setImageResource(R.drawable.ic_add_bookmark);
                    checkBookmark = false;
                }
                break;
        }
    }
}
