package com.p18e3.unicodeviewer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

public class SettingsActivity extends AppCompatActivity {

    private Spinner sp_fontType;
    private Spinner sp_fontStyle;
    private SeekBar sb_textSize;

    int selectedBackgroundColor;
    int selectedFontColor;
    String selectedFontType;
    int selectedFontStyle;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sp_fontType = (Spinner)findViewById(R.id.sp_fontType);
        sp_fontStyle = (Spinner)findViewById(R.id.sp_fontStyle);
        sb_textSize = (SeekBar)findViewById(R.id.sb_textSize);

        initializeSpinners();
        sharedPreferences = getSharedPreferences("UnicodeViewer", MODE_PRIVATE);
    }

    public void openColorPickerForBackgroundColor(View view) {

        Log.d("INFO", "openColorPickerForBackgroundColor: ");

        final ColorPicker cp = new ColorPicker(SettingsActivity.this, 205, 201, 201);
        cp.show();

        Button okColor = (Button) cp.findViewById(R.id.okColorButton);
        okColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedBackgroundColor = cp.getColor();
                cp.dismiss();
            }
        });
    }

    public void openColorPickerForFontColor(View view) {

        Log.d("INFO", "openColorPickerForBackgroundColor: ");

        final ColorPicker cp = new ColorPicker(SettingsActivity.this, 0, 0, 0);
        cp.show();

        Button okColor = (Button) cp.findViewById(R.id.okColorButton);
        okColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedFontColor = cp.getColor();
                cp.dismiss();
            }
        });
    }

    public void saveChanges(View view){

        Log.d("INFO", String.valueOf(sb_textSize.getProgress()));
        Log.d("INFO", String.valueOf(selectedFontColor));

        if(selectedBackgroundColor != 0){
            sharedPreferences.edit().putInt(getString(R.string.preferences_backgroundColor), selectedBackgroundColor).commit();
        }

        if(selectedFontColor != 0){
            sharedPreferences.edit().putInt(getString(R.string.preferences_fontColor), selectedFontColor).commit();
        }

        if(sb_textSize.getProgress() != 0){
            sharedPreferences.edit().putInt(getString(R.string.preferences_textSize), sb_textSize.getProgress()).commit();
        }

        sharedPreferences.edit().putString(getString(R.string.preferences_fontType), selectedFontType).commit();
        sharedPreferences.edit().putInt(getString(R.string.preferences_fontStyle), selectedFontStyle).commit();

        this.finish();
    }

    public void closeApplication(View view) {
        this.finishAffinity();
        finishAndRemoveTask();
        //System.exit(0);
    }

    private void initializeSpinners(){

        ArrayAdapter<CharSequence> fontTypeAdapter = ArrayAdapter.createFromResource(this, R.array.fontTypesArray, R.layout.support_simple_spinner_dropdown_item);
        fontTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_fontType.setAdapter(fontTypeAdapter);
        sp_fontType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedFontType = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ArrayAdapter<CharSequence> fontStyleAdapter = ArrayAdapter.createFromResource(this, R.array.fontStylesArray, R.layout.support_simple_spinner_dropdown_item);
        fontStyleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_fontStyle.setAdapter(fontStyleAdapter);
        sp_fontStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedFontStyle = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
}