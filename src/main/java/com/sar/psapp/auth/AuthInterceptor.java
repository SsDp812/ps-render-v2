package com.sar.psapp.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
@Data
public class AuthInterceptor implements HandlerInterceptor {

    //перехватывает запрос и устанавливает текущего пользователя в LocalUser

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            KeycloakSecurityContext keycloakSecurityContext = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
            if (keycloakSecurityContext != null) {
                String login = keycloakSecurityContext.getToken().getPreferredUsername();
                LocalUser.setCurrentUserName(login);
            }
        } catch (Exception ex) {
            return true;
        }
        return true;
    }
}
