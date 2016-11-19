package com.p18e3.unicodeviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ConversionActivity extends AppCompatActivity {

    private TextView tv_unicodeOutput;
    private TextView tv_hexInput;
    private ImageButton but_incrementHexInput;
    private ImageButton but_decrementHexInput;
    private int hexInputNumber = 0x0021;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        initializeControls();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_set_color:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeControls() {

        // TextView for Hex input.
        tv_hexInput = (TextView) findViewById(R.id.tv_hexInput);
        String hexInputString = String.format("#%04X", hexInputNumber);
        tv_hexInput.setText(hexInputString);

        // TextView für Unicode output.
        tv_unicodeOutput = (TextView) findViewById(R.id.tv_unicodeOutput);
        //tv_unicodeOutput.setText("\u0021");

        // Button to increment hex input.
        but_incrementHexInput = (ImageButton) findViewById(R.id.but_incrementHexInput);
        but_incrementHexInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hexInputNumber++;
                String hexInputString = String.format("#%04X", hexInputNumber);
                tv_hexInput.setText(hexInputString);
                convertToUnicodeSign(hexInputNumber);
            }
        });

        // Button to decrement hex input.
        but_decrementHexInput = (ImageButton) findViewById(R.id.but_decrementHexInput);
        but_decrementHexInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hexInputNumber--;
                String hexInputString = String.format("#%04X", hexInputNumber);
                tv_hexInput.setText(hexInputString);
                convertToUnicodeSign(hexInputNumber);
            }
        });

        convertToUnicodeSign(hexInputNumber);
    }

    private void convertToUnicodeSign(int hexString) {
        //int decimalNumber = Integer.parseInt(hexString, 16);
        char unicodeSymbol = (char) hexString;

        String unicodeSymbolText = String.format("%s", unicodeSymbol);
        tv_unicodeOutput.setText(unicodeSymbolText);
    }
}