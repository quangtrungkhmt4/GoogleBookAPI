package com.example.quang.library;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.quang.library.model.ArrayBook;
import com.example.quang.library.utils.Constraint;
import com.example.quang.library.utils.GetJson;
import com.example.quang.library.utils.OnEventListener;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        crawlData();
    }

    private void crawlData(){
        GetJson getJson = new GetJson(getApplicationContext(), new OnEventListener() {
            @Override
            public void onSuccess(Object object) {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                intent.putExtra("ArrayBook",(ArrayBook)object);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Exception e) {

            }
        });
        getJson.execute(Constraint.API+"Android");
    }

}
