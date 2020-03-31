package it.lsantopadre.mp2020.customactivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import it.lsantopadre.mp2020.R;

public class GANActivity extends AppCompatActivity  {

    Context GANContext;
    TextView tevText, tvLife, tvMin, tvMax;
    ImageView img;
    EditText edtNumber;
    Holder holder;
    Random n = new Random();
    int randomNum, userNumber, life;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ganactivity);
        GANContext = getApplicationContext();
        init();
    }

    // initialization
    private void init(){
        life = 4;
        randomNum = n.nextInt(100) + 1; // on rotate this is ovewrited by restoreinsancestate

        holder = new Holder();
        tevText = findViewById(R.id.tevText);
        tevText.setText("Welcome to Guess a number game...\nInsert a number");

        edtNumber = findViewById(R.id.edtNumber);
        edtNumber.setText(null);

        // range min - max
        tvMin = findViewById(R.id.tvMin);
        tvMax = findViewById(R.id.tvMax);
        tvMin.setText(String.valueOf(1));
        tvMax.setText(String.valueOf(100));

        img = findViewById(R.id.imvImage);
        img.setImageDrawable(getDrawable(R.drawable.welcome));

        // life in game
        tvLife = findViewById(R.id.tvLife);
        tvLife.setText("Life " + life);
        tvLife.setTextColor(Color.GREEN);
    }


    private void checkNumber(){
        userNumber = Integer.parseInt(edtNumber.getText().toString());

        // if userNumber is not in range min - max : retry
        if(userNumber > Integer.parseInt(tvMax.getText().toString()) || userNumber < Integer.parseInt(tvMin.getText().toString())){
            Toast.makeText(GANContext, "Range not valid!", Toast.LENGTH_SHORT).show();
            edtNumber.setText(null);
            return;
        }

        if(userNumber > randomNum){
            holder.tooHight();
        } else if(userNumber < randomNum){
            holder.tooLow();
        }else {
            holder.youWin();
            return;
        }

        life--;
        tvLife.setText("Life " + life);
        // check life remaining
        if(life > 0){
            tevText.setText("Insert a number");
        } else{
            holder.youLose();
            tevText.setText("Click crying bender to restart...\npss the number is " + randomNum);
        }
    }


    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("life", life);
        outState.putInt("userNumber", userNumber);
        outState.putInt("randomNum", randomNum);
        outState.putString("rangeMax", tvMax.getText().toString());
        outState.putString("rangeMin", tvMin.getText().toString());
        outState.putBoolean("btnEnabled", holder.btnAttempt.isEnabled());
        outState.putString("text", tevText.getText().toString());
        outState.putString("tvLifeString", tvLife.getText().toString());

        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
        outState.putParcelable("image", bitmap);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        if(savedInstanceState != null){
            life = savedInstanceState.getInt("life");
            userNumber = savedInstanceState.getInt("userNumber");
            randomNum = savedInstanceState.getInt("randomNum");
            tvMax.setText(savedInstanceState.getString("rangeMax"));
            tvMin.setText(savedInstanceState.getString("rangeMin"));
            holder.btnAttempt.setEnabled(savedInstanceState.getBoolean("btnEnabled"));
            tevText.setText(savedInstanceState.getString("text"));
            tvLife.setText(savedInstanceState.getString("tvLifeString"));
            img.setImageBitmap((Bitmap)savedInstanceState.getParcelable("image"));
        }
    }


    class Holder implements View.OnClickListener{

        private Button btnAttempt;
        private ImageView imvImage;

        Holder(){
            btnAttempt = findViewById(R.id.btnAttempt);
            btnAttempt.setOnClickListener(this);
            imvImage = findViewById(R.id.imvImage);
            imvImage.setOnClickListener(this);
            btnAttempt.setEnabled(true);
        }

        private void tooHight(){
            Toast.makeText(GANContext, "Too Hight!", Toast.LENGTH_SHORT).show();
            tvMax.setText(String.valueOf(userNumber));
            imvImage.setImageDrawable(getDrawable(R.drawable.toohigh));
            edtNumber.setText(null);
            edtNumber.requestFocus();
        }

        private void tooLow(){
            Toast.makeText(GANContext, "Too Low!", Toast.LENGTH_SHORT).show();
            tvMin.setText(String.valueOf(userNumber));
            imvImage.setImageDrawable(getDrawable(R.drawable.toolow));
            edtNumber.setText(null);
            edtNumber.requestFocus();
        }

        private void youWin(){
            Toast.makeText(GANContext, "You Win!", Toast.LENGTH_SHORT).show();
            imvImage.setImageDrawable(getDrawable(R.drawable.win));
            btnAttempt.setEnabled(false);
            tevText.setText("CONGRATULATION!\nClick bender to restart.");
        }

        private void youLose(){
            Toast.makeText(GANContext, "You Lose!", Toast.LENGTH_SHORT).show();
            imvImage.setImageDrawable(getDrawable(R.drawable.lose));
            btnAttempt.setEnabled(false);
            tvLife.setTextColor(Color.RED);
        }


        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btnAttempt){
                checkNumber();
            }
            if(v.getId() == R.id.imvImage){
                init();
            }
        }
    }
}


