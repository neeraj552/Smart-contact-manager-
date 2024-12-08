package com.scm.scm20.forms;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserForm {
    @NotBlank(message = "Username is required")
    @Size(min=3 ,message = "Min 3 Character is required")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Address")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min=6,message ="Min 6 Character is required" )
    private String password;
    @NotBlank(message = "About is required")
    @Size(max=250,message = "Max 250 Character is required")
    private String about;
    @Size(min=8,max = 12,message = "Invalid Phone Number")
    private String phoneNumber;
}
