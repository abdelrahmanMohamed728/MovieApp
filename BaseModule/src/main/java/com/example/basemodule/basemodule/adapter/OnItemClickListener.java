package com.example.basemodule.basemodule.adapter;

import android.view.View;



public interface OnItemClickListener<K> {
    void onItemClick(View view, K item);
}