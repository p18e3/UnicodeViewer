package com.p18e3.unicodeviewer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConversionActivity extends AppCompatActivity {

    private TextView tv_unicodeOutput;
    private TextView tv_hexInput;
    private EditText et_hexInputString;
    private int hexInputNumber = 0x0021;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        tv_unicodeOutput = (TextView) findViewById(R.id.tv_unicodeOutput);
        tv_hexInput = (TextView) findViewById(R.id.tv_hexInput);
        et_hexInputString = (EditText) findViewById(R.id.et_hexInputString);

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

    public void removeDigit(View view) {
        String currentText = et_hexInputString.getText().toString();

        if (currentText.length() == 0)
            return;

        String updatedText = currentText.substring(0, currentText.length() - 1);
        et_hexInputString.setText(updatedText);
    }

    public void writeDigit(View view) {
        TextView textView = (TextView) view;

        String currentText = et_hexInputString.getText().toString();
        et_hexInputString.setText(currentText + textView.getText().toString());
    }

    public void setHexInputNumber(View view) {

        // Validate the user input.
        if (et_hexInputString.getText().length() == 0) {
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Please type in a hex number.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        hexInputNumber = Integer.parseInt(et_hexInputString.getText().toString(), 16);
        updateUserInterface();
    }

    public void incrementCodePart(View view) {
        if (hexInputNumber == 65535) {
            return;
        }

        hexInputNumber++;
        updateUserInterface();
    }

    public void decrementCodePart(View view) {
        if (hexInputNumber == 0)
            return;

        hexInputNumber--;
        updateUserInterface();
    }

    public void incrementPagePart(View view) {
        if (hexInputNumber > 65280)
            return;

        hexInputNumber += 256;
        updateUserInterface();
    }

    public void decrementPagePart(View view) {
        if (hexInputNumber < 256)
            return;

        hexInputNumber -= 256;
        updateUserInterface();
    }

    private void updateUserInterface() {
        String hexInputString = String.format("0x%04X", hexInputNumber);
        tv_hexInput.setText(hexInputString);

        char unicodeSymbol = (char) hexInputNumber;

        String unicodeSymbolText = String.format("%s", unicodeSymbol);
        tv_unicodeOutput.setText(unicodeSymbolText);
    }
}