package pub_server;

public class ReturnBase {
    private Boolean success;
    private String code;
    private String msg;


    public ReturnBase() {
        success = false;
        code = "";
        msg = "";
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
