package com.example.quang.library;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.quang.library.model.ItemBook;
import com.example.quang.library.utils.DatabaseUtils;

import java.util.Objects;

public class AddBookmarkActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText edtTitle;
    private EditText edtAuthor;
    private EditText edtDesc;
    private ImageView imBook;
    private Button btnCreate;

    private String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bookmark);

        findID();
        loadData();
        initViews();
    }

    private void findID() {
        toolbar = findViewById(R.id.toolBarAddBm);
        edtTitle = findViewById(R.id.edtTitle);
        edtAuthor = findViewById(R.id.edtAuthor);
        edtDesc = findViewById(R.id.edtDesc);
        imBook = findViewById(R.id.imAddImage);
        btnCreate = findViewById(R.id.btnCreateBm);
    }

    private void loadData() {

    }

    private void initViews() {
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        }

        imBook.setOnClickListener(this);
        btnCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imAddImage:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
                break;
            case R.id.btnCreateBm:
                if (edtTitle.getText().toString().isEmpty()||edtAuthor.getText().toString().isEmpty()||edtDesc.getText().toString().isEmpty()){
                    return;
                }
                ItemBook itemBook = new ItemBook(edtTitle.getText().toString(),"[\""+edtAuthor.getText()+"\"]"
                    ,edtDesc.getText().toString(),imagePath,imagePath,"");
                DatabaseUtils databaseUtils = new DatabaseUtils(this);
                databaseUtils.insertBM(itemBook);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    imagePath = getRealPathFromURI(selectedImage);
                    imBook.setImageURI(selectedImage);
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery( contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }
}
