package com.example.game.annotation;

import com.example.game.vaild.StatusValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = StatusValidator.class) // Trỏ đến lớp kiểm tra
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface ValidStatus {
    // Phương thức bắt buộc
    String message() default "Giá trị không hợp lệ. Phải khớp với các hằng số đã định nghĩa.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
