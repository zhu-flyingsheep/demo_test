package com.utsoft.myapplication.model;

/**
 * Created by wangzhu on 2017/3/8.
 * func:
 */

public class UserResult {

    /**
     * flag : 1
     * result : 登陆成功,谢谢
     * JSESSIONID : 25B02E40906A06B21D95064E7F9D390A
     * IMID : 06291136174770002
     * SERISE : 977c743b62636828d97caa4d06be4e93
     */

    private int flag;
    private String result;
    private String JSESSIONID;
    private String IMID;
    private String SERISE;

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getJSESSIONID() {
        return JSESSIONID;
    }

    public void setJSESSIONID(String JSESSIONID) {
        this.JSESSIONID = JSESSIONID;
    }

    public String getIMID() {
        return IMID;
    }

    public void setIMID(String IMID) {
        this.IMID = IMID;
    }

    public String getSERISE() {
        return SERISE;
    }

    public void setSERISE(String SERISE) {
        this.SERISE = SERISE;
    }
}
