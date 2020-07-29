package com.max.fftopup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConstraintLayout main = (ConstraintLayout)findViewById(R.id.main);
        final TextView charge = (TextView)findViewById(R.id.charge);
        final TextView total = (TextView)findViewById(R.id.total);
        final EditText topup = (EditText)findViewById(R.id.topup);
        Button proceed = (Button)findViewById(R.id.proceed);
        Button enter = (Button)findViewById(R.id.enter);
        final TextView date = (TextView)findViewById(R.id.date);
        Button reset = (Button)findViewById(R.id.reset);
        final EditText uid = (EditText)findViewById(R.id.uid);
        final Integer[] flag = {0};

        //Hide keyboard when touched on the screen
        main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });

        //On softkeyboard enter button
        topup.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    try {
                        if (uid.getText().toString().length() == 0 || uid.getText().toString().length() < 9)
                        {
                            uid.setError("Enter valid in-game User ID !!");
                        }
                        else if (topup.getText().toString().length() == 0)
                        {
                            topup.setError("Enter Top-Up amount !!");
                        }
                        else
                        {
                            int t = Integer.parseInt(topup.getText().toString());
                            int c;
                            if (t % 10 == 9) {
                                t = t + 1;
                            }
                            if (t % 100 == 0) {
                                c = t / 10;
                            } else {
                                c = ((t / 100) + 1) * 10;
                            }
                            charge.setText(Integer.toString(c));
                            total.setText(Integer.toString((c + t)));
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                            String currentDateandTime = sdf.format(new Date());
                            //date.setText(currentDateandTime);
                            SpannableString content = new SpannableString(currentDateandTime);
                            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                            date.setText(content);
                            hideKeyboard(v);
                            handled = true;
                            flag[0] =1;
                        }
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), "INPUT DETAILS !!", Toast.LENGTH_SHORT).show();
                        uid.setText("");
                        topup.setText("");
                        date.setText("Date");
                        charge.setText("");
                        total.setText("");
                        flag[0] = 0;
                        return true;
                    }
                }
                return handled;
            }
        });

        //Enter button click function
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try {
                    if (uid.getText().toString().length() == 0 || uid.getText().toString().length() < 9)
                    {
                        uid.setError("Enter valid in-game User ID !!");
                    }
                    else if (topup.getText().toString().length() <= 0)
                    {
                        topup.setError("Enter Top-Up amount !!");
                    }
                    else
                    {
                        // || Integer.parseInt(topup.getText().toString())==0
                        int t = Integer.parseInt(topup.getText().toString());
                        int c;
                        if (t % 10 == 9) {
                            t = t + 1;
                        }
                        if (t % 100 == 0) {
                            c = t / 10;
                        } else {
                            c = ((t / 100) + 1) * 10;
                        }
                        charge.setText(Integer.toString(c));
                        total.setText(Integer.toString((c + t)));
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
                        String currentDateandTime = sdf.format(new Date());
                        //date.setText(currentDateandTime);
                        SpannableString content = new SpannableString(currentDateandTime);
                        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                        date.setText(content);
                        hideKeyboard(v);
                        flag[0] = 1;
                    }
                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "INPUT DETAILS !!", Toast.LENGTH_SHORT).show();
                    uid.setText("");
                    topup.setText("");
                    date.setText("Date");
                    charge.setText("");
                    total.setText("");
                    flag[0] = 0;
                    return;
                }
            }
        });

        //on Proceed button click fuction
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag[0]==1)
                {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("UID", uid.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getApplicationContext(), "UID copied to clipboard", Toast.LENGTH_SHORT).show();
                    Uri uri = Uri.parse("https://gameskharido.in/app/100067/idlogin");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Enter Correct Values to Proceed Further !!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //on reset button click function
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uid.setText("");
                topup.setText("");
                date.setText("Date");
                charge.setText("");
                total.setText("");
                Toast.makeText(MainActivity.this, "Values reset !!", Toast.LENGTH_SHORT).show();
            }
        });


    }

    //hide keyboard function
    void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //exit confirmation on back pressed
    public void onBackPressed(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Confirm Exit..");
        alertDialogBuilder.setMessage("Are you sure want to exit the app?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            // @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Exit successfull !", Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Cancel successfull !", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog=alertDialogBuilder.create();
        alertDialog.show();
    }
}