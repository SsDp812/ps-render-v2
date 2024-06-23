package com.sar.psapp.rest;


import com.sar.psapp.commons.BaseDataResponse;
import com.sar.psapp.dto.request.RegisterDto;
import com.sar.psapp.dto.response.UserProfile;
import com.sar.psapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserRest {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/logIn")
    public BaseDataResponse<UserProfile> logIn(){
        return userService.logIn();
    }

    @GetMapping(value = "/getProfile")
    public BaseDataResponse<UserProfile> getProfile(){
        return userService.getProfile();
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseDataResponse<UserProfile> register(@RequestBody RegisterDto dto){
        return userService.register(dto);
    }
}
