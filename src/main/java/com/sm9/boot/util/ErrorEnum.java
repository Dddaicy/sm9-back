package com.sm9.boot.util;

public enum ErrorEnum {

    /*登录模块*/
    E_91001("91001", "登陆用户不存在", "该用户不存在！"),
    E_91002("91002", "登陆账号密码错误", "账号或密码错误，请重新输入。"),
    E_91003("91003", "用户名或密码为空", "账号或密码为空，请重新输入。"),
    E_91004("91004", "token异常", "登陆已过期,请重新登陆"),
    /*设备管理模块*/
    E_92001("92001", "查找中心侧设备不存在", "该中心侧设备不存在！"),
    E_92002("92002", "查找终端侧设备不存在", "该终端侧设备不存在！"),
    E_92003("92003", "查找设备权限不足", "当前用户设备权限不足！"),
    E_92004("92004", "重复添加中心侧设备", "该中心侧设备已存在！"),
    E_92005("92005", "重复添加终端侧设备", "该终端侧设备已存在！"),
    E_92006("92006", "添加设备权限不足", "当前用户设备权限不足！"),
    E_92007("92007", "删除中心侧设备失败", "该设备不存在！"),
    E_92008("92008", "删除终端侧设备失败", "该设备不存在！"),
    E_92009("92009", "删除设备权限不足", "当前用户设备权限不足！"),
    /*系统管理模块*/
    E_93001("93001", "查找用户不存在", "该用户不存在！"),
    E_93002("93002", "重复添加用户", "该用户已存在！"),
    E_93003("93003", "删除用户失败", "该用户不存在！"),
    E_93004("93004", "查找角色不存在", "该角色不存在！"),
    E_93005("93005", "重复添加角色", "该角色已存在！"),
    E_93006("93006", "删除角色失败", "该角色不存在！"),
    E_93007("93007", "操作权限不足", "当前用户操作权限不足！"),
    /*数据管理模块*/
    E_94001("94001","数据库连接超时", "数据库连接超时，请稍后再试。"),
    E_94002("94002","数据库断开",	"数据库断开，请联系管理员。"),
    E_94003("94003","数据库溢出",	"数据库溢出，请联系管理员。"),
    /*网络配置模块*/
    E_95001("95001", "IP设置出错", "IP设置出错，请重新设置！"),
    E_95002("95002", "子网掩码设置出错", "子网掩码设置出错，请重新设置！"),
    E_95003("95003", "网关设置出错", "网关设置出错，请重新设置！"),
    /*资源管理模块*/
    E_96001("96001", "系统参数读取失败", "系统参数读取失败，请稍后重试！"),
    E_96002("96002", "系统参数请求超时", "系统参数请求超时，请联系管理员！"),
    /*日志管理模块*/
    E_97001("97001", "数据库写满溢出", "数据库溢出，请联系管理员！"),
    E_97002("97002", "日志写入失败", "日志写入失败，请联系管理员！");



    private final String errorCode;
    private final String errorMsg;
    private final String errorReason;

    ErrorEnum(String errorCode, String errorReason, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.errorReason = errorReason;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getErrorReason() {
        return errorReason;
    }
}
