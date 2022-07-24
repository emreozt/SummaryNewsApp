package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class webView extends AppCompatActivity {

    Toolbar toolbar;
    WebView webView;
    //ActionBar actionBar = getActionBar();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.activity_web_view);
        //toolbar=findViewById(R.id.toolbar);
        webView=findViewById(R.id.webview);
        //setSupportActionBar(toolbar);

        Intent intent= getIntent();
        String url = intent.getStringExtra("url");
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);


    //    ActionBar actionBar = getSupportActionBar();

       // actionBar.setDisplayHomeAsUpEnabled(true);


    }


    //  @Override
    //public boolean onOptionsItemSelected(@NonNull MenuItem item) {

   //     if(item.getItemId()==android.R.id.home){
   //         Intent intent= new Intent(getApplicationContext(),MainActivity.class);
   //         NavUtils.navigateUpTo(this,intent);
   //         return true;

    //    }
    //    return super.onOptionsItemSelected(item);

  // }
//  @Override
//  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//      switch (item.getItemId()) {
//          case android.R.id.home:
//              this.finish();
//              return true;
//      }
//      return super.onOptionsItemSelected(item);
//  }
}