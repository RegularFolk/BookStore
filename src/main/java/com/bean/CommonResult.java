package com.bean;

public class CommonResult {
    //表示服务器端处理请求成功或失败
    private static final boolean SUCCESS = true;
    private static final boolean FAILED = false;
    private boolean flag;        //服务器端处理请求的标识
    private Object resultData;   //服务器端处理请求成功时要显示给客户端的数据
    private String message;      //服务器端处理请求失败的时候要相应给客户端的错误信息

    /**
     * 处理请求成功
     */
    public static CommonResult ok() {
        return new CommonResult().setFlag(SUCCESS);
    }

    /**
     * 处理请求失败
     */
    public static CommonResult error() {
        return new CommonResult().setFlag(FAILED);
    }

    public boolean isFlag() {
        return flag;
    }

    private CommonResult setFlag(boolean flag) {
        this.flag = flag;
        return this;
    }

    public Object getResultData() {
        return resultData;
    }

    public CommonResult setResultData(Object resultData) {
        this.resultData = resultData;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CommonResult setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "flag=" + flag +
                ", resultData=" + resultData +
                ", message='" + message + '\'' +
                '}';
    }


}
