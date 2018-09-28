package pub_server;

public class ReturnList extends ReturnBase{
    private int total_count;
    private ListData mListData;

    public ListData getListData() {
        return mListData;
    }

    public void setListData(ListData listData) {
        mListData = listData;
    }
}
