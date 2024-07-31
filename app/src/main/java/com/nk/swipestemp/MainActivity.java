package com.nk.swipestemp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button swipeRefreshRecyclerviewBtn, swipeRefreshRecyclerviewHorizontalBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        setReferences();

        getSupportActionBar().hide();

        swipeRefreshRecyclerviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SwipeRefreshRecyclerViewActivity.class));
            }
        });

        swipeRefreshRecyclerviewHorizontalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), SwipeRefreshRecyclerViewHorizontalActivity.class));
            }
        });
    }

    private void setReferences() {
        swipeRefreshRecyclerviewBtn = findViewById(R.id.swipe_refresh_recyclerview_btn);
        swipeRefreshRecyclerviewHorizontalBtn = findViewById(R.id.swipe_refresh_recyclerview_horizontal_btn);
    }
}