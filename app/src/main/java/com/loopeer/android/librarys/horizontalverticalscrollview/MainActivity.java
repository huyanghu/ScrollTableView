package com.loopeer.android.librarys.horizontalverticalscrollview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.loopeer.android.librarys.scrolltable.Position;
import com.loopeer.android.librarys.scrolltable.ScrollTableView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String[] topTitles = new String[]{"场地一", "场地二", "场地三", "场地四", "场地五", "场地六", "场地七", "场地八", "场地九", "场地十", "场地十一"};

    private ScrollTableView scrollTableView;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrollTableView = (ScrollTableView) findViewById(R.id.scroll_table_view);
        text = (TextView) findViewById(R.id.text);
        ArrayList<String> leftTitle = createLeftTitle();
        ArrayList<String> topTitles = createTopTitles();
        scrollTableView.setDatas(createTopTitles(), createLeftTitle(), createContent(leftTitle.size(), topTitles.size()));
    }

    private ArrayList<ArrayList<String>> createContent(int row, int column) {
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            ArrayList<String> strings = new ArrayList<>();
            for (int j = 0; j < column; j++) {
                strings.add("$" + i + "" + j);
            }
            results.add(strings);
        }
        return results;
    }

    private ArrayList<String> createTopTitles() {
        ArrayList<String> results = new ArrayList<>();
        for (String string : topTitles) {
            results.add(string);
        }
        return results;
    }

    private ArrayList<String> createLeftTitle() {
        ArrayList<String> results = new ArrayList<>();
        for (int i = 9; i < 23; i++) {
            results.add(i + ":00");
        }
        return results;
    }

    public void click(View view) {
        text.setText("点击了 "+scrollTableView.getSelectPositions().size() +"个item");
    }

}
