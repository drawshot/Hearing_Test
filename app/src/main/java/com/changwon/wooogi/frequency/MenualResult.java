package com.changwon.wooogi.frequency;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MenualResult extends AppCompatActivity {


    int[] MenualRealLeftValue = new int[100];
    int[] MenualRealRightValue = new int[100];
    float PTA_R;
    float PTA_L;
    float MIb;
    float MIw;
    float HH;
    float textRight;
    float textLeft;
    int l_sextant;
    int r_sextant;
    String l_sextant_text;
    String r_sextant_text;

    public static int listnumber;   // 현재 리스트 넘버
    String date_month;    // 현재 월
    String date_day;    // 현재 일
    String date_hour;   // 현재 시간
    String date_minute;     // 현재 분

    boolean list_left_decibel = false;
    boolean list_right_decibel = false;
    boolean list_left_ratio = false;
    boolean list_right_ratio = false;

    int num_list_leftdecibel;
    int num_list_leftdecibel_text;
    int num_list_rightdecibel;
    int num_list_rightdecibel_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menual_result);

//        Intent intent = getIntent();
//        MenualRealRightValue = intent.getIntArrayExtra("menualrightvalue");
//        MenualRealLeftValue = intent.getIntArrayExtra("menualleftvalue");

        MenualRealLeftValue = MenualTest.menualrealleftvalue;
        MenualRealRightValue = MenualTest.menualrealrightvalue;


        l_sextant = Function.sextantfunction(MenualRealLeftValue[2], MenualRealLeftValue[3], MenualRealLeftValue[3], MenualRealLeftValue[4], MenualRealLeftValue[4],
                MenualRealLeftValue[6]);
        r_sextant = Function.sextantfunction(MenualRealRightValue[2], MenualRealRightValue[3], MenualRealRightValue[3], MenualRealRightValue[4], MenualRealRightValue[4],
                MenualRealRightValue[6]);

        l_sextant_text = Function.Resultdgreefunction(l_sextant);
        r_sextant_text = Function.Resultdgreefunction(r_sextant);


        PTA_R = Function.PTAfunction(MenualRealRightValue[2], MenualRealRightValue[3], MenualRealRightValue[4], MenualRealRightValue[5]);
        PTA_L = Function.PTAfunction(MenualRealLeftValue[2], MenualRealLeftValue[3], MenualRealLeftValue[4], MenualRealLeftValue[5]);
        if( Function.MIfunction(PTA_R) <= Function.MIfunction(PTA_L) ) {
            MIb = Function.MIfunction(PTA_R);
            MIw = Function.MIfunction(PTA_L);
            textRight = MIb;
            textLeft = MIw;

        }
        else {
            MIb = Function.MIfunction(PTA_L);
            MIw = Function.MIfunction(PTA_R);
            textRight = MIw;
            textLeft = MIb;
        }
        HH = Function.HHfunction(MIb, MIw);



        List<FrequencyVO> list = MainActivity.dao.list();
        listnumber = list.size();
        date_month = new SimpleDateFormat("MM").format(new Date());
        date_day = new SimpleDateFormat("dd").format(new Date());
        date_hour = new SimpleDateFormat("HH").format(new Date());
        date_minute = new SimpleDateFormat("mm").format(new Date());
        FrequencyVO frequencydate = new FrequencyVO(0,date_month + "월 " + date_day + "일",date_hour + "시 " + date_minute + "분", MenualRealLeftValue[0],
                MenualRealLeftValue[1], MenualRealLeftValue[2], MenualRealLeftValue[3], MenualRealLeftValue[4], MenualRealLeftValue[5], MenualRealLeftValue[6],
                MenualRealLeftValue[7], MenualRealLeftValue[8], MenualRealRightValue[0], MenualRealRightValue[1], MenualRealRightValue[2], MenualRealRightValue[3],
                MenualRealRightValue[4], MenualRealRightValue[5], MenualRealRightValue[6], MenualRealRightValue[7], MenualRealRightValue[8], l_sextant, r_sextant,
                l_sextant_text, r_sextant_text, textLeft, textRight, HH);
        MainActivity.dao.insert(frequencydate);

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
        for(int a = 0; 9 > a; a++){ //6은 데이터갯수
            charView.dataO.add(new Double(MenualRealLeftValue[a]));
        }
        charView.dataX = new ArrayList<Double>();
        for(int a = 0; 9 > a; a++){
            charView.dataX.add(new Double(MenualRealRightValue[a]));
        }

        setContentView(R.layout.activity_menual_result);

        TextView ResultText = (TextView)findViewById(R.id.menualresulttext);
        ResultText.setText("당신의 청력은 보건복지부 기준 6분법상 오른쪽 청력은 " + r_sextant + "dB로 "+ r_sextant_text +"상태이며 왼쪽 청력은 " + l_sextant + "dB로 " + l_sextant_text + "상태입니다.\n" +
                "당신의 오른쪽 청력 손실율은 " + textRight + "%, 왼쪽 청력손실율은 " + textLeft + "% 이며 전체 청력 손실율은 " + HH + "% 입니다.");

        SpannableStringBuilder builder = new SpannableStringBuilder(ResultText.getText());

