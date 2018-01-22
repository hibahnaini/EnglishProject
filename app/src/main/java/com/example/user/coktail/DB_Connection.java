package com.example.user.coktail;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by user on 08-Jan-18.
 */

public class DB_Connection extends SQLiteOpenHelper {

    private static String DB_Name="coktail.sqlite";
    private Context mcontext;
    private String DB_PATH = "data/data/com.example.user.coktail/databases/";
    private SQLiteDatabase db;

    public DB_Connection(Context context) {
        super(context,DB_Name,null,1);
        this.mcontext=context;

    }

    public void createDataBase()  throws IOException {
        //check if the database exists
        boolean databaseExist = checkDataBase();

        if (!databaseExist) {

            this.getWritableDatabase();

            try {
                copyDataBase();
            }catch (Exception e){
                throw new Error("Error Copying Database!");
            }

        }// end createDataBase().

    }

    public boolean checkDataBase(){
        File databaseFile = new File(DB_PATH + DB_Name);
        return databaseFile.exists();
    }

    private void copyDataBase() throws IOException{

        InputStream input=mcontext.getAssets().open(DB_Name);
        String outFileName=DB_PATH+DB_Name;
        OutputStream output=new FileOutputStream(outFileName);
        byte[] buffer=new byte[1024];
        int length;
        while ((length=input.read(buffer))>0){
            output.write(buffer,0,length);
        }
        output.flush();
        output.close();
        input.close();

    }

    public void openDataBase() throws SQLException {

        String myPath = DB_PATH + DB_Name;
        if(db!=null && db.isOpen()){
            return;
        }

        db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {
        if(db != null)
            db.close();
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    private String col,stat;
    private  String query;
       public ArrayList<String> getCoktails() throws IOException{

        openDataBase();
        ArrayList<String> Ing=MyApp.getIng();
        ArrayList<String> res= new ArrayList<String>();
        String str="";
        Cursor cursor;
        for(String ing : Ing){
            str=str+"'"+ing.toLowerCase()+"'";
            if(Ing.indexOf(ing)!=Ing.size()-1){
                str=str+",";
            }
        }
        query= "SELECT r.name,ri.quantity,i.name from coktail r"+
                " INNER JOIN CoktailIngredients ri on r.id=ri.CID"+
                " INNER JOIN ingredients i on i.id=ri.IID"+
                " WHERE i.name IN ("+str+")"+
                " and r.id not in (select r.id from coktail r"+
                " INNER JOIN CoktailIngredients ri on r.id=ri.CID"+
                " INNER JOIN ingredients i on i.id=ri.IID"+
                " WHERE i.name not IN ("+str+"))";
        Log.d("MSG",query);
        cursor = db.rawQuery(query, null);
        //MyApp.setC(cursor);
        cursor.moveToFirst();
        String name="";
        while (!cursor.isAfterLast()) {
            if(!cursor.getString(0).equals(name)) {
                res.add(cursor.getString(0));
            }
            name=cursor.getString(0);
            cursor.moveToNext();

        }

        cursor.close();


        db.close();
        if(res!=null)
            return res;
        else return null;
    }

    public HashMap<String, String> getIngredients() throws IOException{

        openDataBase();
        ArrayList<String> Ing=MyApp.getIng();
        HashMap<String,String> res= new  HashMap<String, String>();
        String str="";
        Cursor cursor;
        for(String ing : Ing){
            str=str+"'"+ing.toLowerCase()+"'";
            if(Ing.indexOf(ing)!=Ing.size()-1){
                str=str+",";
            }
        }
        query= "SELECT ri.quantity,i.name from coktail r"+
                " INNER JOIN CoktailIngredients ri on r.id=ri.CID"+
                " INNER JOIN ingredients i on i.id=ri.IID"+
                " WHERE i.name IN ("+str+")"+
                " and r.id not in (select r.id from coktail r"+
                " INNER JOIN CoktailIngredients ri on r.id=ri.CID"+
                " INNER JOIN ingredients i on i.id=ri.IID"+
                " WHERE i.name not IN ("+str+")) and r.name='"+MyApp.getCoktail().trim()+"'";
        Log.d("MSG",query);
        cursor = db.rawQuery(query, null);
        //MyApp.setC(cursor);
        cursor.moveToFirst();
        String name="";
        while (!cursor.isAfterLast()) {
            res.put(cursor.getString(1),cursor.getString(0));

            //name=cursor.getString(0);
            cursor.moveToNext();

        }

        cursor.close();


        db.close();
        if(res!=null)
            return res;
        else return null;
    }

}
