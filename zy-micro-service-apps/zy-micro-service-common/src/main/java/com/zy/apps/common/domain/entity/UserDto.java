package com.zy.apps.common.domain.entity;

import lombok.*;

import java.util.List;

/**
 * 登录用户信息
 * Created by macro on 2020/6/19.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private Integer status;
    private List<String> roles;
}

