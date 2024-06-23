package com.sar.psapp.service.serviceValidation;


import com.sar.psapp.commons.BaseDataResponse;
import com.sar.psapp.dto.request.RegisterDto;

public interface AccountValidator {
    public BaseDataResponse<?> validateUserForRegister(RegisterDto registerDto);
}
