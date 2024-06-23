package com.sar.psapp.auth;

public class LocalUser {
    //Текущий пользователь (от которого пришел запрос)
    private static final ThreadLocal<String> currentUserName = new ThreadLocal<>();

    public static void setCurrentUserName(String value) {
        currentUserName.set(value);
    }
    public static String getCurrentUserName(){
        return currentUserName.get();
    }
}
