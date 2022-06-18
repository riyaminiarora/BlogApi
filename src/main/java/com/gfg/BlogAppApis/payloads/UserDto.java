package com.gfg.BlogAppApis.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private Integer id;

    @Size(min=3,message="Username must be of atleast 3 characters") @NotNull @NotEmpty
    private String name;
    @Email
    private String email;
    @NotNull @NotEmpty(message="Please provide Password")
    private String password;
    @NotNull @NotEmpty
    private String about;
    private Set<RoleDto> roles=new HashSet<>();
}
