package com.innilabs.inniboard.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel*/
@Data
public class User {
    
    private String userId;
    private String password;
    private String name;
}