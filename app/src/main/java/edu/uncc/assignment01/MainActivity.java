package edu.uncc.assignment01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset(v);
            }
        });

        findViewById(R.id.calcButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(v);
            }
        });

        SeekBar seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean b) {
                RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroupSale);
                radioGroup.check(R.id.radioButtonCustom);
                TextView discount = (TextView)findViewById(R.id.seekText);
                discount.setText((progressValue/2) + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStartTrackingTouch: " + seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStopTrackingTouch: " + seekBar.getProgress());
            }
        });
    }

    public void calculate(View view) {
        EditText listPrice = (EditText)findViewById(R.id.editTextNumber);
        TextView discount = (TextView)findViewById(R.id.discountAmount);
        TextView result = (TextView)findViewById(R.id.finalPrice);

        //check for empty list price and show toast if true
        if(listPrice.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter a list price", Toast.LENGTH_SHORT).show();
            return;
        }

        //Read the discount amount from the radio button
        double discountRate = 0;
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroupSale);
        RadioButton selectedRadioButton = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
        String radioButtonString = selectedRadioButton.getText().toString();
        Log.d(TAG, "Radio Button Selected: " + radioButtonString);
        if(radioButtonString.equals("Custom")) {
            String preString = ((TextView)findViewById(R.id.seekText)).getText().toString();;
            discountRate = Double.parseDouble(preString.substring(0, preString.length() - 1));
        } else {
            discountRate = Double.parseDouble(radioButtonString.substring(0, radioButtonString.length() - 1));
        }

        double listPriceValue = Double.parseDouble(listPrice.getText().toString());

        double discountAmount = listPriceValue * (discountRate/100);
        discount.setText("$" + String.format("%.2f" ,discountAmount));
        double finalPrice = listPriceValue - discountAmount;

        result.setText("$" + String.format("%.2f",finalPrice));
    }

    public void reset(View view) {
        EditText listPrice = (EditText)findViewById(R.id.editTextNumber);
        TextView discount = (TextView)findViewById(R.id.discountAmount);
        TextView result = (TextView)findViewById(R.id.finalPrice);
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroupSale);

        seekBar.setProgress(50);
        radioGroup.check(R.id.radioButton10);
        listPrice.setText(null);
        discount.setText("$0.00");
        result.setText("$0.00");
    }
}