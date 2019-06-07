package com.jxau.jf.englishstudy.coverAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jxau.jf.englishstudy.R;
import com.jxau.jf.englishstudy.vo.SpoCatas;

import java.util.List;

public class CataAdapter extends ArrayAdapter {
    private int resourceId;

    public CataAdapter(@NonNull Context context,
                       int resource,
                       @NonNull List objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // 取出bean对象
        SpoCatas spoCatas = (SpoCatas) getItem(position);

        ViewHolder viewHolder;
        View view;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder.title = view.findViewById(R.id.spoken_title);
            view.setTag(viewHolder);

        } else {//如果缓存池中有对应的view缓存，则直接通过getTag取出viewHolder
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        // 设置控件的数据
        viewHolder.title.setText(spoCatas.getTitle());

        return view;
    }

    // ViewHolder用于缓存控件，三个属性分别对应item布局文件的三个控件
    class ViewHolder {
        public TextView title;
    }
}
