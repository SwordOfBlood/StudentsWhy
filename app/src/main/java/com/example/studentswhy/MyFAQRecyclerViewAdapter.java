package com.example.studentswhy;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studentswhy.FAQFragment.OnListFragmentInteractionListener;
import com.example.studentswhy.dummy.FAQContent;
import com.example.studentswhy.dummy.FAQContent.DummyItem;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyFAQRecyclerViewAdapter extends RecyclerView.Adapter<MyFAQRecyclerViewAdapter.ViewHolder> {

    private View vv;
    private List<DummyItem> allRecords; //список всех данных
    private final OnListFragmentInteractionListener mListener;

    public MyFAQRecyclerViewAdapter(List<DummyItem> records, OnListFragmentInteractionListener listener) {
        allRecords = records;
        mListener = listener;
    }

    @Override
    public MyFAQRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_faq, viewGroup, false);
        return new MyFAQRecyclerViewAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyFAQRecyclerViewAdapter.ViewHolder viewHolder, int i) {
        DummyItem record = allRecords.get(i);
        String value = record.getValueText();
        int id = record.valueId;
        int parentId = record.getParentId();
        final int position = i;
        final String text = "#" + id + ": " + value + " (id родительского элемента: " + parentId + ")";

        //покажем или скроем элемент, если он дочерний
        if (parentId > 0)
        {
            //видимость делаем по параметру родительского элемента
            setVisibility(viewHolder.item, allRecords.get(parentId).isChildVisibility(), parentId);
        }
        else { //элемент не дочерний, показываем его
            setVisibility(viewHolder.item, true, parentId);
        }

        //покажем или скроем иконку деревовидного списка
        if (record.isItemParent()) {
            viewHolder.iconTree.setVisibility(View.VISIBLE);
            //показываем нужную иконку
            if (record.isChildVisibility()) //показываются дочерние элементы
                viewHolder.iconTree.setBackgroundResource(R.drawable.icon_hide);
            else //скрыты дочерние элементы
                viewHolder.iconTree.setBackgroundResource(R.drawable.icon_show);
        }
        else //элемент не родительский
            viewHolder.iconTree.setVisibility(View.GONE);

        //устанавливаем текст элемента
        if (!TextUtils.isEmpty(value)) {
            viewHolder.valueText.setText(value);
        }

        //добавляем обработку нажатий по значению
        viewHolder.valueText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyItem dataItem = allRecords.get(position);
                for(int j = 0; j < FAQContent.ITEMS.size();j++)
                {
                    if (FAQContent.ITEMS.get(j).getParentId() == dataItem.valueId)
                        FAQContent.ITEMS.get(j).setChildVisibility();
                }
                if (dataItem.isItemParent()) { //нажали по родительскому элементу, меняем видимость дочерних элементов
                    dataItem.setChildVisibility();
                    notifyDataSetChanged();
                }
                else { //нажали по обычному элементу, обрабатываем как нужно
                    Snackbar snackbar = Snackbar.make(vv, text, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }

    //установка видимости элемента
    private void setVisibility(View curV,  boolean visible, int parentId) {
        //найдем блок, благодаря которому будем сдвигать текст
        LinearLayout vPadding = curV.findViewById(R.id.block_text);

        LinearLayout.LayoutParams params;
        if (visible) {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (vPadding != null) {
                if (parentId >= 0) { //это дочерний элемент, делаем отступ
                    vPadding.setPadding(80, 0, 0, 0);
                }
                else {
                    vPadding.setPadding(0, 0, 0, 0);
                }
            }
        }
        else
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        curV.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return allRecords.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout item;
        private TextView valueText;
        private ImageView iconTree;

        public ViewHolder(View itemView) {
            super(itemView);
            vv = itemView;
            item = vv.findViewById(R.id.item);
            valueText = vv.findViewById(R.id.value_name);
            iconTree = vv.findViewById(R.id.icon_tree);
        }
    }
}