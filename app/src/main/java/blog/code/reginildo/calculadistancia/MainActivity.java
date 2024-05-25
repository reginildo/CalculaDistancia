package blog.code.reginildo.calculadistancia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button buttonOkay, buttonClean;
    EditText editTextP1X, editTextP1Y, editTextP1Z;
    EditText editTextP2X, editTextP2Y, editTextP2Z;
    TextView textViewDistHorizon, textViewDistIncl, textViewPercent;

    double p1x, p1y, p1z, p2x, p2y, p2z;
    double distHorizon, distIncl, diffCoordinatesZ, percentIncl;
    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextP1X = findViewById(R.id.editTextNumberDecimalP1X);
        editTextP1Y = findViewById(R.id.editTextNumberDecimalP1Y);
        editTextP1Z = findViewById(R.id.editTextNumberDecimalP1Z);

        editTextP2X = findViewById(R.id.editTextNumberDecimalP2X);
        editTextP2Y = findViewById(R.id.editTextNumberDecimalP2Y);
        editTextP2Z = findViewById(R.id.editTextNumberDecimalP2Z);

        textViewDistHorizon = findViewById(R.id.textViewDistHorizon);
        textViewDistIncl = findViewById(R.id.textViewDistIncl);
        textViewPercent = findViewById(R.id.textViewPercent);

        buttonOkay = findViewById(R.id.buttonOkay);
        buttonClean = findViewById(R.id.buttonClean);
        setButtonListeners();
    }

    private void setButtonListeners() {
        buttonOkay.setOnClickListener(new ButtonOkayListener());
        buttonClean.setOnClickListener(new ButtonCleanListener());
    }

    private class ButtonOkayListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (isThereAnyEditTextEmpty()) {
                toAlert();
                editTextP1X.requestFocus();
            } else {
                setCoordinateValues();
                calculateTheDistances();
                calculatePercent();
                submitValues();
            }
        }
    }

    private void calculatePercent() {
        percentIncl = diffCoordinatesZ / distHorizon;
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    private void submitValues() {
        textViewDistHorizon.setText(String.format("%.2f", distHorizon));
        textViewDistIncl.setText(String.format("%.2f", distIncl));
        textViewPercent.setText("%: " + String.format("%.5f", percentIncl));
    }

    private void calculateTheDistances() {
        diffCoordinatesZ = p1z - p2z;
        distHorizon = Math.sqrt(Math.pow(p2x - p1x, 2) + Math.pow(p2y - p1y, 2));
        distIncl = Math.sqrt(Math.pow(diffCoordinatesZ, 2) + Math.pow(distHorizon, 2));
    }

    private void setCoordinateValues() {
        p1x = Double.parseDouble(editTextP1X.getText().toString());
        p1y = Double.parseDouble(editTextP1Y.getText().toString());
        p1z = Double.parseDouble(editTextP1Z.getText().toString());
        p2x = Double.parseDouble(editTextP2X.getText().toString());
        p2y = Double.parseDouble(editTextP2Y.getText().toString());
        p2z = Double.parseDouble(editTextP2Z.getText().toString());
    }

    private boolean isThereAnyEditTextEmpty() {
        return TextUtils.isEmpty(editTextP1X.getText().toString()) |
                TextUtils.isEmpty(editTextP1Y.getText().toString()) |
                TextUtils.isEmpty(editTextP1Z.getText().toString()) |
                TextUtils.isEmpty(editTextP2X.getText().toString()) |
                TextUtils.isEmpty(editTextP2Y.getText().toString()) |
                TextUtils.isEmpty(editTextP2Z.getText().toString());
    }

    private void toAlert() {
        alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle(R.string.title_caution);
        alertDialog.setMessage(R.string.message_field_empty);
        alertDialog.setNeutralButton(R.string.message_okay, null);
        alertDialog.show();
    }

    private class ButtonCleanListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            clearFields();
        }
    }

    private void clearFields() {
        editTextP1X.setText("");
        editTextP1Y.setText("");
        editTextP1Z.setText("");
        editTextP2X.setText("");
        editTextP2Y.setText("");
        editTextP2Z.setText("");
        textViewDistHorizon.setText("");
        textViewDistIncl.setText("");
        textViewPercent.setText("");

        editTextP1X.requestFocus();
    }
}