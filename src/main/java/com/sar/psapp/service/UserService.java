package com.sar.psapp.service;

import com.sar.psapp.commons.BaseDataResponse;
import com.sar.psapp.dto.request.RegisterDto;
import com.sar.psapp.dto.response.UserProfile;

public interface UserService {
    public BaseDataResponse<UserProfile> logIn();
    public BaseDataResponse<UserProfile> getProfile();
    public BaseDataResponse<UserProfile> register(RegisterDto registerDto);
}
