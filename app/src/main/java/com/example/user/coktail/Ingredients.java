package com.example.user.coktail;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class Ingredients extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        TextView txt = (TextView) findViewById(R.id.textView);
        txt.setText(MyApp.getCoktail());
        TableLayout tl = (TableLayout) findViewById(R.id.table);
        TableRow tr_head = new TableRow(this);
        tr_head.setBackgroundColor(Color.GRAY);
        tr_head.setLayoutParams(new TableRow.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView label_date = new TextView(this);
        label_date.setText("Ingredient");
        //label_date.setTextColor(Color.WHITE);
        label_date.setPadding(5, 5, 5, 5);
        label_date.setTextSize(20);
        tr_head.addView(label_date);// add the column to the table row here

        TextView label_weight_kg = new TextView(this);
        label_weight_kg.setTextSize(20);
        label_weight_kg.setText("Quantity"); // set the text for the header
        label_weight_kg.setPadding(5, 5, 5, 5); // set the padding (if required)
        tr_head.addView(label_weight_kg); // add the column to the table row here
        tl.addView(tr_head, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        DB_Connection data = new DB_Connection(this);
        HashMap<String,String> map=new HashMap<>();
        try {
            map = data.getIngredients();
        } catch (Exception e) {
            Toast.makeText(Ingredients.this, "Error in getIngredients", Toast.LENGTH_SHORT).show();
        }


        if (map != null) {
           for (Map.Entry<String,String> m : map.entrySet()){
                    String ing = m.getKey();
                    String qty = m.getValue();



                TableRow tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(
                        TableLayout.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

//Create two columns to add as table data
                // Create a TextView to add date
                TextView labelDATE = new TextView(this);
                labelDATE.setTextSize(20);
                labelDATE.setText(ing);
                labelDATE.setPadding(2, 0, 5, 0);
                tr.addView(labelDATE);
                TextView labelWEIGHT = new TextView(this);
                labelWEIGHT.setTextSize(20);
                labelWEIGHT.setText(qty);
                tr.addView(labelWEIGHT);

// finally add this to the table row
                tl.addView(tr, new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.FILL_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));

            }
        }
    }
}
