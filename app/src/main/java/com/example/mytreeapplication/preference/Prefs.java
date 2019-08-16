package com.example.mytreeapplication.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {
    private static SharedPreferences preferences;
    private static SharedPreferences.Editor editor;
    private static final String USER_F_NAME = "userName";
  //  private static final String USER_L_NAME = "lastName";
   // private static final String MALE = "male";
  //  private static final String FEMALE = "female";
   // private static final String OTHERS = "others";
    private static final String EMAIL = "email";
    private static final String MOBILE = "mobile";
    private static final String PASSWORD = "password";
    private static final String AUTH = "auth";

        public static void init(Context context){
        preferences=context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        editor=preferences.edit();
        }

    public static void setUserName(String userName){
        editor.putString(USER_F_NAME,userName);
    }
    public static String getUserName(){
        return preferences.getString(USER_F_NAME,"");

    }
    public static void setEmail(String email){
        editor.putString(EMAIL,email);
    }
    public static String getEmail(){
        return preferences.getString(EMAIL,"");
        }
    public static void setMobile(String mobile){
        editor.putString(MOBILE,mobile);
    }
    public static String getMobile(){
        return preferences.getString(MOBILE,"");

    }
    public static void setPassword(String password){
        editor.putString(PASSWORD,password);
    }
    public static String getPassword(){
        return preferences.getString(PASSWORD,"");
    }
    public static void setAuth(Boolean auth){
        editor.putBoolean(AUTH,auth);
    }
    public static boolean getAuth(){
        return preferences.getBoolean(AUTH,false);
    }

    public static void commit(){
        editor.commit();
    }
}
