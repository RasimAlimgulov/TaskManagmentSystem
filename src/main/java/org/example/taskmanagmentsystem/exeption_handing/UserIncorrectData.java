package org.example.taskmanagmentsystem.exeption_handing;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserIncorrectData {
String message;
}
