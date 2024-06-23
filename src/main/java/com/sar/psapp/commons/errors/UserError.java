package com.sar.psapp.commons.errors;

public enum UserError {

    LOGIN_NOT_UNIQU("Логин не уникальный"),
    LOGIN_TOO_SHORT("Логин слишком короткий"),
    LOGIN_TOO_LONG("Логин слишком длинный"),
    PASSWORD_TOO_SHORT("Пароль слишком короткий"),
    PASSWORD_TOO_LONG("Пароль слишком длинный"),
    ACCOUNT_NOT_FOUND("Аккаунт не найден");

    public String message;
    UserError(String s) {
        this.message = s;
    }
}
