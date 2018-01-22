package com.example.user.coktail;

import android.app.Application;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by user on 10-Jan-18.
 */

public class MyApp extends Application {
    private static ArrayList<String> Ingredients=new ArrayList<String>();
    private int i;
   // private static Cursor c;
    private static String coktail;




    public void setIng(ArrayList<String> Ing){
        Ingredients.clear();
        Ingredients=(ArrayList<String>) Ing.clone();
    }

    public static ArrayList<String> getIng(){
        return Ingredients;
    }
    public void setI(int i){
        this.i=i;
    }
    public void setCoktail(String name){
        coktail=name;
    }
    public static String getCoktail(){
        return coktail;
    }
}
