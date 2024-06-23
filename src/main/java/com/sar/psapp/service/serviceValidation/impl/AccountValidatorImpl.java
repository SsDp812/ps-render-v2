package com.sar.psapp.service.serviceValidation.impl;

import com.sar.psapp.commons.BaseDataResponse;
import com.sar.psapp.commons.errors.UserError;
import com.sar.psapp.dto.request.RegisterDto;
import com.sar.psapp.model.AppUser;
import com.sar.psapp.repositories.AppUserRepository;
import com.sar.psapp.service.serviceValidation.AccountValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


import java.util.Optional;

@Component
public class AccountValidatorImpl implements AccountValidator {
    @Autowired
    private AppUserRepository accountRepository;

    @Value("${app.conf.platform.settings.users.login.min_len}")
    private Long minLoginLength;
    @Value("${app.conf.platform.settings.users.login.max_len}")
    private Long maxLoginLength;
    @Value("${app.conf.platform.settings.users.password.min_len}")
    private Long minPasswordLength;
    @Value("${app.conf.platform.settings.users.password.max_len}")
    private Long maxPasswordLength;
    @Override
    public BaseDataResponse<?> validateUserForRegister(RegisterDto registerDto) {
        BaseDataResponse<?> response = new BaseDataResponse<>();
        if(StringUtils.isEmpty(registerDto.getUserName())){
            response.addError(UserError.LOGIN_TOO_SHORT.message);
        }else if(registerDto.getUserName().length() < minLoginLength){
            response.addError(UserError.LOGIN_TOO_SHORT.message);
        }else if(registerDto.getUserName().length() > maxLoginLength){
            response.addError(UserError.LOGIN_TOO_LONG.message);
        }else{
            Optional<AppUser> optionalAccount = accountRepository.findByUserName(registerDto.getUserName());
            if(optionalAccount.isPresent()){
                response.addError(UserError.LOGIN_NOT_UNIQU.message);
                return response.build();
            }
        }

        if(StringUtils.isEmpty(registerDto.getPassword())){
            response.addError(UserError.PASSWORD_TOO_SHORT.message);
        }else if(registerDto.getPassword().length() < minPasswordLength){
            response.addError(UserError.PASSWORD_TOO_SHORT.message);
        }else if(registerDto.getPassword().length() > maxPasswordLength){
            response.addError(UserError.PASSWORD_TOO_LONG.message);
        }

        return response.build();
    }
}
