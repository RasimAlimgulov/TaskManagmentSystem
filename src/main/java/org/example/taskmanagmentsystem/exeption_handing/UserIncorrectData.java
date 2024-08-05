package com.rasimalimgulov.userservice.user_service.exeption_handing;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserIncorrectData {
String message;
}
