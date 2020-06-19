package com.example.account.dto;

import com.example.common.api.BaseResponse;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SecurityAccountResponse extends BaseResponse {
    private SecurityAccountDto account;
}
