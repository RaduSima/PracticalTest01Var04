package ro.pub.cs.systems.eim.practicaltest01var04;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class PracticalTest01Var04SecondaryActivity extends AppCompatActivity {

    private Button okButton;
    private Button cancelButton;
    private EditText editText1, editText2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var04_secondary);

        okButton = findViewById(R.id.button1);
        cancelButton = findViewById(R.id.button2);
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);

        okButton.setOnClickListener(v -> {
            setResult(RESULT_OK);
            finish();
        });

        cancelButton.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.TEXT1_KEY) &&
                intent.getExtras().containsKey(Constants.TEXT2_KEY)) {
            String text1 = intent.getStringExtra(Constants.TEXT1_KEY);
            String text2 = intent.getStringExtra(Constants.TEXT2_KEY);
            editText1.setText(text1);
            editText2.setText(text2);
        }
    }
}