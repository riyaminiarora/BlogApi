package com.gfg.BlogAppApis.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {
    private Integer categoryId;
    @Size(min=4,message="Title must be of atleast 4 characters") @NotNull @NotEmpty
    private String categoryTitle;
    @Size(min=10,message="Description must be of atleast 10 characters") @NotNull @NotEmpty
    private String categoryDescription;
}
