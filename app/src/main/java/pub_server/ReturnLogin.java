package pub_server;

public class ReturnLogin extends ReturnBase{
    private UserInfo mUserInfo;

    public ReturnLogin(){
    }
    public UserInfo getUserInfo() {
        return mUserInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        mUserInfo = userInfo;
    }
}
