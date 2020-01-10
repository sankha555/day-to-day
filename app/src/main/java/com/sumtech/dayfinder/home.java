package com.sumtech.dayfinder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class home extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onResume();
        setContentView(R.layout.activity_home);

        Button go = findViewById(R.id.button);
        TextView credits = findViewById(R.id.textview9);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, workspace.class);
                startActivity(intent);
            }
        });

        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new AlertDialog.Builder(home.this)
                       .setTitle("Acknowledgements")
                       .setMessage("Background Image taken from Mac OS Mojave Desktop Themes\n\nIcon Image taken from ShutterStock\n\n" +
                               "Trivia Info taken from onthisday.com and wikipedia.org\n\n"+
                               "App designed and developed by   Sankha Das")
                       .setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialogInterface, int i) {
                               home.super.onResume();
                           }
                       })
                       .show();


            }
        });

    }
}
