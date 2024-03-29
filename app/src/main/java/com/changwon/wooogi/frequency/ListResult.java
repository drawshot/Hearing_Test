package com.changwon.wooogi.frequency;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ListResult extends AppCompatActivity {

    public static int[] ListRealLeftValue = new int[100];
    public static int[] ListRealRightValue = new int[100];
    float HH;
    float textRight;
    float textLeft;
    int l_sextant;
    int r_sextant;
    boolean list_left_decibel = false;
    boolean list_right_decibel = false;
    boolean list_left_ratio = false;
    boolean list_right_ratio = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_result);

        Intent intent = getIntent();
        int listposition = Integer.parseInt(intent.getStringExtra("position"));

//        Toast.makeText(this, "listposition = " + listposition, Toast.LENGTH_SHORT).show();

        List<FrequencyVO> list = MainActivity.dao.list();
        ListRealLeftValue[0] = list.get(listposition).l_a125;
        ListRealLeftValue[1] = list.get(listposition).l_a250;
        ListRealLeftValue[2] = list.get(listposition).l_a500;
        ListRealLeftValue[3] = list.get(listposition).l_a1000;
        ListRealLeftValue[4] = list.get(listposition).l_a2000;
        ListRealLeftValue[5] = list.get(listposition).l_a3000;
        ListRealLeftValue[6] = list.get(listposition).l_a4000;
        ListRealLeftValue[7] = list.get(listposition).l_a6000;
        ListRealLeftValue[8] = list.get(listposition).l_a8000;

        ListRealRightValue[0] = list.get(listposition).r_a125;
        ListRealRightValue[1] = list.get(listposition).r_a250;
        ListRealRightValue[2] = list.get(listposition).r_a500;
        ListRealRightValue[3] = list.get(listposition).r_a1000;
        ListRealRightValue[4] = list.get(listposition).r_a2000;
        ListRealRightValue[5] = list.get(listposition).r_a3000;
        ListRealRightValue[6] = list.get(listposition).r_a4000;
        ListRealRightValue[7] = list.get(listposition).r_a6000;
        ListRealRightValue[8] = list.get(listposition).r_a8000;

        l_sextant = (int)list.get(listposition).l_sextant;
        r_sextant = (int)list.get(listposition).r_sextant;

        textLeft = list.get(listposition).l_loss_ratio;
        textRight = list.get(listposition).r_loss_ratio;
        HH = list.get(listposition).loss_ratio;


//
//        final String number = arrayList.get(position).getNum();
//        final String date = arrayList.get(position).getdate();
//        final String time = arrayList.get(position).gettime();
//        final String l_sextant = arrayList.get(position).getl_sextant();
//        final String r_sextant = arrayList.get(position).getr_sextant();
//        final String loss_ratio = arrayList.get(position).getloss_ratio();

//        Toast.makeText(this, "listposition = " + listposition, Toast.LENGTH_SHORT).show();


//        PTA_R = ( (ListRealRightValue[1] + ListRealRightValue[2] + ListRealRightValue[3] + ListRealRightValue[4]) / 4 );
//        PTA_L = ( (ListRealLeftValue[1] + ListRealLeftValue[2] + ListRealLeftValue[3] + ListRealLeftValue[4]) / 4 );
//        if( (PTA_R-25)*1.5 <= (PTA_L-25)*1.5 ) {
//            MIb = (PTA_R-25)*1.5f;
//            MIw = (PTA_L-25)*1.5f;
//            textRight = MIb;
//            textLeft = MIw;
//        }
//        else {
//            MIb = (PTA_L-25)*1.5f;
//            MIw = (PTA_R-25)*1.5f;
//            textRight = MIw;
//            textLeft = MIb;
//        }
//        HH = (5*MIb + MIw) / 6;



        ChartView charView = new ChartView(this);
        charView.outerRectMargin = 80;

        charView.innerVerticalTickCount = 9;
        charView.topLabeles = new ArrayList<String>();
        charView.topLabeles.add("125");
        charView.topLabeles.add("250");
        charView.topLabeles.add("500");
        charView.topLabeles.add("1000");
//        charView.topLabeles.add("1000");
        charView.topLabeles.add("2000");
        charView.topLabeles.add("3000");
        charView.topLabeles.add("4000");
        charView.topLabeles.add("6000");
        charView.topLabeles.add("8000");
        charView.strTopUnit = "Hz";

        charView.innerHorizontalTickCount = 13;
        charView.leftLabeles = new ArrayList<String>();
        charView.leftLabeles.add("-10");
        charView.leftLabeles.add("0");
        charView.leftLabeles.add("10");
        charView.leftLabeles.add("20");
        charView.leftLabeles.add("30");
        charView.leftLabeles.add("40");
        charView.leftLabeles.add("50");
        charView.leftLabeles.add("60");
        charView.leftLabeles.add("70");
        charView.leftLabeles.add("80");
        charView.leftLabeles.add("90");
        charView.leftLabeles.add("100");
        charView.leftLabeles.add("110");
        charView.strLeftUnit = "dB";

        charView.maxLevel = 110;
        charView.minLevel = -10;

        charView.dataO = new ArrayList<Double>();
        for(int a = 0; 9 > a; a++){ //8은 데이터갯수
            charView.dataO.add(new Double(ListRealLeftValue[a]));
        }
        charView.dataX = new ArrayList<Double>();
        for(int a = 0; 9 > a; a++){
            charView.dataX.add(new Double(ListRealRightValue[a]));
        }

        setContentView(R.layout.activity_list_result);

        TextView ResultText = (TextView)findViewById(R.id.listresulttext);
        ResultText.setText("당신의 청력은 보건복지부 기준 6분법상 오른쪽 청력은 " + r_sextant + "dB로 정상 상태이며 왼쪽 청력은 " + l_sextant + "dB로 경도난청 상태입니다.\n" +
                "당신의 오른쪽 청력 손실율은 " + textRight + "%, 왼쪽 청력손실율은 " + textLeft + "% 이며 전체 청력 손실율은 " + HH + "% 입니다.");

        SpannableStringBuilder builder = new SpannableStringBuilder(ResultText.getText());
        builder.setSpan(new ForegroundColorSpan(Color.RED),22,28, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.RED),30,34, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.BLUE),44,49, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.BLUE),51,55, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new StyleSpan(Typeface.BOLD),57,64, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.setSpan(new ForegroundColorSpan(Color.RED),73,83, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.RED),85,90, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.BLUE),92,100, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ForegroundColorSpan(Color.BLUE),102,107, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new StyleSpan(Typeface.BOLD),111,120, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new StyleSpan(Typeface.BOLD),122,128, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ResultText.setText(builder);

        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.list_wrap_char_view);

        RelativeLayout.LayoutParams chartViewLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        charView.setLayoutParams(chartViewLayoutParams);
        rootLayout.addView(charView);





    }
}
