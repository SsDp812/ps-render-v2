package com.sar.psapp.service.impl;

import com.sar.psapp.auth.AuthService;
import com.sar.psapp.commons.BaseDataResponse;
import com.sar.psapp.commons.UserRole;
import com.sar.psapp.commons.errors.KeycloakError;
import com.sar.psapp.commons.errors.UserError;
import com.sar.psapp.commons.states.UserState;
import com.sar.psapp.dto.request.RegisterDto;
import com.sar.psapp.dto.response.UserProfile;
import com.sar.psapp.model.AppUser;
import com.sar.psapp.repositories.AppUserRepository;
import com.sar.psapp.service.UserService;
import com.sar.psapp.service.serviceValidation.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserServiceImpl implements UserService {

    @Value("${app.conf.user-system.default-admin.user-name}")
    private String defaultAdminName;
    @Value("${app.conf.user-system.default-admin.password}")
    private String defaultAdminPassword;
    @Value("${app.conf.user-system.default-admin.email}")
    private String defaultAdminEmail;

    @Autowired
    private AuthService authService;

    @Autowired
    private AppUserRepository accountRepository;
    @Override
    public BaseDataResponse<UserProfile> logIn() {
        return this.getProfile();
    }

    @Autowired
    private AccountValidator accountValidator;

    @Override
    public BaseDataResponse<UserProfile> register(RegisterDto registerDto) {
        //TODO сделать чтобы пока регистировать пользователей мог только админ
        BaseDataResponse<UserProfile> response = new BaseDataResponse<>();
        BaseDataResponse<?> validationResult = accountValidator.validateUserForRegister(registerDto);
        if(!validationResult.getSuccess()){
            response.setErrors(validationResult.getErrors());
            return response.build();
        }
        BaseDataResponse<?> keyCloakResponse = authService.registerNewUser(registerDto);
        if(!keyCloakResponse.getSuccess()){
            response.addError(KeycloakError.KEYCLOAK_ERROR_REGISTER.message);
            return response.build();
        }
        AppUser account = new AppUser();
        account.setUserName(registerDto.getUserName());
        account.setEmail(registerDto.getEmail());
        account.setRole(registerDto.getRole());
        account.setState(UserState.ACTIVE);
        account = accountRepository.save(account);
        UserProfile profile = new UserProfile();
        profile.setUserName(account.getUserName());
        profile.setEmail(account.getEmail());
        response.setData(profile);
        return response.build();
    }

    @Override
    public BaseDataResponse<UserProfile> getProfile() {
        BaseDataResponse<UserProfile> response = new BaseDataResponse<>();
        BaseDataResponse<AppUser> authAccount = authService.getCurrentAccount();
        if(!authAccount.getSuccess()){
            response.addError(UserError.ACCOUNT_NOT_FOUND.message);
            return response.build();
        }
        AppUser account = authAccount.getData();
        UserProfile profile = new UserProfile();
        profile.setUserName(account.getUserName());
        profile.setEmail(account.getEmail());
        response.setData(profile);
        return response.build();
    }

    @PostConstruct
    public void initDefaultAdmin(){
        RegisterDto dto = new RegisterDto();
        dto.setRole(UserRole.ADMIN);
        dto.setUserName(defaultAdminName);
        dto.setPassword(defaultAdminPassword);
        dto.setEmail(defaultAdminEmail);
        this.register(dto);
    }
}
