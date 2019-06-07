package com.jxau.jf.englishstudy.mainContent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.pdfview.PDFView;
import com.joanzapata.pdfview.listener.OnPageChangeListener;
import com.jxau.jf.englishstudy.R;
import com.jxau.jf.englishstudy.thread.TxtHttpThread;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public class ReadActivity extends Activity {
    private PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
    pdfView = (PDFView)findViewById(R.id.pdfView);
        pdfView.fromAsset("CRM01登陆.pdf").defaultPage(1).onPageChange(new OnPageChangeListener() {

            @Override
            public void onPageChanged(int page, int pageCount) {
                // 当用户在翻页时候将回调。
                Toast.makeText(getApplicationContext(), page + " / " + pageCount, Toast.LENGTH_SHORT).show();
            }
        }).load();



    }
}
