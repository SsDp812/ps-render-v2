package com.sar.psapp.commons.errors;

public enum KeycloakError {
    KEYCLOAK_ERROR_REGISTER("Ошибка при сохранении пользователя в keycloak");

    public String message;
    KeycloakError(String m) {
        this.message = m;
    }
}
