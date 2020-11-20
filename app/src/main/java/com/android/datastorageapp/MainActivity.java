package com.android.datastorageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    public static final String FILENAME = "namafile.txt";
    public static final String PREFNAME = "com.android.datastorageapp.PREF";
    private EditText editText;
    private TextView textBaca;
    private static final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.edt);
        textBaca = findViewById(R.id.txtBaca);

    }

    private static boolean hasPermission(Context context, String... permissions){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions!= null){
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission)
                != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    public void simpan(View view) {
        simpanFileES();
    }

    public void hapus(View view) {
        deleteFileES();
    }

    public void baca(View view) {
        bacaFileES();
    }

    public void simpanFileSP(){
        String isiFile = editText.getText().toString();
        SharedPreferences sharedPreferences =  getSharedPreferences(PREFNAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FILENAME, isiFile);
        editor.commit();
    }

    public void simpanFileEP(){
        String isiFile = editText.getText().toString();
        SharedPreferences sharedPreferences =  getSharedPreferences(PREFNAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FILENAME, isiFile);
        editor.commit();
    }

    public void deleteFileSP(){
        SharedPreferences sharedPreferences = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void simpanFileIS(){
        String isiFile = editText.getText().toString();
        File path = getDir("NEWFOLDER", MODE_PRIVATE);
        File file = new File(path.toString(), FILENAME);
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            outputStream = new FileOutputStream(file, true);
            outputStream.write(isiFile.getBytes());
            outputStream.flush();
            outputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void simpanFileES(){
        if(hasPermission(this, PERMISSIONS)){
            String content = editText.getText().toString();
            File path = Environment.getExternalStorageDirectory();
            File file = new File(path.toString(), FILENAME);

            FileOutputStream outputStream = null;
            try {
                file.createNewFile();
                outputStream = new FileOutputStream(file, false);
                outputStream.write(content.getBytes());
                outputStream.flush();
                outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else{
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE);
        }

    }

    public void bacaFileIS(){
        File path = getDir("NEWFOLDER", MODE_PRIVATE);
        File file = new File(path.toString(), FILENAME);
        if (file.exists()){
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = br.readLine();
                while (line!= null){
                    text.append(line);
                    line = br.readLine();
                }
                br.close();
            }catch (IOException e) {
                e.printStackTrace();
            }

            textBaca.setText(text.toString());
        }else {
            textBaca.setText("");
        }
    }

    public void bacaFileES(){
        File path = Environment.getExternalStorageDirectory();
        File file = new File(path.toString(), FILENAME);

        if (file.exists()){
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = reader.readLine();
                while (line != null){
                    text.append(line);
                    line = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            textBaca.setText(text.toString());
        } else {
            textBaca.setText("");
        }
    }
    public void deleteFileES(){
        File path = Environment.getExternalStorageDirectory();
        File file = new File(path.toString(), FILENAME);

        if (file.exists()) file.delete();

    }

    public void deleteFileIS(){
        File path = getDir("NEWFOLDER", MODE_PRIVATE);
        File file = new File(path.toString(), FILENAME);
        if (file.exists()){
            file.delete();
        }
    }

}