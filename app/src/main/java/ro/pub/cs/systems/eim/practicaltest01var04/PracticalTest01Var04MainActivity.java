package ro.pub.cs.systems.eim.practicaltest01var04;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01Var04MainActivity extends AppCompatActivity {

    private EditText editText1, editText2, displayText;
    private CheckBox checkBox1, checkBox2;
    private Button navigateButton, displayButton;

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();

    private IntentFilter intentFilter = new IntentFilter();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(Constants.BROADCAST_TAG, intent.getStringExtra(Constants.BROADCAST_KEY));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_main);

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        displayText = findViewById(R.id.displayText);

        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);

        navigateButton = findViewById(R.id.navigateButton);
        displayButton = findViewById(R.id.displayButton);

        displayButton.setOnClickListener(v -> {
            if ((checkBox1.isChecked() && editText1.getText().toString().length() == 0) ||
                    (checkBox2.isChecked() && editText2.getText().toString().length() == 0)) {
                Toast.makeText(this, "Please fill in the checked fields", Toast.LENGTH_SHORT).show();
            } else {
                String displayString = "";
                if (checkBox1.isChecked()) {
                    displayString += editText1.getText().toString() + " ";
                }
                if (checkBox2.isChecked()) {
                    displayString += editText2.getText().toString();
                }
                displayText.setText(displayString);
            }

            if (editText1.getText().toString().length() > 0 && editText2.getText().toString().length() > 0) {
                Intent pushIntent = new Intent(this, PracticalTest01Var04Service.class);
                pushIntent.putExtra(Constants.TEXT1_KEY, editText1.getText().toString());
                pushIntent.putExtra(Constants.TEXT2_KEY, editText2.getText().toString());
                startService(pushIntent);
            }
        });

        navigateButton.setOnClickListener(v -> {
            Intent intent = new Intent("ro.pub.cs.systems.eim.practicaltest01var04.PracticalTest01Var04SecondaryActivity");
            intent.putExtra(Constants.TEXT1_KEY, editText1.getText().toString());
            intent.putExtra(Constants.TEXT2_KEY, editText2.getText().toString());
            startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_INTENT_CODE);
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Constants.TEXT1_KEY, editText1.getText().toString());
        savedInstanceState.putString(Constants.TEXT2_KEY, editText1.getText().toString());
        savedInstanceState.putString(Constants.DISPLAY_TEXT_KEY, displayText.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(Constants.TEXT1_KEY)) {
            editText1.setText(savedInstanceState.getString(Constants.TEXT1_KEY));
        }
        if (savedInstanceState.containsKey(Constants.TEXT2_KEY)) {
            editText2.setText(savedInstanceState.getString(Constants.TEXT2_KEY));
        }
        if (savedInstanceState.containsKey(Constants.DISPLAY_TEXT_KEY)) {
            displayText.setText(savedInstanceState.getString(Constants.DISPLAY_TEXT_KEY));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SECONDARY_ACTIVITY_INTENT_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "The activity returned with result ok", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "The activity returned with result cancel", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PracticalTest01Var04Service.class));
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}