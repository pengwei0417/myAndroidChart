package com.lp.mychart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    private HorizontalBarChart mHBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        initView();
    }

    //显示菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    //处理菜单击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu1:
                Toast.makeText(this,"选择了第一项",Toast.LENGTH_SHORT).show();
               Intent intent=new Intent("com.example.activitytest.ACTION_START");

                startActivity(intent);
                break;
            case R.id.menu2:
                Toast.makeText(this,"选择了第二项",Toast.LENGTH_SHORT).show();
                finish();
        }

        return true;
    }

    //初始化
    private void initView() {

        //条形图
        mHBarChart = (HorizontalBarChart) findViewById(R.id.hbarcat);
        //设置表格上的点，被点击的时候，的回调函数
        mHBarChart.setOnChartValueSelectedListener(this);
        mHBarChart.setDrawBarShadow(false);
        mHBarChart.setDrawValueAboveBar(true);
        mHBarChart.getDescription().setEnabled(false);
        // 如果60多个条目显示在图表,drawn没有值
        mHBarChart.setMaxVisibleValueCount(60);
        // 扩展现在只能分别在x轴和y轴
        mHBarChart.setPinchZoom(false);
        //是否显示表格颜色
        mHBarChart.setDrawGridBackground(false);

        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mHBarChart);

        XAxis xAxis = mHBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        // 只有1天的时间间隔
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7);
        xAxis.setAxisMaximum(50f);
        xAxis.setAxisMinimum(0f);
        xAxis.setValueFormatter(xAxisFormatter);

        //设置悬浮
        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(mHBarChart);
        mHBarChart.setMarker(mv);

        YAxis leftAxis = mHBarChart.getAxisLeft();
        leftAxis.setLabelCount(8, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(15f);
        //这个替换setStartAtZero(true)
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(50f);

        YAxis rightAxis = mHBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(15f);
        rightAxis.setAxisMinimum(0f);
        rightAxis.setAxisMaximum(50f);

        // 设置标示，就是那个一组y的value的
        Legend l = mHBarChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        //样式
        l.setForm(Legend.LegendForm.SQUARE);
        //字体
        l.setFormSize(9f);
        //大小
        l.setTextSize(11f);
        l.setXEntrySpace(4f);

        //模拟数据
        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        yVals1.add(new BarEntry(0, 10));
        yVals1.add(new BarEntry(5, 20));
        yVals1.add(new BarEntry(16, 30));
        yVals1.add(new BarEntry(18, 40));
        yVals1.add(new BarEntry(20, 50));
        yVals1.add(new BarEntry(22, 10));
        yVals1.add(new BarEntry(25, 20));
        yVals1.add(new BarEntry(28, 30));
        yVals1.add(new BarEntry(30, 40));

        setData(yVals1);
    }

    //设置数据
    private void setData(ArrayList yVals1) {
        float start = 1f;
        BarDataSet set1;
        if (mHBarChart.getData() != null &&
                mHBarChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) mHBarChart.getData().getDataSetByIndex(0);
            set1.setValues(yVals1);
            mHBarChart.getData().notifyDataChanged();
            mHBarChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(yVals1, "2017年工资涨幅");
            //设置有四种颜色
            set1.setColors(ColorTemplate.MATERIAL_COLORS);
            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            //设置数据
            mHBarChart.setData(data);
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
}
