package com.siddiqui.recycleit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class ItemActivity extends AppCompatActivity {

    TextView itemName;
    TextView explanation;
    ImageView itemImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Intent intent = getIntent();

        itemName = findViewById(R.id.itemName);
        explanation = findViewById(R.id.explanationText);
        itemImage = findViewById(R.id.itemPicture);


        String item = intent.getStringExtra("Item");
        Toast.makeText(this,"The item you chose is "+item, Toast.LENGTH_SHORT).show();

        itemName.setText(item);
        explanation.setText(Items.text[ Arrays.asList(Items.items).indexOf(item) ]);
        itemImage.setImageResource(Items.layoutResource[ Arrays.asList(Items.items).indexOf(item) ]);

    }
}
