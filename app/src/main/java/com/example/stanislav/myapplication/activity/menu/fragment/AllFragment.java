package com.example.stanislav.myapplication.activity.menu.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.stanislav.myapplication.R;

public class AllFragment extends Fragment implements View.OnClickListener {

    private LinearLayout.LayoutParams layoutParams;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(30, 20, 30, 0);

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all, container, false);

        LinearLayout itemContainer = (LinearLayout) view.findViewById(R.id.all_layout);

        int[] ids = new int[] {1, 2, 5};

        for (int i : ids) {

            LinearLayout item = new LinearLayout(view.getContext());
            item.setBackgroundColor(Color.parseColor("#ffffff"));

            Button button = new Button(view.getContext());
            button.setId(i);
            button.setOnClickListener(this);
            item.addView(button);
            itemContainer.addView(item, layoutParams);
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        System.out.println("############CLICKEDP:" + v.getId());
    }
}
