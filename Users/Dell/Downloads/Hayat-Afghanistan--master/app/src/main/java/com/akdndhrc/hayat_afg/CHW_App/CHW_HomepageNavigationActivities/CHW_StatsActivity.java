package com.akdndhrc.hayat_afg.CHW_App.CHW_HomepageNavigationActivities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import com.akdndhrc.hayat_afg.Adapter.Adt_LHW_StatsExpandableList;
import com.akdndhrc.hayat_afg.CHW_App.HomePage_Activity;
import com.akdndhrc.hayat_afg.DatabaseFile.Lister;
import com.akdndhrc.hayat_afg.R;
import com.akdndhrc.hayat_afg.VAC_App.VAC_HomePageVaccinatorActivities.VAC_SearchActivities.VAC_AboveTwoRegister_Activity;
import com.akdndhrc.hayat_afg.VAC_App.VAC_HomePageVaccinatorActivities.VAC_SearchActivities.VAC_BelowTwoRegister_Activity;
import com.akdndhrc.hayat_afg.slider.DefaultExceptionHandler;
import com.example.sundatepicker.components.DateItem;
import com.example.sundatepicker.interfaces.DateSetListener;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class LHW_StatsActivity extends AppCompatActivity implements DateSetListener {

    Context ctx = LHW_StatsActivity.this;
    ExpandableListView expListView;
    String[][] mData_video_parent,mDatapreg, mData_anc_parent, mData_delivery_parent, mData_pnc_parent, total_registration, total_childlessthanTwo, total_child2to15, total_member15to49, total_member49;
    Adt_LHW_StatsExpandableList adt_statsExpandableList;
    ArrayList<String> mArrayList_preg_ladies = new ArrayList<>();
    ArrayList<String> mArrayList_anc = new ArrayList<>();
    ArrayList<String> mArrayList_deliv = new ArrayList<>();
    ArrayList<String> mArrayList_pnc = new ArrayList<>();
    ArrayList<String> mArrayList_video = new ArrayList<>();


    HashMap<String, ArrayList<String>> mArrayList_Main = new HashMap<String, ArrayList<String>>();
    List<String> mArrayList_parent = new ArrayList<String>();

    EditText et_is_tareekh_tk, et_is_tareekh_sy;
    int mYear, mMonth, mDay, mYear2, mMonth2, mDay2;
    int date_for_condition = 0;
    int date_for_condition_2 = 0;
    int month_for_condition = 0;
    int month_for_condition_2 = 0;
    String grg_month, grg_day, grg_year,gregorian_date_istareekh_se,gregorian_date_istareek_tk;
    public String hold_age_date_condition = "fromage";
    public String hold_age_date_condition_2 = "fromage";
    String monthf, dayf, yearf = "null";
    String monthf2, dayf2, yearf2 = "null";
    private Date mStartDate ;
    String gregorian_date;

    boolean isOkayClicked = false;
    String temp = "0", TodayDate;
    ProgressBar pbProgress;
    JSONObject jsonObject;

    int count_anc_0,count_anc_1, total_anc;
    int count_pnc_0,count_pnc_1, total_pnc;
    int count_del_0,count_del_1, total_del;
    int count_male_15to19_age, count_female_15to19_age,count_male_20to49_age,count_female_20to49_age,count_male_49plus_age,
            count_female_49plus_age,count_male_communityleaders,count_female_communityleaders , total_male_female_videos;

    TableLayout tableLayout;
    ImageView iv_navigation_drawer, iv_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lhw_stats);

        Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler(this, LHW_StatsActivity.class));


        SimpleDateFormat dates = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        TodayDate = dates.format(c.getTime());

        pbProgress = findViewById(R.id.pbProgress);
        pbProgress.setVisibility(View.VISIBLE);


        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.exp_list);

        tableLayout = (TableLayout) findViewById(R.id.table_layout);

        //ImageView
        iv_navigation_drawer = findViewById(R.id.iv_navigation_drawer);
        iv_home = findViewById(R.id.iv_home);

        //Edittext
        et_is_tareekh_sy = findViewById(R.id.et_is_tareekh_sy);
        et_is_tareekh_tk = findViewById(R.id.et_is_tareekh_tk);
     //   et_is_tareekh_sy.setText(TodayDate);
       // et_is_tareekh_tk.setText(TodayDate);


        //Date
        mStartDate = new Date();

        PersianDate pdate = new PersianDate();
        PersianDateFormat pdformater1 = new PersianDateFormat("Y-m-d");
       String Per_TodayDate = pdformater1.format(pdate);//1396/05/20
        Log.d("000555", "Current Persian Date: " + Per_TodayDate);


        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(ctx, HomePage_Activity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(newIntent);
            }
        });


        et_is_tareekh_sy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShowPersianCalender();

               /* if (temp.equalsIgnoreCase("0")) {
                    ShowDateDialougeIsTareekhSy();
                } else {
                    et_is_tareekh_sy.getText().clear();
                    ShowDateDialougeIsTareekhSy();
                }*/
            }
        });
        et_is_tareekh_tk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (et_is_tareekh_sy.getText().toString().length() < 1) {
//                    //btn_jamaa_kre.setVisibility(View.GONE);
//                    Toast.makeText(getApplicationContext(), "برائے مہربانی (اس تاریخ سے) منتخب کریں", Toast.LENGTH_LONG).show();
//                    return;
//                }
              //  ShowDateDialougeIsTareekhTk();

            }
        });
        // preparing list data

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pbProgress.setVisibility(View.GONE);
                prepareListData();

            }
        }, 1500);


        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                try {
                    if (groupPosition == 0) {
                        if (total_registration[0][0].equalsIgnoreCase("0")) {
                            //Toast.makeText(ctx, "کوئی رجسٹریشن ریکارڈ نہیں", Toast.LENGTH_SHORT).show();
                            final Snackbar snackbar = Snackbar.make(v, "رجسٹریشن کا کوئی ریکارڈ نہیں", Snackbar.LENGTH_SHORT);
                            View mySbView = snackbar.getView();
                            mySbView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                            mySbView.setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
                            TextView textView = (TextView) mySbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.WHITE);
                            textView.setTextSize(14);
                            snackbar.setDuration(3500);
                            snackbar.show();
                        } else {

                        }
                    } else if (groupPosition == 1) {
                        if (total_childlessthanTwo[0][0].equalsIgnoreCase("0")) {
                           // Toast.makeText(ctx, "2 سال سے کم عمر کے بچے کا کوئی ریکارڈ نہیں", Toast.LENGTH_SHORT).show();
                            final Snackbar snackbar = Snackbar.make(v, "2 سال سے کم عمر کے بچے کا کوئی ریکارڈ نہیں", Snackbar.LENGTH_SHORT);
                            View mySbView = snackbar.getView();
                            mySbView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                            mySbView.setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
                            TextView textView = (TextView) mySbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.WHITE);
                            textView.setTextSize(14);
                            snackbar.setDuration(3500);
                            snackbar.show();
                        }
                        else {

                        }
                    } else if (groupPosition == 2) {
                        if (total_child2to15[0][0].equalsIgnoreCase("0")) {
                            //Toast.makeText(ctx, "2 سال سے زیادہ اور 15 سال سے کم عمر کا کوئی ریکارڈ نہیں", Toast.LENGTH_SHORT).show();
                            final Snackbar snackbar = Snackbar.make(v, "2 سال سے زیادہ اور 15 سال سے کم عمر کا کوئی ریکارڈ نہیں", Snackbar.LENGTH_SHORT);
                            View mySbView = snackbar.getView();
                            mySbView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                            mySbView.setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
                            TextView textView = (TextView) mySbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.WHITE);
                            textView.setTextSize(14);
                            snackbar.setDuration(3500);
                            snackbar.show();


                        } else {
                        }
                    } else if (groupPosition == 3) {
                        if (total_member15to49[0][0].equalsIgnoreCase("0")) {

                            final Snackbar snackbar = Snackbar.make(v, "15 سال سے زیادہ اور 49 سال سے کم عمر کا کوئی ریکارڈ نہیں", Snackbar.LENGTH_SHORT);
                            View mySbView = snackbar.getView();
                            mySbView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                            mySbView.setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
                            TextView textView = (TextView) mySbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.WHITE);
                            textView.setTextSize(14);
                            snackbar.setDuration(3500);
                            snackbar.show();

                            //  Toast.makeText(ctx, "15 سال سے زیادہ اور 49 سال سے کم عمر کا کوئی ریکارڈ نہیں", Toast.LENGTH_SHORT).show();
                        } else {
                        }
                    } else if (groupPosition == 4) {
                        if (total_member49[0][0].equalsIgnoreCase("0")) {
                          //  Toast.makeText(ctx, "49 سال سے زیادہ عمر کے افراد کا کوئی ریکارڈ نہیں", Toast.LENGTH_SHORT).show();
                            final Snackbar snackbar = Snackbar.make(v, "49 سال سے زیادہ عمر کے افراد کا کوئی ریکارڈ نہیں", Snackbar.LENGTH_SHORT);
                            View mySbView = snackbar.getView();
                            mySbView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                            mySbView.setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
                            TextView textView = (TextView) mySbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.WHITE);
                            textView.setTextSize(14);
                            snackbar.setDuration(3500);
                            snackbar.show();
                        } else {
                        }
                    } else if (groupPosition == 5) {
                        if (mDatapreg[0][0].equalsIgnoreCase("0")) {
                           // Toast.makeText(ctx, "حاملہ کا کوئی ریکارڈ نہیں", Toast.LENGTH_SHORT).show();
                            final Snackbar snackbar = Snackbar.make(v, "حاملہ خواتین کا کوئی ریکارڈ نہیں", Snackbar.LENGTH_SHORT);
                            View mySbView = snackbar.getView();
                            mySbView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                            mySbView.setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
                            TextView textView = (TextView) mySbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.WHITE);
                            textView.setTextSize(14);
                            snackbar.setDuration(3500);
                            snackbar.show();
                        } else {
                        }
                    } else if (groupPosition == 6) {
                        if (mData_anc_parent[0][0].equalsIgnoreCase("0")) {
                            //Toast.makeText(ctx, "قبل از زچگی کا کوئی ریکارڈ نہیں", Toast.LENGTH_SHORT).show();
                            final Snackbar snackbar = Snackbar.make(v, "قبل از زچگی کا کوئی ریکارڈ نہیں", Snackbar.LENGTH_SHORT);
                            View mySbView = snackbar.getView();
                            mySbView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                            mySbView.setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
                            TextView textView = (TextView) mySbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.WHITE);
                            textView.setTextSize(14);
                            snackbar.setDuration(3500);
                            snackbar.show();
                        } else {
                            Log.d("000111", "ELSE 1: ");
                        }
                    } else if (groupPosition == 7) {
                        if (mData_delivery_parent == null) {
                            //Toast.makeText(ctx, "زچگی کا کوئی ریکارڈ نہیں", Toast.LENGTH_SHORT).show();
                            final Snackbar snackbar = Snackbar.make(v, "زچگی کا کوئی ریکارڈ نہیں", Snackbar.LENGTH_SHORT);
                            View mySbView = snackbar.getView();
                            mySbView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                            mySbView.setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
                            TextView textView = (TextView) mySbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.WHITE);
                            textView.setTextSize(14);
                            snackbar.setDuration(3500);
                            snackbar.show();
                        } else {
                            Log.d("000111", "ELSE 2 ");
                        }
                    } else if (groupPosition == 8) {
                        if (mData_pnc_parent[0][0].equalsIgnoreCase("0")) {
                            //   Toast.makeText(ctx, "بعد از زچگی کا کوئی ریکارڈ نہیں", Toast.LENGTH_SHORT).show();
                            final Snackbar snackbar = Snackbar.make(v, "بعد از زچگی کا کوئی ریکارڈ نہیں", Snackbar.LENGTH_SHORT);
                            View mySbView = snackbar.getView();
                            mySbView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                            mySbView.setBackgroundColor(ctx.getResources().getColor(android.R.color.black));
                            TextView textView = (TextView) mySbView.findViewById(android.support.design.R.id.snackbar_text);
                            textView.setTextColor(Color.WHITE);
                            textView.setTextSize(14);
                            snackbar.setDuration(3500);
                            snackbar.show();
                        } else {
                            Log.d("000111", "ELSE 3: ");
                        }
                    }

                } catch (Exception e) {
                    Log.d("000111", "Error Data: " + e.getMessage());
                }

                return false;
            }
        });

    }

    private void prepareListData() {


        mArrayList_parent.clear();
        mArrayList_preg_ladies.clear();
        mArrayList_anc.clear();
        mArrayList_deliv.clear();
        mArrayList_pnc.clear();

        count_anc_0 = 0;
        count_anc_1 = 0;
        total_anc = 0;
        count_pnc_0 = 0;
        count_pnc_1 = 0;
        total_pnc = 0;
        count_del_0 = 0;
        count_del_1 = 0;
        total_del = 0;
        count_male_15to19_age = 0;
        count_female_15to19_age = 0;
        count_male_20to49_age = 0;
        count_female_20to49_age = 0;
        count_male_49plus_age = 0;
        count_female_49plus_age = 0;
        count_male_communityleaders = 0;
        count_female_communityleaders = 0;
        total_male_female_videos = 0;


        //////////////// Total Member Registration////////////////////////////////////
        try {
            Lister ls = new Lister(LHW_StatsActivity.this);
            ls.createAndOpenDB();
            try {
                total_registration = ls.executeReader("Select count(*) from MEMBER where date(added_on/1000, 'unixepoch', 'localtime') BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "'");
                if (total_registration != null) {
                    Log.d("000111", "Total MEMBER REGSITER: " + total_registration[0][0]);
                    mArrayList_parent.add(total_registration[0][0] + "@" + "کل رجسٹریشن");
                } else {
                    Log.d("000111", "ELSE TOTAL MEMBER");
                }
                mArrayList_Main.put(mArrayList_parent.get(0), null);
            } catch (Exception e) {
                Log.d("000555", "Error Total Member Catch: " + e.getMessage());
            }


            /////////////////////////// Total Child < 2 ///////////////////////////////////////////////////

            try {
                    total_childlessthanTwo = ls.executeReader("Select count(*) from MEMBER where age <= 2 AND date(added_on/1000, 'unixepoch', 'localtime') BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "'");
                if (total_childlessthanTwo != null) {
                    Log.d("000111", "Total CHILD LESS THAN TWO REGSITER: " + total_childlessthanTwo[0][0]);
                    mArrayList_parent.add(total_childlessthanTwo[0][0] + "@" + "2 سال سے کم عمر کے بچے");
                } else {
                    Log.d("000111", "ELSE TOTAL CHILD LESS THAN TWO");
                }
                mArrayList_Main.put(mArrayList_parent.get(1), null);
            } catch (Exception e) {
                Log.d("000555", "Error CHILD LESS THAN TWO Catch: " + e.getMessage());
            }


            /////////////////////////// Total Child >2  AND <15 ///////////////////////////////////////////////////

            try {
                total_child2to15 = ls.executeReader("Select count(*) from MEMBER where age > 2 AND age < 15 AND date(added_on/1000, 'unixepoch', 'localtime') BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "'");
                if (total_child2to15 != null) {
                    Log.d("000111", "Total CHILD 2 to 15 REGSITER: " + total_child2to15[0][0]);
                    mArrayList_parent.add(total_child2to15[0][0] + "@" + "2 سال سے زیادہ اور 15 سال سے کم عمر کے بچے");
                } else {
                    Log.d("000111", "ELSE Total CHILD 2 to 15 REGSITER");
                }
                mArrayList_Main.put(mArrayList_parent.get(2), null);
            } catch (Exception e) {
                Log.d("000555", "Error Total CHILD 2 to 15 REGSITER Catch :" + e.getMessage());
            }


            /////////////////////////// Total MEMBER >15  AND <49 ///////////////////////////////////////////////////

            try {
                total_member15to49 = ls.executeReader("Select count(*) from MEMBER where age >= 15 AND age <= 49 AND date(added_on/1000, 'unixepoch', 'localtime') BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "'");
                if (total_member15to49 != null) {
                    Log.d("000111", "Total MEMBER 15 to 49 REGSITER: " + total_member15to49[0][0]);
                    mArrayList_parent.add(total_member15to49[0][0] + "@" + "15 سال سے زیادہ اور 49 سال سے کم عمر کے افراد");
                } else {
                    Log.d("000111", "ELSE Total MEMBER 15 to 49 REGSITER");
                }
                mArrayList_Main.put(mArrayList_parent.get(3), null);
            } catch (Exception e) {
                Log.d("000555", "Error Total MEMBER 15 to 49 REGSITER Catch :" + e.getMessage());
            }

            /////////////////////////// Total MEMBER >49 ///////////////////////////////////////////////////

            try {
                total_member49 = ls.executeReader("Select count(*) from MEMBER where age > 49 AND date(added_on/1000, 'unixepoch', 'localtime') BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "'");
                if (total_member49 != null) {
                    Log.d("000111", "Total MEMBER 49 REGSITER: " + total_member49[0][0]);
                    mArrayList_parent.add(total_member49[0][0] + "@" + "49 سال سے زیادہ عمر کے افراد");
                } else {
                    Log.d("000111", "ELSE Total MEMBER 49 REGSITER");
                }
                mArrayList_Main.put(mArrayList_parent.get(4), null);
            } catch (Exception e) {
                Log.d("000555", "Error Total MEMBER 49 REGSITER Catch :" + e.getMessage());
            }


            //////////////////////////////////////////////// // Pregnant Ladies   /////////////////////////////////////
            mDatapreg = ls.executeReader("Select count(*) from MDELIV where  date(added_on/1000, 'unixepoch', 'localtime') BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "'");
            try {
                if (mDatapreg != null) {
                    Log.d("000111", "Total Pregnancies: " + mDatapreg[0][0]);
                    mArrayList_parent.add(mDatapreg[0][0] + "@" + "حاملہ خواتین");

                    for (int c = 0; c < mArrayList_parent.size(); c++) {
                        mArrayList_preg_ladies = new ArrayList<>();
                        String[][] mDatapreg_ladies = ls.executeReader("Select t1.full_name,count(*) from MDELIV t0 INNER JOIN MEMBER t1 ON t0.member_uid = t1.uid  where date(t0.record_data) BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "' GROUP BY uid");
                        if (mDatapreg_ladies != null) {
                            for (int i = 0; i < mDatapreg_ladies.length; i++) {
                                Log.d("000111", "FullName and Count: " + mDatapreg_ladies[i][0] + "-" + mDatapreg_ladies[i][1]);
                                mArrayList_preg_ladies.add(mDatapreg_ladies[i][0] + "@" + mDatapreg_ladies[i][1]);
                            }
                        } else {
                            Log.d("000111", "Preg child data Null: ");
                        }
                    }
                } else {
                    Log.d("000111", "ELSE 2");
                }

                mArrayList_Main.put(mArrayList_parent.get(5), mArrayList_preg_ladies);
                // childList.add(mArrayListChild);
                Log.d("000111", "Preg Ladies ChildList Size: " + mArrayList_preg_ladies.size());

            } catch (Exception e) {
                Log.d("000555", "Error Preg Catch " + e.getMessage());
            }


            ////////////////////////////////////////////////// // ANC ////////////////////////////////////////////
            try {
                mData_anc_parent = ls.executeReader("Select data from MDELIV where date(added_on/1000, 'unixepoch', 'localtime') BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "'");
                if (mData_anc_parent != null) {
                    Log.d("000222", "Data Delivery LEN: " + mData_anc_parent.length);

                    for (int i = 0; i < mData_anc_parent.length; i++) {
                        try {
                            Log.d("000222", "Data Del Loop: " + i);
                            String json = mData_anc_parent[i][0];
                            jsonObject = new JSONObject(json);
                            Log.d("000222", "Data Delivery: " + mData_anc_parent[i][0]);

                            switch (jsonObject.getString("ANV_at_Home")) {

                                case "0":
                                    count_anc_0 = count_anc_0 + 1;
                                    //mArrayList_houseriskmother.add("Pills" + "@" + count_pills);
                                    Log.d("000222", "ANC O: " + "-" + count_anc_0);
                                    break;

                                case "1":
                                    count_anc_1 = count_anc_1 + 1;
                                    //  mArrayList_houseriskmother.add("Condom" + "@" + count_condom);
                                    Log.d("000222", "ANC 1: " + "-" + count_anc_1);
                                    break;


                                default:

                                    break;
                            }

                        } catch (Exception e) {
                            continue;
                        }
                    }

                  /*  mArrayList_deliv.add("مرکز صحت" + "@" + count_markaz);
                    mArrayList_deliv.add("گھر" + "@" + count_home);
                    mArrayList_deliv.add("ہسپتال" + "@" + count_hospital);
*/

                    total_anc = count_anc_0 + count_anc_1;
                    Log.d("000222", "TOTAl  ANC " + "-" + total_anc);

                } else {
                    Log.d("000222", "ELSE ACN data null");
                }

                mArrayList_parent.add(total_anc + "@" + "ANC");

                for (int c = 0; c < mArrayList_parent.size(); c++) {
                    mArrayList_anc = new ArrayList<>();
                    String[][] mData_anc_parent = ls.executeReader("Select t1.full_name,count(*) from MDELIV t0 INNER JOIN MEMBER t1 ON t0.member_uid = t1.uid  where date(t0.record_data) BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "' GROUP BY uid");
                    if (mData_anc_parent != null) {
                        for (int i = 0; i < mData_anc_parent.length; i++) {
                            Log.d("000111", "FullName and Count: " + mData_anc_parent[i][0] + "-" + mData_anc_parent[i][1]);
                            mArrayList_anc.add(mData_anc_parent[i][0] + "@" + mData_anc_parent[i][1]);
                        }
                    } else {
                        Log.d("000111", "ANC data Null: ");
                    }
                }

                mArrayList_Main.put(mArrayList_parent.get(6), mArrayList_anc);
                Log.d("000222", "ANC ChildList Size: " + mArrayList_anc.size());


            } catch (Exception e) {
                Log.d("000222", "Error ANC Catch " + e.getMessage());
            }

            ////////////////////////////////////////////////// // Delivery ////////////////////////////////////////////
            try {

                mData_delivery_parent = ls.executeReader("Select data from MDELIV where date(added_on/1000, 'unixepoch', 'localtime') BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "'");
                if (mData_delivery_parent != null) {
                    Log.d("000222", "Data Delivery LEN: " + mData_delivery_parent.length);

                    for (int i = 0; i < mData_delivery_parent.length; i++) {
                        try {
                            Log.d("000222", "Data Del Loop: " + i);
                            String json = mData_delivery_parent[i][0];
                            jsonObject = new JSONObject(json);
                            Log.d("000222", "Data Delivery: " + mData_delivery_parent[i][0]);

                            switch (jsonObject.getString("Normal_Deliveries_Referred")) {

                                case "0":
                                    count_del_0 = count_del_0 + 1;
                                    //mArrayList_houseriskmother.add("Pills" + "@" + count_pills);
                                    Log.d("000222", "DEL0: " + "-" + count_del_0);
                                    break;

                                case "1":
                                    count_del_1 = count_del_1 + 1;
                                    //  mArrayList_houseriskmother.add("Condom" + "@" + count_condom);
                                    Log.d("000222", "DEL1 " + "-" + count_del_1);
                                    break;

                                default:

                                    break;
                            }

                        } catch (Exception e) {
                            Log.d("000222", "Data Del Loop: " + i);
                            Log.d("000222", "E1: " + e.getMessage());
                            continue;
                        }
                        }

                  /*  mArrayList_deliv.add("مرکز صحت" + "@" + count_markaz);
                    mArrayList_deliv.add("گھر" + "@" + count_home);
                    mArrayList_deliv.add("ہسپتال" + "@" + count_hospital);*/


                    total_del = count_del_0 + count_del_1;
                    Log.d("000222", "TOTAl  DEL " + "-" + total_del);

                } else {
                    Log.d("000222", "ELSE Delivery Child dat null");
                }

                mArrayList_parent.add(total_del + "@" + "Delivery");

                for (int c = 0; c < mArrayList_parent.size(); c++) {
                    mArrayList_deliv = new ArrayList<>();
                    String[][] mData_deliv = ls.executeReader("Select t1.full_name,count(*) from MDELIV t0 INNER JOIN MEMBER t1 ON t0.member_uid = t1.uid  where date(t0.record_data) BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "' GROUP BY uid");
                    if (mData_deliv != null) {
                        for (int i = 0; i < mData_deliv.length; i++) {
                            Log.d("000111", "FullName and Count: " + mData_deliv[i][0] + "-" + mData_deliv[i][1]);
                            mArrayList_deliv.add(mData_deliv[i][0] + "@" + mData_deliv[i][1]);
                        }
                    } else {
                        Log.d("000111", "DELIVERY data Null: ");
                    }
                }

                mArrayList_Main.put(mArrayList_parent.get(7), mArrayList_deliv);
                Log.d("000222", "Delivery ChildList Size: " + mArrayList_deliv.size());




            } catch (Exception e) {
                Log.d("000222", "Error Del Catch " + e.getMessage());
            }
  /*         mData_delivery_parent = ls.executeReader("Select count(*) from MDELIV where date(record_data) BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "'");
            if (mData_delivery_parent != null) {
                Log.d("000111", "Delivery ladies count: " + mData_delivery_parent[0][0]);
                mArrayList_parent.add(mData_delivery_parent[0][0] + "@" + "زچگی");

                for (int e = 0; e < mArrayList_parent.size(); e++) {
                    mArrayList_deliv = new ArrayList<>();
                 /*   mArrayList_deliv.add("گھر");
                    mArrayList_deliv.add("مرکز صحت");
                    mArrayList_deliv.add("ہسپتال");*/
  /*                  String[][] mData_delivery = ls.executeReader("Select t1.full_name,count(*) from MDELIV t0 INNER JOIN MEMBER t1 ON t0.member_uid = t1.uid where date(t0.record_data) BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "' GROUP BY uid");

                    if (mData_delivery != null) {
                        for (int i = 0; i < mData_delivery.length; i++) {
                            Log.d("000111", "Delivry Mother NAme: " + mData_delivery[i][0]);
                            mArrayList_deliv.add(mData_delivery[i][0] + "@" + mData_delivery[i][1]);
                        }
                    } else {
                        Log.d("000111", "Delivry child data Null: ");
                    }
                }
            } else {
                Log.d("000111", "ELSE Delivry");
            }

            mArrayList_Main.put(mArrayList_parent.get(2), mArrayList_deliv);
            // childList.add(mArrayListChild);
            Log.d("000111", "Delivry Ladies ChildList Size: " + mArrayList_deliv.size());
*/

            ////////////////////////////////////////////////// // PNC ////////////////////////////////////////////

           try {
                mData_pnc_parent = ls.executeReader("Select data from MDELIV where date(added_on/1000, 'unixepoch', 'localtime') BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "'");
                if (mData_pnc_parent != null) {
                    Log.d("000222", "Data Delivery LEN: " + mData_anc_parent.length);

                    for (int i = 0; i < mData_pnc_parent.length; i++) {
                        try {
                            Log.d("000222", "Data Del Loop: " + i);
                            String json = mData_pnc_parent[i][0];
                            jsonObject = new JSONObject(json);
                            Log.d("000222", "Data Delivery: " + mData_pnc_parent[i][0]);

                            switch (jsonObject.getString("PNV_at_Home")) {

                                case "0":
                                    count_pnc_0 = count_pnc_0 + 1;
                                    //mArrayList_houseriskmother.add("Pills" + "@" + count_pills);
                                    Log.d("000222", "PNC0: " + "-" + count_pnc_0);
                                    break;

                                case "1":
                                    count_pnc_1 = count_pnc_1 + 1;
                                    //  mArrayList_houseriskmother.add("Condom" + "@" + count_condom);
                                    Log.d("000222", "PNC1: " + "-" + count_pnc_1);
                                    break;

                                default:

                                    break;
                            }

                        } catch (Exception e) {
                            continue;
                            }
                        }


                  /*  mArrayList_deliv.add("مرکز صحت" + "@" + count_markaz);
                    mArrayList_deliv.add("گھر" + "@" + count_home);
                    mArrayList_deliv.add("ہسپتال" + "@" + count_hospital);*/


                    total_pnc = count_pnc_0 + count_pnc_1;
                    Log.d("000111", "TOTAl  PNC " + "-" + total_pnc);

                } else {
                    Log.d("000111", "ELSE Delivery Child dat null");
                }

                mArrayList_parent.add(total_pnc + "@" + "PNC");
//AND t0.PNV_at_Home = 1
               for (int c = 0; c < mArrayList_parent.size(); c++) {
                   mArrayList_pnc = new ArrayList<>();
                   String[][] mData_pnc = ls.executeReader("Select t1.full_name,count(*) from MDELIV t0 INNER JOIN MEMBER t1 ON t0.member_uid = t1.uid  where date(t0.record_data)  BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "' GROUP BY uid");
                   if (mData_pnc != null) {
                       for (int i = 0; i < mData_pnc.length; i++) {
                           Log.d("000111", "FullName and Count: " + mData_pnc[i][0] + "-" + mData_pnc[i][1]);
                           mArrayList_pnc.add(mData_pnc[i][0] + "@" + mData_pnc[i][1]);
                       }
                   } else {
                       Log.d("000111", "DELIVERY data Null: ");
                   }
               }

                mArrayList_Main.put(mArrayList_parent.get(8), mArrayList_pnc);
                Log.d("000222", "PNC DATA Size: " + mArrayList_pnc.size());

            } catch (Exception e) {
                Log.d("000222", "Error PNC Catch " + e.getMessage());
            }

            ////////////////////////////////////////////////// // Videos////////////////////////////////////////////



            try {
                mData_video_parent = ls.executeReader("Select data from VIDEOS  where date(added_on/1000, 'unixepoch', 'localtime') BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "'");
                if (mData_video_parent != null) {
                    Log.d("000147", "Data VIDEOS LEN: " + mData_video_parent.length);

                    for (int i = 0; i < mData_video_parent.length; i++) {
                        try {
                            Log.d("000147", "Data Del Loop: " + i);
                            String json = mData_video_parent[i][0];
                            jsonObject = new JSONObject(json);
                            Log.d("000147", "Data Videos: " + mData_video_parent[i][0]);


                            count_male_15to19_age = count_male_15to19_age + Integer.parseInt(jsonObject.getString("tv_count_male_15to19_age"));

                            Log.d("000147", "count_male_15to19_age: " + count_male_15to19_age);

                            count_female_15to19_age = count_female_15to19_age +Integer.parseInt(jsonObject.getString("tv_count_female_15to19_age"));
                            Log.d("000147", "count_female_15to19_age " + count_female_15to19_age);


                            count_male_20to49_age = count_male_20to49_age + Integer.parseInt(jsonObject.getString("tv_count_male_20to49_age"));
                            Log.d("000147", "count_male_20to49_age " + count_male_20to49_age);


                            count_female_20to49_age = count_female_20to49_age + Integer.parseInt(jsonObject.getString("tv_count_female_20to49_age"));
                            Log.d("000147", "count_female_20to49_age " + count_female_20to49_age);

                            count_male_49plus_age = count_male_49plus_age + Integer.parseInt(jsonObject.getString("tv_count_male_49plus_age"));
                            Log.d("000147", "count_male_49plus_age " + count_male_49plus_age);

                            count_female_49plus_age =  count_female_49plus_age +Integer.parseInt(jsonObject.getString("tv_count_female_49plus_age"));
                            Log.d("000147", "count_female_49plus_age " + count_female_49plus_age);

                            count_male_communityleaders = count_male_communityleaders + Integer.parseInt(jsonObject.getString("tv_count_male_communityleaders"));
                            Log.d("000147", "count_male_communityleaders " + count_male_communityleaders);

                            count_female_communityleaders = count_female_communityleaders +Integer.parseInt(jsonObject.getString("tv_count_female_communityleaders"));
                            Log.d("000147", "count_female_communityleaders " + count_female_communityleaders);



                        } catch (Exception e) {
                            Log.d("000147", "Video Err: " + e.getMessage());
                            continue;
                        }
                    }

                    int count_male= count_male_15to19_age  + count_male_20to49_age + count_male_49plus_age + count_male_communityleaders;
                    Log.d("000147", "TOTAl  MALE  VIDEO: " + count_male);

                    int count_female= count_female_15to19_age  + count_female_20to49_age + count_female_49plus_age + count_female_communityleaders;
                    Log.d("000147", "TOTAl  FEMALE VIDEO: " + count_female);

                    mArrayList_video.add("Videos" + "@" + count_male);
                    mArrayList_video.add("Videos" + "@" + count_female);



                    total_male_female_videos = count_male_15to19_age + count_female_15to19_age + count_male_20to49_age + count_female_20to49_age + count_male_49plus_age + count_female_49plus_age + count_male_communityleaders + count_female_communityleaders;
                    Log.d("000147", "TOTAl  MALE FEMALE VIDEO: " +total_male_female_videos);

                } else {
                    Log.d("000147", "ELSE VIDEO Child data null");
                }

                mArrayList_parent.add(total_male_female_videos + "@" + "Videos");
                mArrayList_Main.put(mArrayList_parent.get(11), mArrayList_video);
                Log.d("000147", "Videos ChildList Size: " + mArrayList_video.size());

            } catch (Exception e) {
                Log.d("000147", "Error Video Catch:  " + e.getMessage());
            }
            ////////////////////////////////////////////////// // Family Planning////////////////////////////////////////////
           /* try {
                mData_fplan_parent = ls.executeReader("Select data from MFPLAN where date(record_data) BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "'");
                if (mData_fplan_parent != null) {
                    Log.d("000111", "Data FPlaning LEN: " + mData_fplan_parent.length);

                    for (int i = 0; i < mData_fplan_parent.length; i++) {
                        Log.d("000111", "Data Loop: " + i);
                        String json = mData_fplan_parent[i][0];
                        jsonObject = new JSONObject(json);
                        Log.d("000111", "Data Planning: " + mData_fplan_parent[i][0]);

                        switch (jsonObject.getString("tariqa")) {

                            case "-1":
                                count_none = 0;
                                Log.d("000111", "-1: " + count_none);
                                break;
                            case "0":
                                count_pills = count_pills + 1;
                                //mArrayList_houseriskmother.add("Pills" + "@" + count_pills);
                                Log.d("000111", "Pills " + "-" + count_pills);
                                break;

                            case "1":
                                count_condom = count_condom + 1;
                                //  mArrayList_houseriskmother.add("Condom" + "@" + count_condom);
                                Log.d("000111", "Condom " + "-" + count_condom);
                                break;

                            case "2":
                                count_iud = count_iud + 1;
                                //   mArrayList_houseriskmother.add("IUD" + "@" + count_iud);
                                Log.d("000111", "IUD " + "-" + count_iud);
                                break;

                            case "3":
                                count_impl = count_impl + 1;
                                //    mArrayList_houseriskmother.add("Implants" + "@" + count_impl);
                                Log.d("000111", "Implants " + "-" + count_impl);
                                break;

                            case "4":
                                count_inject = count_inject + 1;
                                //  mArrayList_houseriskmother.add("Injectable" + "@" + count_inject);
                                Log.d("000111", "Injectable " + "-" + count_inject);
                                break;
                            default:

                                break;
                        }

                    }

                    mArrayList_fplanning.add("Pills" + "@" + count_pills);
                    mArrayList_fplanning.add("Condom" + "@" + count_condom);
                    mArrayList_fplanning.add("IUD" + "@" + count_iud);
                    mArrayList_fplanning.add("Implants" + "@" + count_impl);
                    mArrayList_fplanning.add("Injectable" + "@" + count_inject);

                    total = count_pills + count_condom + count_iud + count_impl + count_inject;
                    Log.d("000111", "TOTAl " + "-" + total);
                } else {
                    Log.d("000111", "ELSE FPlaning Child dat null");
                }

                mArrayList_parent.add(total + "@" + "خاندانی منصوبہ بندی");
                mArrayList_Main.put(mArrayList_parent.get(9), mArrayList_fplanning);
                Log.d("000111", "FPlanning ChildList Size: " + mArrayList_fplanning.size());
            } catch (Exception e) {
                Log.d("000555", "Error KhandanMan Catch " + e.getMessage());
            }*/
           /* mData_fplan_parent = ls.executeReader("Select count(*) from MFPLAN where date(record_data) BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "'");
            if (mData_fplan_parent != null) {
                Log.d("000111", "MFPLAN ladies count: " + mData_fplan_parent[0][0]);
                mArrayList_parent.add(mData_fplan_parent[0][0] + "@" + "خاندانی منصوبہ بندی");

                for (int e = 0; e < mArrayList_parent.size(); e++) {
                    mArrayList_fplanning = new ArrayList<>();

                    String[][] mData_fplanning = ls.executeReader("Select t1.full_name,count(*) from MFPLAN t0 INNER JOIN MEMBER t1 ON t0.member_uid = t1.uid where date(t0.record_data) BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "' GROUP BY uid");

                    if (mData_fplanning != null) {
                        for (int i = 0; i < mData_fplanning.length; i++) {
                            Log.d("000111", "MFPLAN Mother NAme: " + mData_fplanning[i][0]);
                            mArrayList_fplanning.add(mData_fplanning[i][0] + "@" + mData_fplanning[i][1]);
                        }
                    } else {
                        Log.d("000111", "MFPLAN child data Null: ");
                    }
                  /*  mArrayList_fplanning.add("Pills");
                    mArrayList_fplanning.add("Condoms");
                    mArrayList_fplanning.add("IUD");
                    mArrayList_fplanning.add("Implants");
                    mArrayList_fplanning.add("Injectable");*/
            /*    }
            } else {
                Log.d("000111", "ELSE MFPLAN");
            }

            mArrayList_Main.put(mArrayList_parent.get(4), mArrayList_fplanning);
            Log.d("000111", "MFPLAN Ladies ChildList Size: " + mArrayList_fplanning.size());*/


            ////////////////////////////////////////////////// //Household Visit////////////////////////////////////////////
/*
            String[][] mData_household_parent = ls.executeReader("Select count(*) from MFPLAN");
            if (mData_household_parent != null) {
                Log.d("000111", "HouseHold ladies count: " + mData_household_parent[0][0]);
                mArrayList_parent.add(mData_household_parent[0][0] + "@" + "گھریلو دورہ");

                for (int e = 0; e < mArrayList_parent.size(); e++) {
                    mArrayList_householdvisit = new ArrayList<>();

                    mArrayList_householdvisit.add("1");
                    mArrayList_householdvisit.add("2");
                }
            } else {
                Log.d("000111", "ELSE HouseHold");
            }

            mArrayList_Main.put(mArrayList_parent.get(5), mArrayList_householdvisit);
            Log.d("000111", "HouseHold Ladies ChildList Size: " + mArrayList_householdvisit.size());

*/
            ////////////////////////////////////////////////// //High Risk Mother////////////////////////////////////////////
           /* try {

                String[][] mData_highriskmother_parent = ls.executeReader("Select count(*) from MANC  where data like '%\"child_lessthan_two\":\"1\"%' AND date(record_data) BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "' AND type = '0'");
                if (mData_highriskmother_parent != null) {
                    Log.d("000111", "HighRiskMothr Total count: " + mData_highriskmother_parent[0][0]);
                    mArrayList_parent.add(mData_highriskmother_parent[0][0] + "@" + "ہائی رسک پریگننسی");

                    // mData_highrisk_parent = ls.executeReader("Select data from MANC where date(record_data) BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "' AND  type = '0'");
                    mData_highrisk_parent = ls.executeReader("Select t0.data,t1.full_name,count(*) from MANC t0 " +
                            "INNER JOIN MEMBER t1 ON t0.member_uid = t1.uid " +
                            "where t0.data like '%\"child_lessthan_two\":\"1\"%' AND date(t0.record_data) BETWEEN '" + et_is_tareekh_sy.getText().toString() + "' AND'" + et_is_tareekh_tk.getText().toString() + "' AND t0.type = '0' GROUP BY t0.member_uid");
                    if (mData_highrisk_parent != null) {
                        Log.d("000111", "Data HighRisk LEN: " + mData_highrisk_parent.length);
                        for (int i = 0; i < mData_highrisk_parent.length; i++) {
                            Log.d("000111", "Data HighRisk Loop: " + i);
                            Log.d("000111", "Data HighRisk: " + mData_highrisk_parent[i][0] + " - " + mData_highrisk_parent[i][1] + " - " + mData_highrisk_parent[i][2]);
           */     /*    String json = mData_highrisk_parent[i][0];
                    jsonObject = new JSONObject(json);


                    switch (jsonObject.getString("child_lessthan_two")) {

                        case "null":
                            count_none = 0;
                            Log.d("000111", "child less than two null: " + count_none);
                            break;
                        case "0":
                            count_lessthantwo_no = count_lessthantwo_no + 1;
                            //mArrayList_houseriskmother.add("Pills" + "@" + count_pills);
                            Log.d("000111", "less than two No " + "-" + count_lessthantwo_no);
                            break;

                        case "1":
                            count_lessthantwo_yes = count_lessthantwo_yes + 1;
                            //  mArrayList_houseriskmother.add("Condom" + "@" + count_condom);
                            Log.d("000111", "less than two yes " + "-" + count_lessthantwo_yes);
                            break;

                        default:

                            break;
                    }*/
                          /*  mArrayList_highiskmother.add(mData_highrisk_parent[i][1] + "@" + mData_highrisk_parent[i][2]);
                            Log.d("000111", "HighRiskMothr ChildList Size: " + mArrayList_highiskmother.size());
                        }

                    } else {
                        Log.d("000111", "ELSE HighRiskMothr Child data null");
                    }
                } else {
                    Log.d("000111", "ELSE HighRiskMothr Parent data null");
                }

                mArrayList_Main.put(mArrayList_parent.get(10), mArrayList_highiskmother);
            } catch (Exception e) {
                Log.d("000555", "Error HighRisk Catch " + e.getMessage());
            }*/

            ls.closeDB();
            adt_statsExpandableList = new Adt_LHW_StatsExpandableList(ctx, mArrayList_parent, mArrayList_Main);
            // setting list adapter
            adt_statsExpandableList.notifyDataSetChanged();
            expListView.setAdapter(adt_statsExpandableList);

            expListView.setClickable(true);


        } catch (Exception e) {

            Log.d("000111", "Error: " + e.getMessage());

        }
    }



    public void ShowPersianCalender() {

        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.set(Calendar.YEAR, minDate.get(Calendar.YEAR) - 100);

        com.example.sundatepicker.DatePicker.Builder builder = new com.example.sundatepicker.DatePicker.Builder()
                .minDate(minDate)
                .maxDate(maxDate)
                .closeYearAutomatically(true)
                .setRetainInstance(true);

        builder.date(mStartDate.getDay(), mStartDate.getMonth(), mStartDate.getYear());

        builder.build(LHW_StatsActivity.this).show(getSupportFragmentManager(), "");
    }

    @Override
    public void onDateSet(int id, @Nullable Calendar calendar, int day, int month, int year) {

        mStartDate.setDate(day, month, year);
        // mStart.setText(mStartDate.getDate());

        /////////Selected Persian DATE with padding 0
        if (month <= 9) {

            monthf2 = "0" + String.valueOf(month);

        } else {
            monthf2 = String.valueOf(month);
        }
        if (day <= 9) {

            dayf2 = "0" + String.valueOf(day);
        } else {
            dayf2 = String.valueOf(day);
        }
        yearf2 = String.valueOf(year);

        String persian_date = (yearf2 + "-" + monthf2 + "-" + dayf2);

        et_is_tareekh_sy.setText(persian_date);
        Log.d("000555","PERSIAN DATE: " +persian_date);


        /////////Selected Gregorian DATE
        SimpleDateFormat gdf = new SimpleDateFormat("yyyy-MM-dd");

        if (calendar.get(Calendar.MONTH) + 1 <= 9) {
            grg_month = "0" + String.valueOf(calendar.get(Calendar.MONTH) + 1);
        } else {
            grg_month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        }
        if (calendar.get(Calendar.DAY_OF_MONTH) <= 9) {
            grg_day = "0" + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            grg_day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        }

        grg_year = String.valueOf(calendar.get(Calendar.YEAR));

        gregorian_date = (grg_year + "-" + grg_month + "-" + grg_day);

        Log.d("000555", "gregorian_date: " + gregorian_date);


    }

    class Date extends DateItem {
        String getDate() {
            Calendar calendar = getCalendar();
            return String.format(Locale.US,
                    " (%d/%d/%d)",
                    getYear(), getMonth(), getDay(),
                    calendar.get(Calendar.YEAR),
                    +calendar.get(Calendar.MONTH) + 1,
                    +calendar.get(Calendar.DAY_OF_MONTH));
        }
    }



  /*  public void ShowDateDialougeIsTareekhSy() {

        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.set(Calendar.YEAR, minDate.get(Calendar.YEAR) - 2);

        DatePicker.Builder builder = new DatePicker.Builder()
                .minDate(minDate)
                .maxDate(maxDate)
                .closeYearAutomatically(true)
                .setRetainInstance(true);

        builder.date(mStartDate.getDay(), mStartDate.getMonth(), mStartDate.getYear());

        builder.build(LHW_StatsActivity.this).show(getSupportFragmentManager(), "");
    }

    @Override
    public void onDateSet(int id, @Nullable Calendar calendar, int day, int month, int year) {

        mStartDate.setDate(day, month, year);
        // mStart.setText(mStartDate.getDate());

        /////////Selected Persian DATE with padding 0
        if (month <= 9) {

            monthf2 = "0" + String.valueOf(month);

        } else {
            monthf2 = String.valueOf(month);
        }
        if (day <= 9) {

            dayf2 = "0" + String.valueOf(day);
        } else {
            dayf2 = String.valueOf(day);
        }
        yearf2 = String.valueOf(year);

        String persian_date = (yearf2 + "-" + monthf2 + "-" + dayf2);

        et_is_tareekh_sy.setText(persian_date);
        Log.d("000555", "PERSIAN DATE: " + persian_date);


        /////////Selected Gregorian DATE
        SimpleDateFormat gdf = new SimpleDateFormat("yyyy-MM-dd");

        if (calendar.get(Calendar.MONTH) + 1 <= 9) {
            grg_month = "0" + String.valueOf(calendar.get(Calendar.MONTH) + 1);
        } else {
            grg_month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        }
        if (calendar.get(Calendar.DAY_OF_MONTH) <= 9) {
            grg_day = "0" + String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        } else {
            grg_day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        }

        grg_year = String.valueOf(calendar.get(Calendar.YEAR));

        gregorian_date = (grg_year + "-" + grg_month + "-" + grg_day);

        Log.d("000555", "gregorian_date: " + gregorian_date);




    }

    public class Date extends DateItem {
        String getDate() {
            Calendar calendar = getCalendar();
            return String.format(Locale.US,
                    " (%d/%d/%d)",
                    getYear(), getMonth(), getDay(),
                    calendar.get(Calendar.YEAR),
                    +calendar.get(Calendar.MONTH) + 1,
                    +calendar.get(Calendar.DAY_OF_MONTH));
        }
    }

    public void ShowDateDialougeIsTareekhTk() {
        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.set(Calendar.YEAR, minDate.get(Calendar.YEAR) - 2);

        com.example.sundatepicker.DatePicker.Builder builder = new com.example.sundatepicker.DatePicker.Builder()
                .minDate(minDate)
                .maxDate(maxDate)
                .closeYearAutomatically(true)
                .setRetainInstance(true);

        builder.date(mStartDate.getDay(), mStartDate.getMonth(), mStartDate.getYear());

        builder.build(LHW_StatsActivity.this).show(getSupportFragmentManager(), "");

    }*/


}