//        if(r_sextant >= 0) {
//            list_right_decibel = false;
//        }
//        else {
//            list_right_decibel = true;
//        }
//
//        if(l_sextant >= 0) {
//            list_left_decibel = false;
//        }
//        else {
//            list_left_decibel = true;
//        }
//
//        if(list_right_decibel) {                // 오른쪽 청력이 음수일 경우
//
//
//            if(list_left_decibel) {             // 왼쪽 청력이 음수일 경우
//                builder.setSpan(new ForegroundColorSpan(Color.RED),22,28, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.RED),30,35, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////                builder.setSpan(new StyleSpan(Typeface.BOLD),38,64, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.BLUE),44,49, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.BLUE),51,56, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////                builder.setSpan(new StyleSpan(Typeface.BOLD),57,64, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                builder.setSpan(new ForegroundColorSpan(Color.RED),74,84, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.RED),86,92, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.BLUE),93,102, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.BLUE),104,110, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new StyleSpan(Typeface.BOLD),114,123, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new StyleSpan(Typeface.BOLD),125,126, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//
//            else {                              // 왼쪽 청력이 양수일 경우
//                builder.setSpan(new ForegroundColorSpan(Color.RED),22,28, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.RED),30,35, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////                builder.setSpan(new StyleSpan(Typeface.BOLD),38,64, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.BLUE),44,49, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.BLUE),51,55, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////                builder.setSpan(new StyleSpan(Typeface.BOLD),57,64, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                builder.setSpan(new ForegroundColorSpan(Color.RED),73,83, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.RED),85,91, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.BLUE),92,101, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.BLUE),103,108, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new StyleSpan(Typeface.BOLD),111,120, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new StyleSpan(Typeface.BOLD),122,128, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//
//
//        }
//
//        else {                                    // 오른쪽 청력이 양수일 경우
//            if(list_left_decibel) {             // 왼쪽 청력이 음수일 경우
//                builder.setSpan(new ForegroundColorSpan(Color.RED),22,28, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.RED),30,34, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.BLUE),44,49, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.BLUE),51,55, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new StyleSpan(Typeface.BOLD),57,64, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                builder.setSpan(new ForegroundColorSpan(Color.RED),73,83, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.RED),85,90, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.BLUE),92,100, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.BLUE),102,107, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new StyleSpan(Typeface.BOLD),111,120, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new StyleSpan(Typeface.BOLD),122,128, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//
//            else {                              // 왼쪽 청력이 양수일 경우
//                builder.setSpan(new ForegroundColorSpan(Color.RED),22,28, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.RED),30,34, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.BLUE),43,48, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.BLUE),50,54, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new StyleSpan(Typeface.BOLD),57,64, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                builder.setSpan(new ForegroundColorSpan(Color.RED),73,83, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.RED),85,90, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.BLUE),92,100, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new ForegroundColorSpan(Color.BLUE),102,107, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new StyleSpan(Typeface.BOLD),111,120, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                builder.setSpan(new StyleSpan(Typeface.BOLD),122,128, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//
//        }
//
        ResultText.setText(builder);

        RelativeLayout rootLayout = (RelativeLayout) findViewById(R.id.menual_wrap_char_view);

        RelativeLayout.LayoutParams chartViewLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        charView.setLayoutParams(chartViewLayoutParams);
        rootLayout.addView(charView);

    }
}
