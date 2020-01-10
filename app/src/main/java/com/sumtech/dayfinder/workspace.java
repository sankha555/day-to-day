package com.sumtech.dayfinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


import static com.sumtech.dayfinder.R.*;

public class workspace extends AppCompatActivity {

    int is_leap(int x){
        if ((x % 100) == 0) {
            if (x % 400 == 0) {
                return 1;
            } else return 0;
        } else if (x % 4 == 0) {
            return 1;
        } else return 0;

    }

    int validate() {

        Spinner f_dd = findViewById(id.spinner);
        EditText f_yy = findViewById(id.editText2);
        Spinner f_mm = findViewById(id.spinner2);

        int dd = f_dd.getSelectedItemPosition();
        int yy = Integer.parseInt(f_yy.getText().toString());
        int mm = f_mm.getSelectedItemPosition();

        int error=0;

        if (dd > 31) {
            error = 1;
        } else if ((mm == 4 || mm == 6 || mm == 9 || mm == 11) && (dd == 31)) {
            error = 1;
        } else if (mm == 2) {
            if (dd > 29) {
                error = 1;
            } else if (is_leap(yy) == 0 && dd == 29) {
                error = 1;
            }
        }

        return error;
    }

    public int find_odd(){

        Spinner f_dd = findViewById(id.spinner);
        EditText f_yy = findViewById(id.editText2);
        Spinner f_mm = findViewById(id.spinner2);

        int dd = f_dd.getSelectedItemPosition();
        int yy = Integer.parseInt(f_yy.getText().toString());
        int mm = f_mm.getSelectedItemPosition();

        int odd_final, y = validate();

        if(y==0) {
            int odd1 = dd % 7, odd2, odd_month = 0, odd_year, count_leap = 0, count_ord = 0;

            for (int i = 1; i < yy; i = i + 1) {
                if (is_leap(i) == 1) {
                    count_leap = count_leap + 1;
                } else count_ord = count_ord + 1;
            }

            for (int j = 1; j < mm; j = j + 1) {

                if (j == 1 || j == 3 || j == 5 || j == 7 || j == 8 || j == 10 || j == 12) {
                    odd2 = 3;
                } else if (j == 2) {
                    if (is_leap(yy) == 1) {
                        odd2 = 1;
                    } else odd2 = 0;
                } else odd2 = 2;

                odd_month = odd_month + odd2;
            }

            odd_month = odd_month % 7;
            odd_year = (count_leap * 2 + count_ord) % 7;

            odd_final = (odd1 + odd_month + odd_year) % 7;
        }else odd_final=7;


        return odd_final;
    }

