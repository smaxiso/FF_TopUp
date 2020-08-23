package com.max.fftopup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.firebase.analytics.FirebaseAnalytics;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private int code = 1;
    public static int count = 0;
    public static ArrayList<String> tempList=new ArrayList<String>();
    public static SharedPreferences sp;
    public static String str = "";
    public static final String mypreference = "mypref";
    public static final String value = "valuekey";
    public static final String counter = "countkey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setting Actionbar name
        getSupportActionBar().setTitle("FF TopUp - Home   ");

        //Defining views
        ConstraintLayout main = (ConstraintLayout)findViewById(R.id.main);
        final TextView charge = (TextView)findViewById(R.id.charge);
        final TextView total = (TextView)findViewById(R.id.total);
        final EditText topup = (EditText)findViewById(R.id.topup);
        Button proceed = (Button)findViewById(R.id.proceed);
        Button enter = (Button)findViewById(R.id.enter);
        Button log = (Button)findViewById(R.id.log);
        final TextView date = (TextView)findViewById(R.id.date);
        Button reset = (Button)findViewById(R.id.reset);
        final EditText uid = (EditText)findViewById(R.id.uid);
        final Integer[] flag = {0};

        //Creating firebase variables
//        FirebaseAnalytics data = new;
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        final DatabaseReference myrefuid = database.getReference("UID");
//        final DatabaseReference myrefamnt = myrefuid.child("amount");
//        final DatabaseReference myrefdate = myrefuid.child("date");

        //setting log value in log activity
        final String[] oldValue = {""};
        sp = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if(sp.contains(value)) {
            oldValue[0] = sp.getString(value, "");
        }
        str = oldValue[0];


        //Hide keyboard when touched on the screen
        main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard(v);
                return false;
            }
        });


//        //On softkeyboard enter button
//        topup.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {boolean handled = false;
//                if (actionId == EditorInfo.IME_ACTION_GO)
//                {
//                    try
//                    {
//                        if (uid.getText().toString().length() == 0 || uid.getText().toString().length() < 9)
//                        {
//                            uid.setError("Enter valid in-game User ID !!");
//                        }
//                        else if (topup.getText().toString().length() == 0)
//                        {
//                            topup.setError("Enter Top-Up amount !!");
//                        }
//                        else
//                        {
//                            int t = Integer.parseInt(topup.getText().toString());
//                            int c;
//                            if (t % 10 == 9) {
//                                t = t + 1;
//                            }
//                            if (t % 100 == 0) {
//                                c = t / 10;
//                            } else {
//                                c = ((t / 100) + 1) * 10;
//                                if(t>100)
//                                  c-=10;
//                            }
//                            charge.setText(Integer.toString(c));
//                            total.setText(Integer.toString((c + t)));
//                            SimpleDateFormat sdf = new SimpleDateFormat("'Date: ' dd-MM-yyyy',' 'Time: ' HH:mm:ss");
//                            String currentDateandTime = sdf.format(new Date());
//                            SpannableString content = new SpannableString(currentDateandTime);
//                            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//                            date.setText(content);
//                            hideKeyboard(v);
//                            handled = true;
//                            flag[0] =1;
//                        }
//                    }
//                    catch (Exception e)
//                    {
//                        Toast.makeText(getApplicationContext(), "INPUT DETAILS !!", Toast.LENGTH_SHORT).show();
//                        uid.setText("");
//                        topup.setText("");
//                        date.setText("Date");
//                        charge.setText("");
//                        total.setText("");
//                        flag[0] = 0;
//                        return true;
//                    }
//                }
//                return handled;
//            }
//        });

        //Enter button click function
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try
                {
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
                        int t = Integer.parseInt(topup.getText().toString());
                        int c;
                        if (t % 10 == 9)
                        {
                            t = t + 1;
                        }
                        if (t % 100 == 0)
                        {
                            c = t / 10;
                        }
                        else
                        {
                            c = ((t / 100) + 1) * 10;
                            if(t>100)
                                c-=10;
                        }
                        charge.setText(Integer.toString(c));
                        total.setText(Integer.toString((c + t)));
                        SimpleDateFormat sdf = new SimpleDateFormat("'Date: ' dd-MM-yyyy',' 'Time: ' HH:mm:ss");
                        String currentDateandTime = sdf.format(new Date());
                        SpannableString content = new SpannableString(currentDateandTime);
                        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                        date.setText(content);
                        hideKeyboard(v);
                        flag[0] = 1;
                    }
                }
                catch (Exception e)
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
                    if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    {
                        //using FireBase Backend to store realtime data
                        //myrefuid.setValue(uid.getText().toString());
//                        Map<Integer, String> details = new HashMap<>();
//                        details.put(Integer.parseInt(topup.getText().toString()), date.getText().toString());
//                        myrefamnt.setValue(details);
//                        myrefamnt.setValue(topup.getText());
//                        myrefdate.setValue(date.getText());

                        //sending data to log activity
                        //String oldValue = "";
                        //sp = getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                        if(sp.contains(counter)) {
                            count = Integer.parseInt(sp.getString(counter, "").trim());
                        }
                        if(sp.contains(value)) {
                            oldValue[0] = sp.getString(value, "");
                        }
                        count++;
                        //tempList.add(Integer.toString(count) +". " + date.getText().toString() + "\n\t  UID: " + uid.getText().toString() + ", Amount: " + topup.getText().toString() + " RS.\n\n");
                        String newValue = Integer.toString(count) +". " + date.getText().toString() + "\n\t  UID: " + uid.getText().toString() + ", Amount: " + topup.getText().toString() + " RS.\n\n";
                        str = oldValue[0] + newValue;
                        String c = Integer.toString(count);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString(counter, c);
                        editor.putString(value, str);
                        editor.commit();

                        //copying UID in clipboard
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("UID", uid.getText().toString());
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(getApplicationContext(), "UID copied to clipboard", Toast.LENGTH_SHORT).show();

                        //opening topip site in chrome
                        Uri uri = Uri.parse("https://gameskharido.in/app/100067/idlogin");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                    else
                    {
                        permission();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Enter Correct informations to Proceed Further !!", Toast.LENGTH_SHORT).show();
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
                flag[0]=0;
                Toast.makeText(MainActivity.this, "Values reset !!", Toast.LENGTH_SHORT).show();
            }
        });


        //on Log button click function
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LogActivity.class);
                startActivity(intent);
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
        alertDialogBuilder.setCancelable(true);
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

    //function to take screenshot
    public static Bitmap getss(View view)
    {
        View Screenview = view.getRootView();
        Screenview.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(Screenview.getDrawingCache());
        Screenview.setDrawingCacheEnabled(false);
        return bitmap;
    }

    //function to store the screenshot in device
    public void store(Bitmap bm, String filename)
    {
        File dir_name = Environment.getExternalStorageDirectory();
        File dir = new File(dir_name.getAbsolutePath() + "/FF/");
        if(!dir.exists())
        {
            dir.mkdirs();
        }
        File file = new File(dir, filename);
        try
        {
            FileOutputStream fos = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(this, "saved SS", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "error in saving SS", Toast.LENGTH_SHORT).show();
        }
    }

    //run time permission ask function
    private void permission()
    {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            new  AlertDialog.Builder(this)
                    .setTitle("Permission Required !!")
                    .setMessage("This permission is required to store ScreenShots on your device's storage.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, code);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, code);
        }
    }

    //function to validate storage access permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == code)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}