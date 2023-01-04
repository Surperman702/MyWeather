package com.cxz.kotlin.baselibs.aroute;

/**
 * <pre>
 *     @author : yitouniu
 *     time   : 2021/02/04
 *     desc   :
 *     路由统一管理类  、
 *     路径 以 model 名称作为跟路径 后接路由activity 名称
 * </pre>
 */
public interface AppRoute {
    /**
     * 宿主app组件
     */
    String APP = "/app";
    /**
     * nurse app组件
     */
    String NURSE = "/nurse";

    /**
     * 参数 用户信息
     */
    String PARAMS_USER_INFO = "user_info";

    /**
     * 科室
     */
    String MANAGE_DEPARTMENT = "manage_department";

    /**
     * 宿主APP
     */
    String APP_ACTIVITY_LOGIN = APP + "/entrance/loginActivity";

    /**
     * nurse App
     */
    String NURSE_ACTIVITY_MAIN = NURSE + "/MainActivity";

}