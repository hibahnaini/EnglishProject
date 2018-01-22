package com.example.user.coktail;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private ArrayList<String> result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DB_Connection data = new DB_Connection(this);

        try {
            data.createDataBase();


        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();


        }
    }

        public void addTxt(View view){
            LinearLayout linearLayout =(LinearLayout) findViewById(R.id.txts);

            // Create EditText
            final EditText editText = new EditText(this);
            editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            // Add EditText to LinearLayout
            if (linearLayout != null) {
                linearLayout.addView(editText);
            }
            editText.requestFocus();
            editText.setGravity(Gravity.CENTER);
            editText.setTextColor(Color.parseColor("#ff669900"));


        }

        public void search(View v){
            LinearLayout linearLayout =(LinearLayout) findViewById(R.id.txts);
            ArrayList<String> myEditTextValues = new ArrayList<String>();

            for( int i = 0; i < linearLayout.getChildCount(); i++ ) {
                if (linearLayout.getChildAt(i) instanceof EditText) {
                    myEditTextValues.add(((EditText) linearLayout.getChildAt(i)).getText().toString().toLowerCase().trim());
                    Toast.makeText(MainActivity.this, ((EditText) linearLayout.getChildAt(i)).getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
           ((MyApp) this.getApplication()).setIng(myEditTextValues);
            Intent i = new Intent(MainActivity.this, Coktail.class);
            startActivity(i);
        }

}

