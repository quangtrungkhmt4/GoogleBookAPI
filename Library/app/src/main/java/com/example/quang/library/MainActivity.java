package com.example.quang.library;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quang.library.adapter.ListViewBooksAdapter;
import com.example.quang.library.adapter.ListViewCatalogueAdapter;
import com.example.quang.library.model.ArrayBook;
import com.example.quang.library.model.ItemBook;
import com.example.quang.library.utils.Constraint;
import com.example.quang.library.utils.GetJson;
import com.example.quang.library.utils.GetlargeImage;
import com.example.quang.library.utils.OnEventListener;
import com.example.quang.library.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private ListView lvCatalogue;
    private String[] arrCatalogue = new String[]{"Android", "AngularJS", "Arduino", "ASP.NET", "Bootstrap"
    , "C#", "Entity", "Framework", "Hibernate", "HTML", "CSS", "iOS", "Java", "JavaScript", "Jquery", "Laravel"
    , "LINQ", "MongoDB", "MySql", "NodeJS", "Oracle", "PHP", "Python", "React", "Sql", "Server", "Swift"};

    private ListView lvBook;
    private ListViewBooksAdapter adapterBook;
    private ArrayList<ItemBook> arrBook;

    private Dialog dialogProgress;

    private FloatingActionButton btnSort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!Utils.checkPermission(this) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            startActivity(new Intent(this,PermissionActivity.class));
            finish();
            return;
        }

        findID();
        loadData();
        initViews();
        initDialogProgress();
    }


    private void findID() {
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolBar);
        lvCatalogue = findViewById(R.id.lvCatalogue);
        lvBook = findViewById(R.id.lvBooks);
        btnSort = findViewById(R.id.btnSort);
    }

    private void loadData() {
        Intent intent = getIntent();
        ArrayBook arrayBook = (ArrayBook) intent.getSerializableExtra("ArrayBook");
        arrBook = arrayBook.getArrBook();
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        }

        toggle = new ActionBarDrawerToggle(this,drawerLayout,0,0){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }
        };
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        ListViewCatalogueAdapter adapterCata = new ListViewCatalogueAdapter(this, R.layout.item_listview_catalogue, arrCatalogue);
        lvCatalogue.setAdapter(adapterCata);
        adapterCata.notifyDataSetChanged();

        adapterBook = new ListViewBooksAdapter(this,R.layout.item_listview_books,arrBook);
        lvBook.setAdapter(adapterBook);
        adapterBook.notifyDataSetChanged();

        lvCatalogue.setOnItemClickListener(this);
        lvBook.setOnItemClickListener(this);

        btnSort.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        else if (id == R.id.action_bookmark){
            Intent intent = new Intent(MainActivity.this,BookMarkActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_favorite){
            Intent intent = new Intent(MainActivity.this,FavoriteActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.lvCatalogue:
                drawerLayout.closeDrawers();
                Toast.makeText(this, arrCatalogue[i], Toast.LENGTH_SHORT).show();
                dialogProgress.show();
                GetJson getJson = new GetJson(this, new OnEventListener() {
                    @Override
                    public void onSuccess(Object object) {
                        ArrayBook arrayBook = (ArrayBook) object;
                        arrBook.clear();
                        arrBook = arrayBook.getArrBook();
                        adapterBook = new ListViewBooksAdapter(MainActivity.this,R.layout.item_listview_books,arrBook);
                        lvBook.setAdapter(adapterBook);
                        adapterBook.notifyDataSetChanged();
                        dialogProgress.dismiss();
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
                String encode = Uri.encode(arrCatalogue[i]);
                getJson.execute(Constraint.API+encode);
                break;
            case R.id.lvBooks:
                final ItemBook itemBook = arrBook.get(i);
                dialogProgress.show();
                GetlargeImage getlargeImage = new GetlargeImage(this, new OnEventListener() {
                    @Override
                    public void onSuccess(Object object) {
                        String largeImage = (String) object;
                        itemBook.setLargeImage(largeImage);

                        Intent intent = new Intent(MainActivity.this,InfoBookActivity.class);
                        intent.putExtra("InfoBook",itemBook);
                        startActivity(intent);
                        dialogProgress.dismiss();
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                });
                getlargeImage.execute(itemBook.getSelfLink());
                break;
        }
    }

    private void initDialogProgress() {
        dialogProgress = new Dialog(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            lp.copyFrom(Objects.requireNonNull(dialogProgress.getWindow()).getAttributes());
        }
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(dialogProgress.getWindow()).setAttributes(lp);
        }
        dialogProgress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogProgress.setCancelable(false);
        dialogProgress.setContentView(R.layout.dialog_task_progress);
    }

    @Override
    public void onClick(View view) {
        for (int i=0; i<arrBook.size();i++){
            for (int j=0; j<arrBook.size();j++){
                if (arrBook.get(i).getTitle().compareTo(arrBook.get(j).getTitle())<0){
                    Collections.swap(arrBook,i,j);
                }
            }
        }

        adapterBook = new ListViewBooksAdapter(this,R.layout.item_listview_books,arrBook);
        lvBook.setAdapter(adapterBook);
        adapterBook.notifyDataSetChanged();
    }
}
