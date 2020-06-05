package com.aha.tech.exception;

import com.aha.tech.commons.response.RpcResponse;

public enum ExceptionCodeMessage {


    /**
     *
     */
    DEFAULT_ERROR_CODE(224200,"系统默认异常"),

    ID_NULL_ERROR(224201,"id不能为空"),
    MESSAGE_NULL_ERROR(224202,"消费数据必须填字段值为空"),
    MESSAGE_LOG_SERVICE_NULL_ERROR(224203,"消息日志service为空"),
    MESSAGE_REPEATABLE_ERROR(224204,"重复消费"),
    LEVEL_REPEATABLE_ERROR(224205,"权益等级重复"),
    GOODS_TYPE_CONVERT_ERROR(224206,"标准型不能转营销型"),
    EXPIRE_TIME_BEFORE_ERROR(224207,"修改后的过期时间不能小于当前过期时间"),
    REPEATABLE_CONTRACT_ERROR(224208,"该用户已签约，请先解约"),
    MARKING_BUY_REPEATABLE_ERROR(224209,"同等级营销只能购买一次"),
    MARKING_BUY_FORBIDDEN_ERROR(224210,"未过期会员禁止购买同等级营销会员"),
    ILLEGAL_USER_ID(224211,"userId不能小于0"),

    ARGUMENT_NULL_ERROR(224212, "参数不正确！"),
    MEMBER_ORDER_DATA_ERROR(224213, "member_order跟privilegeLog数据不对应"),
    MEMBER_ORDER_TYPE_ERROR(224213, "member_order_type不在枚举范围内"),
    CONTRACT_ORDER_CAN_NOT_REVERT(224214, "签约单不能回收"),
    WITHHOLD_ORDER_CAN_NOT_REVERT(224215, "代扣单不能回收"),
    OVERDUE_ORDER_CAN_NOT_REVERT(224216, "过期单不能回收")



    ;
    int code;
    String message;

    ExceptionCodeMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public RpcResponse<?> rpcResponse() {
        return new RpcResponse<>(this.code, this.message);
    }

    public BadRequestParameterException badRequestParameterException() {
        return new BadRequestParameterException(this.message, this.code);
    }

    public ServiceException serviceException() {
        return new ServiceException(this.message, this.code);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
