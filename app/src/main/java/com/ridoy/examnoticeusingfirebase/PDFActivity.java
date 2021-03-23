package com.ridoy.examnoticeusingfirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.ridoy.examnoticeusingfirebase.databinding.ActivityPDFBinding;

public class PDFActivity extends AppCompatActivity {

    ActivityPDFBinding activityPDFBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPDFBinding=ActivityPDFBinding.inflate(getLayoutInflater());
        setContentView(activityPDFBinding.getRoot());

        activityPDFBinding.progressbarid.setVisibility(View.VISIBLE);

        String url=getIntent().getStringExtra("uniturl");
        String finalurl="https://drive.google.com/viewerng/viewer?embedded=true&url="+url;
        activityPDFBinding.webviewid.getSettings().setJavaScriptEnabled(true);
        activityPDFBinding.webviewid.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                getSupportActionBar().setTitle("Loading...");
                if (newProgress==100){
                    activityPDFBinding.progressbarid.setVisibility(View.GONE);
                    getSupportActionBar().setTitle(getApplicationInfo().loadLabel(getPackageManager()).toString());
                }
            }
        });
        activityPDFBinding.webviewid.loadUrl(finalurl);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}