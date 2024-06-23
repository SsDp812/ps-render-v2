package com.sar.psapp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfile {
    //TODO в будущем надо будет добавить состоания
    //TODO добавить количество оставшихся дней до конца подписки
    private String userName;
    private String email;
}
