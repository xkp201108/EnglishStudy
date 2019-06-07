package com.jxau.jf.englishstudy.mainContent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jxau.jf.englishstudy.R;
import com.jxau.jf.englishstudy.coverAdapter.CataAdapter;
import com.jxau.jf.englishstudy.thread.CataHttpThread;
import com.jxau.jf.englishstudy.utils.HttpUtils;
import com.jxau.jf.englishstudy.vo.SpoCatas;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    private ListView listView1, listView2, listView3, listView4, listView5;
    private ImageButton button1, button2, button3, button4, button5;
    private RelativeLayout layout1, layout2, layout3, layout4, layout5;
    private int viewId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_control();
        init_view();
    }

//刷新按钮
    public void Refresh(View view) {

        init_view();
    }
    private void init_control() {
        layout1 = findViewById(R.id.spoken_layout);
        layout2 = findViewById(R.id.listen_layout);
        layout3 = findViewById(R.id.word_layout);
        layout4 = findViewById(R.id.read_layout);
        layout5 = findViewById(R.id.other_layout);
        button1 = findViewById(R.id.spoken_button);
        button2 = findViewById(R.id.listen_button);
        button3 = findViewById(R.id.word_button);
        button4 = findViewById(R.id.read_button);
        button5 = findViewById(R.id.other_button);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        listView1 = findViewById(R.id.list_item_spo);
        listView2 = findViewById(R.id.list_item_listen);
        listView3 = findViewById(R.id.list_item_word);
        listView4 = findViewById(R.id.list_item_read);
    }

    private void init_view() {
        //显示口语目录条目
        //设置ListView的数据适配器
        final List<SpoCatas> spoCatas = get_cata(HttpUtils.spoCata);
        if (spoCatas == null) {
            Toast.makeText(this, "无法获取服务器资源", Toast.LENGTH_SHORT).show();
        } else {
            listView1.setAdapter(new CataAdapter(this, R.layout.item_cata_title, spoCatas));
            //设置点击事件
            listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String spoTitle = spoCatas.get(position).getTitle();
                    Intent intentToSpo = new Intent(MainActivity.this, SpokenActivity.class);
                    intentToSpo.putExtra("title", spoTitle);
                    startActivity(intentToSpo);
                }
            });
        }
        //显示听力目录条目
        //设置ListView的数据适配器
        final List<SpoCatas> lisCatas = get_cata(HttpUtils.lisCata);
        if (lisCatas == null) {
            Toast.makeText(this, "无法获取服务器资源", Toast.LENGTH_SHORT).show();
        } else {
            listView2.setAdapter(new CataAdapter(this, R.layout.item_cata_title, lisCatas));
            listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String readTitle = lisCatas.get(position).getTitle();
                    Intent intentToRead = new Intent(MainActivity.this, ReadActivity.class);
                    intentToRead.putExtra("title", readTitle);
                    startActivity(intentToRead);
                }
            });
        }
        //显示阅读目录条目
        //设置ListView的数据适配器
        final List<SpoCatas> wordCatas = get_cata(HttpUtils.wordCata);
        if (wordCatas == null) {
            Toast.makeText(this, "无法获取服务器资源", Toast.LENGTH_SHORT).show();
        } else {
            listView3.setAdapter(new CataAdapter(this, R.layout.item_cata_title, wordCatas));
            listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String readTitle = wordCatas.get(position).getTitle();
                    Intent intentToRead = new Intent(MainActivity.this, ReadActivity.class);
                    intentToRead.putExtra("title", readTitle);
                    startActivity(intentToRead);
                }
            });
        }
        //显示阅读目录条目
        //设置ListView的数据适配器
        final List<SpoCatas> readCatas = get_cata(HttpUtils.readCata);
        if (readCatas == null) {
            Toast.makeText(this, "无法获取服务器资源", Toast.LENGTH_SHORT).show();
        } else {
            listView4.setAdapter(new CataAdapter(this, R.layout.item_cata_title, readCatas));
            listView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String readTitle = readCatas.get(position).getTitle();
                    Intent intentToRead = new Intent(MainActivity.this, ReadActivity.class);
                    intentToRead.putExtra("title", readTitle);
                    startActivity(intentToRead);
                }
            });
        }
        show_view(viewId);
    }

    //根据内容名称去加载相应的目录，并将目录加载到相应的界面容器之中
    public List<SpoCatas> get_cata(String path) {
        CataHttpThread CataHttpThread = new CataHttpThread(path);
        CataHttpThread.start();
        try {
            CataHttpThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<SpoCatas> ItemList =
                JSON.parseArray(CataHttpThread.getResult(), SpoCatas.class);
        return ItemList;
    }

    //底部按钮的点击处理
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spoken_button:
                viewId = 1;
                show_view(viewId);
                break;
            case R.id.listen_button:
                viewId = 2;
                show_view(viewId);
                break;
            case R.id.word_button:
                viewId = 3;
                show_view(viewId);
                break;
            case R.id.read_button:
                viewId = 4;
                show_view(viewId);
                break;
            case R.id.other_button:
                viewId = 5;
                show_view(viewId);
                break;
        }
    }


    private void show_view(int id) {
        switch (id) {
            case 1:
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.GONE);
                layout3.setVisibility(View.GONE);
                layout4.setVisibility(View.GONE);
                layout5.setVisibility(View.GONE);
                break;
            case 2:
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
                layout3.setVisibility(View.GONE);
                layout4.setVisibility(View.GONE);
                layout5.setVisibility(View.GONE);
                break;
            case 3:
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.GONE);
                layout3.setVisibility(View.VISIBLE);
                layout4.setVisibility(View.GONE);
                layout5.setVisibility(View.GONE);
                break;
            case 4:
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.GONE);
                layout3.setVisibility(View.GONE);
                layout4.setVisibility(View.VISIBLE);
                layout5.setVisibility(View.GONE);
                break;
            case 5:
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.GONE);
                layout3.setVisibility(View.GONE);
                layout4.setVisibility(View.GONE);
                layout5.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addCategory(Intent.CATEGORY_HOME);
            startActivity(i);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
