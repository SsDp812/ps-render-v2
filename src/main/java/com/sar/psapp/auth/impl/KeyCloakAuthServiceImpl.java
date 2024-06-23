package com.sar.psapp.auth.impl;

import com.sar.psapp.auth.AuthService;
import com.sar.psapp.auth.LocalUser;
import com.sar.psapp.commons.BaseDataResponse;
import com.sar.psapp.commons.errors.KeycloakError;
import com.sar.psapp.commons.errors.UserError;
import com.sar.psapp.dto.request.RegisterDto;
import com.sar.psapp.model.AppUser;
import com.sar.psapp.repositories.AppUserRepository;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Optional;

@Service
public class KeyCloakAuthServiceImpl implements AuthService {
    @Value("${app.conf.security.keycloak.url}")
    private String keyCloakUrl;

    @Value("${app.conf.security.keycloak.realm}")
    private String realm;
    @Value("${app.conf.security.keycloak.admin.master_realm}")
    private String masterRealm;
    @Value("${app.conf.security.keycloak.admin.client_id}")
    private String adminClientId;
    @Value("${app.conf.security.keycloak.admin.username}")
    private String keyCloakAdminUserName;
    @Value("${app.conf.security.keycloak.admin.password}")
    private String keyCloakAdminPassword;

    @Autowired
    private AppUserRepository accountRepository;

    @Override
    public BaseDataResponse<?> registerNewUser(RegisterDto registerDto) {
        //добавление нового пользователя в keycloak
        BaseDataResponse<?> response = new BaseDataResponse<>();
        Keycloak adminClient = KeycloakBuilder.builder()
                .serverUrl(keyCloakUrl)
                .realm(masterRealm)
                .clientId(adminClientId)
                .grantType(OAuth2Constants.PASSWORD)
                .username(keyCloakAdminUserName)
                .password(keyCloakAdminPassword)
                .build();
        UserRepresentation user = new UserRepresentation();
        user.setUsername(registerDto.getUserName());

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(registerDto.getPassword());
        credential.setTemporary(false);
        user.setCredentials(Arrays.asList(credential));
        user.setEnabled(true);
        try {
            Response r = adminClient.realm(realm).users().create(user);
        }catch (Exception ex){
            response.addError(KeycloakError.KEYCLOAK_ERROR_REGISTER.message);
        }
        return response.build();

    }

    @Override
    public BaseDataResponse<String> getCurrentLogin() {
        BaseDataResponse<String> response = new BaseDataResponse<>();
        String userName = LocalUser.getCurrentUserName();
        if(userName == null){
            response.addError(UserError.ACCOUNT_NOT_FOUND.message);
        }
        response.setData(userName);
        return response.build();
    }

    @Override
    public BaseDataResponse<AppUser> getCurrentAccount() {
        BaseDataResponse<AppUser> response = new BaseDataResponse<>();
        BaseDataResponse<String> loginResponse = this.getCurrentLogin();
        if(!loginResponse.getSuccess()){
            response.setErrors(loginResponse.getErrors());
            return response;
        }
        Optional<AppUser> optionalAccount = accountRepository.findByUserName(loginResponse.getData());
        if(optionalAccount.isEmpty()){
            response.addError(UserError.ACCOUNT_NOT_FOUND.message);
            return response.build();
        }
        response.setData(optionalAccount.get());
        return response.build();
    }
}
