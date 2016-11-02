package com.loopeer.android.librarys.scrolltable;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.loopeer.android.librarys.scrolltable.view.CustomTableView;
import com.loopeer.android.librarys.scrolltable.view.IHorizontalScrollView;
import com.loopeer.android.librarys.scrolltable.view.LeftTitleView;
import com.loopeer.android.librarys.scrolltable.view.TopTitleView;

import java.util.ArrayList;

public class ScrollTableView extends LinearLayout implements CustomTableView.OnPositionClickListener {

    //各种对象
    private IHorizontalScrollView scrollHeaderHorizontal;
    private IHorizontalScrollView scrollHorizontal;
    private LeftTitleView headerVertical;
    private TopTitleView headerHorizontal;
    private CustomTableView contentView;
    //被点击的坐标集合
    private ArrayList<Position> selectPositions;
    private ArrayList<String> topTitles;
    private ArrayList<String> leftTitles;
    //数据源
    private ArrayList<ArrayList<String>> datas;

    public ScrollTableView(Context context) {
        this(context, null);
    }

    public ScrollTableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollTableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.view_container, this);
        setUpView();
        setUpData();
        selectPositions = new ArrayList<>();


        if (attrs == null) return;
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScrollTableView, defStyleAttr, 0);
        if (a == null) return;

        headerVertical.setUpAttrs(context, attrs, defStyleAttr);
        headerHorizontal.setUpAttrs(context, attrs, defStyleAttr);
        contentView.setUpAttrs(context, attrs, defStyleAttr);

        a.recycle();
    }

    //同步了2个横向 2个纵向滚动的行为
    private void setUpView() {
        scrollHeaderHorizontal = (IHorizontalScrollView) findViewById(R.id.scroll_header_horizontal);
        scrollHorizontal = (IHorizontalScrollView) findViewById(R.id.scroll_horizontal);
        headerVertical = (LeftTitleView) findViewById(R.id.header_vertical);
        headerHorizontal = (TopTitleView) findViewById(R.id.header_horizontal);
        contentView = (CustomTableView) findViewById(R.id.content_view);
        //传递滚动行为
        scrollHorizontal.setIScroller(scrollHeaderHorizontal);
        scrollHeaderHorizontal.setIScroller(scrollHorizontal);
    }

    public LeftTitleView getLeftTitleView() {
        return headerVertical;
    }

    public TopTitleView getTopTitleView() {
        return headerHorizontal;
    }

    public CustomTableView getContentView() {
        return contentView;
    }

    private void setUpData() {
        leftTitles = new ArrayList<>();
        topTitles = new ArrayList<>();
        datas = new ArrayList<>();
        contentView.setRowAndColumn(leftTitles.size(), topTitles.size());
        contentView.setOnPositionChangeListener(this);
    }

    //填充数据
    public void setDatas(ArrayList<String> topTitlesData, ArrayList<String> leftTitlesData, ArrayList<ArrayList<String>> itemData) {
        topTitles.clear();
        leftTitles.clear();
        datas.clear();
        topTitles.addAll(topTitlesData);
        leftTitles.addAll(leftTitlesData);
        datas.addAll(itemData);
        updateView();
    }

    //刷新数据
    private void updateView() {
        headerVertical.updateTitles(leftTitles);
        headerHorizontal.updateTitles(topTitles);
        contentView.setRowAndColumn(leftTitles.size(), topTitles.size());
        contentView.setDatas(datas);
    }

    //记录坐标点
    @Override
    public void onPositionClick(Position position) {
        if (selectPositions.contains(position)) {
            selectPositions.remove(position);
        } else {
            selectPositions.add(position);
        }
        contentView.setSelectPositions(selectPositions);
    }

    //获取被点击的集合
    public ArrayList<Position> getSelectPositions() {
        return selectPositions;
    }

}
