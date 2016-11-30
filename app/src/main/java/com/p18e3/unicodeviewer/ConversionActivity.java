package com.p18e3.unicodeviewer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ConversionActivity extends AppCompatActivity {

    // region Private fields

    private TextView tv_unicodeOutput;
    private TextView tv_hexInput;
    private TextView tv_binaryString;
    private EditText et_hexInputString;
    private Dialog aboutDialog;

    private int hexInputNumber = 0x0021;

    SharedPreferences sharedPreferences;

    // endregion

    // region Overrides

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);

        //sharedPreferences = getPreferences(MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("UnicodeViewer", MODE_PRIVATE);

        tv_unicodeOutput = (TextView) findViewById(R.id.tv_unicodeOutput);
        tv_hexInput = (TextView) findViewById(R.id.tv_hexInput);
        tv_binaryString = (TextView) findViewById(R.id.tv_binaryString);
        et_hexInputString = (EditText) findViewById(R.id.et_hexInputString);

        int lastHexNumber = sharedPreferences.getInt(getString(R.string.preferences_lastHexNumber), 33);
        hexInputNumber = lastHexNumber;

        String lastHexNumberString = sharedPreferences.getString(getString(R.string.preferences_lastHexNumberString), "33");
        et_hexInputString.setText(lastHexNumberString);

        updateUserInterface();

        Log.d("INFO", "onCreate: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("INFO", "onResume: ");

        int backgroundColor = sharedPreferences.getInt(getString(R.string.preferences_backgroundColor), -3355444);
        int fontColor = sharedPreferences.getInt(getString(R.string.preferences_fontColor), -3355444);
        int textSize = sharedPreferences.getInt(getString(R.string.preferences_textSize), 80);

        tv_unicodeOutput.setBackgroundColor(backgroundColor);
        tv_unicodeOutput.setTextColor(fontColor);
        tv_unicodeOutput.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);

        String fontType = sharedPreferences.getString(getString(R.string.preferences_fontType), "sans-serif");
        int fontStyle = sharedPreferences.getInt(getString(R.string.preferences_fontStyle), 0);
        tv_unicodeOutput.setTypeface(Typeface.create(fontType, fontStyle));
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

            case R.id.action_close_app:
                closeApplication();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(getString(R.string.preferences_lastHexNumber), hexInputNumber);
        editor.putString(getString(R.string.preferences_lastHexNumberString), et_hexInputString.getText().toString());

        editor.commit();
    }

    // endregion

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

        String binaryString = String.format("%16s", Integer.toBinaryString(hexInputNumber)).replace(' ', '0');
        tv_binaryString.setText(binaryString);

        char unicodeSymbol = (char) hexInputNumber;
        String unicodeSymbolText = String.format("%s", unicodeSymbol);
        tv_unicodeOutput.setText(unicodeSymbolText);
    }

    private void closeApplication() {
        this.finishAndRemoveTask();
        System.exit(0);
    }
}