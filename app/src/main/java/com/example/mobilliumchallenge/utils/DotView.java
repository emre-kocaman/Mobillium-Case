package com.example.mobilliumchallenge.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;


import com.example.mobilliumchallenge.R;

import java.util.ArrayList;
import java.util.List;

public class DotView extends LinearLayout {

    List<ImageView> dots;
    int current = 0;
    int selectedDot = R.drawable.active_dots;
    int defaultDot = R.drawable.default_dots;
    LayoutParams params;


    public DotView(Context context) {
        super(context);
        init();
    }

    public DotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        setup(1, 3);
    }

    public DotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        setup(1, 3);
    }

    private void init() {
        dots = new ArrayList<>();
    }

    public void setup(int size, int current) {
        this.current = current;
        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(16, 0, 16, 0);

        if (dots.size() != size || (dots.size() > 0 && this.current != current)) {
            removeAllViews();
            for (int i = 0; i < size; i++) {
                ImageView imageView = getView(i);
                dots.add(imageView);
                addView(imageView, i);
                invalidate();
            }
        } else {

        }

    }

    private ImageView getView(int position) {
        ImageView imageView = new ImageView(getContext());
        if (position == current) {
            imageView.setImageResource(selectedDot);
        } else{
            imageView.setImageResource(defaultDot);
        }
        imageView.setLayoutParams(params);
        return imageView;
    }


}
