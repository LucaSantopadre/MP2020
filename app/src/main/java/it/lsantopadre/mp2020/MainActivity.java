package it.lsantopadre.mp2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import it.lsantopadre.mp2020.customactivity.GANActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnGan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGan = findViewById(R.id.btnGan);
        btnGan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, GANActivity.class);
        startActivity(intent);
    }
}
