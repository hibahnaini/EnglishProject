package com.example.user.coktail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Coktail extends AppCompatActivity {
    private ArrayList<String> result;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coktail);
        DB_Connection data=new DB_Connection(this);
        intent=new Intent(Coktail.this, Ingredients.class);

      try {
        result = data.getCoktails();
    }catch (Exception e){
        Toast.makeText(Coktail.this,"Error in getCoktails",Toast.LENGTH_SHORT).show();
    }
        if(result!=null){
        ArrayAdapter<String> items=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,result);
            ListView lst=(ListView)findViewById(R.id.listView);
        lst.setAdapter(items);
    }

        else{
        Toast.makeText(Coktail.this,"Error",Toast.LENGTH_SHORT).show();
    }


    AddListenerOnList();

}
    public void AddListenerOnList(){
        ListView lst=(ListView)findViewById(R.id.listView);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                       public void onItemClick(AdapterView<?> parentAdapter, View view, int position,
                                                               long id) {
                                           TextView clickedView = (TextView) view;

                                           ((MyApp) getApplication()).setCoktail(clickedView.getText().toString());

                                           Toast.makeText(Coktail.this, clickedView.getText().toString(), Toast.LENGTH_SHORT).show();

                                           startActivity(intent);

                                       }
                                   }


        );
    }
}