    int trivia_id(){

        Spinner f_dd = findViewById(id.spinner);
        Spinner f_mm = findViewById(id.spinner2);

        int dd = f_dd.getSelectedItemPosition();
        int mm = f_mm.getSelectedItemPosition();
        int id=0;

        if (mm==2&&dd==29){
            id=359;
        }
        else if (mm==2) {
            id = 330+dd;
        }
        else if (mm==1&&dd==31){
            id = 360;
        }
        else if (mm==3&&dd==31){
            id = 361;
        }
        else if (mm==5&&dd==31){
            id = 362;
        }
        else if (mm==7&&dd==31){
            id = 363;
        }
        else if (mm==8&&dd==31){
            id = 364;
        }
        else if (mm==10&&dd==31){
            id = 365;
        }
        else if (mm==12&&dd==31){
            id = 366;
        }
        else {
            for(int i=1; i<mm-1; i+=1) {
                id += 30;
            }
            id += dd;
        }

        return id;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onResume();
        setContentView(layout.activity_workspace);


        final ConstraintLayout bg = findViewById(id.constraintLayout);

        final Spinner f_dd = findViewById(id.spinner);
        final EditText f_yy = findViewById(id.editText2);
        final Spinner f_mm = findViewById(id.spinner2);
        final TextView display = findViewById(id.textView2);
        final Button find = findViewById(id.button2);
        final Button clear = findViewById(id.button3);
        final TextView enter = findViewById(id.textView);
        final TextView date_disp = findViewById(id.textView7);
        final TextView back = findViewById(id.textView8);

        final TextView trivia = findViewById(id.textView3);
        final TextView title = findViewById(id.textView4);
        final TextView date = findViewById(id.textView5);

        final int dd = f_dd.getSelectedItemPosition();
        final int mm = f_mm.getSelectedItemPosition();
        final String year = f_yy.getText().toString();

        find.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                try {
                    int r = find_odd();
                    int index = trivia_id() - 1;

                    if (f_dd.getSelectedItemPosition() != 0 && f_mm.getSelectedItemPosition() != 0 && year.compareTo("Year") != 0) {

                        display.setText(getResources().getStringArray(array.days)[r]);
                        YoYo.with(Techniques.FadeIn)
                                .duration(1000)
                                .repeat(0)
                                .playOn(display);

                        if (validate() != 1) {

                            title.setText(string.trivia);
                            date.setText(f_dd.getSelectedItem() + " " + f_mm.getSelectedItem().toString());
                            date.setPaintFlags(date.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

                            String text = getResources().getStringArray(array.trivia)[index];
                            trivia.setText(text);

                            date_disp.setText(f_dd.getSelectedItem() + " " + f_mm.getSelectedItem().toString() + " " + f_yy.getText().toString());

                            YoYo.with(Techniques.FadeInUp)
                                    .duration(750)
                                    .repeat(0)
                                    .playOn(title);
                            YoYo.with(Techniques.FadeIn)
                                    .duration(750)
                                    .repeat(0)
                                    .playOn(date);
                            YoYo.with(Techniques.FadeIn)
                                    .duration(750)
                                    .repeat(0)
                                    .playOn(trivia);
                            YoYo.with(Techniques.FadeIn)
                                    .duration(750)
                                    .repeat(0)
                                    .playOn(bg);
                            YoYo.with(Techniques.FadeOut)
                                    .duration(750)
                                    .repeat(0)
                                    .playOn(find);
                            YoYo.with(Techniques.FadeOut)
                                    .duration(750)
                                    .repeat(0)
                                    .playOn(clear);


                            find.setClickable(false);
                            clear.setClickable(false);

                            find.setAlpha(0f);
                            clear.setAlpha(0f);
                            bg.setBackgroundResource(drawable.back3);
                            back.setText("BACK");

                        }
                    }

                } catch(Exception e){
                    Toast t = Toast.makeText(workspace.this, "Please enter the full date", Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                f_dd.setSelection(0);
                f_yy.setText("");
                f_mm.setSelection(0);
                display.setText("");
                trivia.setText("");
                title.setText("");
                date.setText("");
                date_disp.setText("");

                find.setAlpha(1f);
                clear.setAlpha(1f);
                f_dd.setAlpha(1f);
                f_mm.setAlpha(1f);
                f_yy.setAlpha(1f);
                enter.setAlpha(1f);

                find.setClickable(true);
                clear.setClickable(true);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                YoYo.with(Techniques.FadeOut)
                        .duration(1000)
                        .repeat(0)
                        .playOn(display);

                if(validate()!=1) {

                    YoYo.with(Techniques.FadeOut)
                            .duration(750)
                            .repeat(0)
                            .playOn(title);
                    YoYo.with(Techniques.FadeOut)
                            .duration(750)
                            .repeat(0)
                            .playOn(date);
                    YoYo.with(Techniques.FadeOut)
                            .duration(750)
                            .repeat(0)
                            .playOn(trivia);
                    YoYo.with(Techniques.FadeOut)
                            .duration(750)
                            .repeat(0)
                            .playOn(bg);
                    YoYo.with(Techniques.FadeIn)
                            .duration(750)
                            .repeat(0)
                            .playOn(find);
                    YoYo.with(Techniques.FadeIn)
                            .duration(750)
                            .repeat(0)
                            .playOn(clear);


                    bg.setBackgroundResource(drawable.back3);
                    find.setClickable(true);
                    clear.setClickable(true);

                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(workspace.this, home.class);
        startActivity(intent);
        onPause();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        onPause();
    }
}




