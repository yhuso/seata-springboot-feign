package com.aha.tech.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author: ahaschool
 * @date: 2019-06-03 11:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CannotAddMemberPrivilegeException extends RuntimeException {
    private String reason;

    public CannotAddMemberPrivilegeException(String reason) {
        super(reason);
        this.reason = reason;
    }
}
