package ua.valentino.Project1.dtos.account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDto {
    @NotBlank(message = "Username не може бути порожнім")
    private String username;

    @Email(message = "Email невалідний")
    @NotBlank(message = "Email не може бути порожнім")
    private String email;

    @NotBlank(message = "Пароль не може бути порожнім")
    private String password;

    @NotBlank(message = "Підтвердження пароля не може бути порожнім")
    private String confirmPassword;
}
