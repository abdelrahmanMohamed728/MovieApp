package com.example.basemodule.basemodule.adapter;

import android.view.View;

/**
 * Created by ahmed on 12/25/17.
 */

public interface OnItemClickListener<K> {
    void onItemClick(View view, K item);
}