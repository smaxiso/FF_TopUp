package com.max.fftopup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        //changing name of action bar
        getSupportActionBar().setTitle("FF TopUp - Log");
//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        //creating view varriables
        final TextView logs = (TextView)findViewById(R.id.logs);
        ConstraintLayout constraintLayout = (ConstraintLayout)findViewById(R.id.loglayout);
        logs.setMovementMethod(new ScrollingMovementMethod());

        Button resetlog = (Button)findViewById(R.id.resetlog);
        Button home = (Button)findViewById(R.id.home);

//        //showing logs in the text view
//        for(int i=0; i<MainActivity.tempList.size(); ++i)
//                logs.append(MainActivity.tempList.get(i));

        logs.setText(MainActivity.str);


        //on home button click function
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(LogActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
            }
        });

        //on resetlog button click function
        resetlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!logs.getText().toString().isEmpty())
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LogActivity.this);
                    alertDialogBuilder.setTitle("Confirm Reset..");
                    alertDialogBuilder.setMessage("Are you sure want to reset the log?\nYou won't be able to revert the action once you confirm reset.");
                    alertDialogBuilder.setCancelable(true);
                    alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        // @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //MainActivity.tempList.clear();

                            MainActivity.sp = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = MainActivity.sp.edit();
                            editor.clear();
                            editor.commit();
                            MainActivity.count = 0;
                            MainActivity.str="";
                            logs.setText("");
                            Toast.makeText(LogActivity.this, "Logs reset successfull", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(LogActivity.this, "Cancel successfull !", Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
                else
                {
                    Toast.makeText(LogActivity.this, "Already empty !", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}