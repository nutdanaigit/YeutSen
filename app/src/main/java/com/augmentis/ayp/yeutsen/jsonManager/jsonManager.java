package com.augmentis.ayp.yeutsen.jsonManager;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Nutdanai on 10/24/2016.
 */

public class jsonManager {
    private static final String stretchFile = "stretch.json";

    private static Context context;
    private static jsonManager instance;
    private ArrayList<HashMap<String, String>> stretchList;

    public static jsonManager getInstance(Context context) {
        if(instance == null){
            instance = new jsonManager(context);
        }
        return instance;
    }

    public ArrayList<HashMap<String, String>> getStetchList(){
        return stretchList;
    }

    private jsonManager(Context context){
        this.context = context.getApplicationContext();
        stretchList = new ArrayList<>();
        jsonStringToList(jsonFileToString(stretchFile));
    }

    private void jsonStringToList(String jsonString){
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("stretch");
            HashMap<String, String> hashMapList;

            for(int i = 0 ; i < jsonArray.length() ; i++){
                JSONObject jsonObjectInside = jsonArray.getJSONObject(i);
                String stretchId = jsonObjectInside.getString("sid");
                String stretchName = jsonObjectInside.getString("sname");
                String stretchInfo = jsonObjectInside.getString("sinfo");
                String stretchPath = jsonObjectInside.getString("spath");
                String isSelected = jsonObjectInside.getString("isselect");

                hashMapList = new HashMap<>();
                hashMapList.put("sid", stretchId);
                hashMapList.put("sname", stretchName);
                hashMapList.put("sinfo", stretchInfo);
                hashMapList.put("spath", stretchPath);
                hashMapList.put("isselect", isSelected);

                stretchList.add(hashMapList);
            }
        }catch(Exception e){

        }
    }


    private String jsonFileToString(String jsonFileName){
        String jsonString;
        try{
            InputStream inputStream = context.getAssets().open(jsonFileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer, "UTF-8");
            return jsonString;
        }catch(Exception e){
            return null;
        }
    }

}
