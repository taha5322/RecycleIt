package com.siddiqui.recycleit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button recycleButton;
    private Button donateButton;
    private Button secondHandButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycleButton = findViewById(R.id.recycleTrashButton);
        recycleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecycleTrashActivity.class);
                startActivity(intent);
            }
        });
        donateButton = findViewById(R.id.donateButton);
        donateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RecylingCentreActivity.class);
                startActivity(intent);
            }
        });
        secondHandButton = findViewById(R.id.secondHand);
        secondHandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondHandSalesActivity.class);
                startActivity(intent);
            }
        });
    }
}
