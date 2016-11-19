package com.p18e3.unicodeviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ConversionActivity extends AppCompatActivity {

    private TextView tv_unicodeOutput;
    private TextView tv_hexInput;
    private int hexInputNumber = 0x0021;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        tv_unicodeOutput = (TextView) findViewById(R.id.tv_unicodeOutput);
        tv_hexInput = (TextView) findViewById(R.id.tv_hexInput);
        updateUserInterface();
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

    public void incrementHexInput(View view) {
        hexInputNumber++;
        updateUserInterface();
    }

    public void decrementHexInput(View view) {
        hexInputNumber--;
        updateUserInterface();
    }

    private void updateUserInterface() {
        String hexInputString = String.format("#%04X", hexInputNumber);
        tv_hexInput.setText(hexInputString);

        char unicodeSymbol = (char) hexInputNumber;

        String unicodeSymbolText = String.format("%s", unicodeSymbol);
        tv_unicodeOutput.setText(unicodeSymbolText);
    }
}