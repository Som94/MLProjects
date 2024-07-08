package com.example.textclasifications;
import com.example.textclasifications.helpers.TextClassificationClient;
import org.tensorflow.lite.support.label.Category;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText txtInput;
    private Button btnSendText;
    private TextView txtOutput;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextClassificationClient client = new TextClassificationClient(getApplicationContext());
        client.load();

        txtInput = findViewById(R.id.txtInput);
        txtOutput = findViewById(R.id.txtOutput);
        btnSendText = findViewById(R.id.btnSendText);

        btnSendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSend = txtInput.getText().toString();
                List<Category> results = client.classify(toSend);
                float score = results.get(1).getScore();
                if (score > 0.8) {
                    txtOutput.setText("Your message was detected as spam with a score of " + Float.toString(score) + " and not sent!");
                } else {
                    txtOutput.setText("Message sent! \nSpam score was:" + Float.toString(score));
                }
                txtInput.getText().clear();
            }
        });



    }
}
