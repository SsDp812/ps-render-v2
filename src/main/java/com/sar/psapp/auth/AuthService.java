package com.sar.psapp.auth;

import com.sar.psapp.commons.BaseDataResponse;
import com.sar.psapp.dto.request.RegisterDto;
import com.sar.psapp.model.AppUser;


public interface AuthService {

    public BaseDataResponse<?> registerNewUser(RegisterDto registerDto);

    public BaseDataResponse<String> getCurrentLogin();
    public BaseDataResponse<AppUser> getCurrentAccount();
}
