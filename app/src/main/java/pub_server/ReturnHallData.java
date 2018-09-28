package pub_server;

public class ReturnHallData extends ReturnBase {
    private HallDataList mHallDataList;

    public HallDataList getHallDataList() {
        return mHallDataList;
    }

    public void setHallDataList(HallDataList hallDataList) {
        mHallDataList = hallDataList;
    }
}
