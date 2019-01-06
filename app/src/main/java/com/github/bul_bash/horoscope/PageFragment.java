package com.github.bul_bash.horoscope;

import java.util.Random;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.bul_bash.horoscope.data.Data;
import com.github.bul_bash.horoscope.data.DataHelper;

public class PageFragment extends Fragment {

    DataHelper helper;

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    int pageNumber;
    int backColor;

    static PageFragment newInstance(int page) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

        Random rnd = new Random();
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, null);

        TextView tvPage = (TextView) view.findViewById(R.id.tvPage);
        try {
            tvPage.setText(Data.getForecasts().get(5-pageNumber));

        }
        catch (IndexOutOfBoundsException e){
            //TODO
            tvPage.setText("=)");
        }
        tvPage.setBackgroundColor(backColor);
        helper = new DataHelper();
//
//            Button newButton = view.findViewById(R.id.newButton);
//
//            View.OnClickListener onClickListenerBtn = new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    helper.selectRandomRecord();
//                    helper.showData();
//                }
//            };
//
//            newButton.setOnClickListener(onClickListenerBtn);


        return view;
    }
}
