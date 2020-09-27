package lioshik.corporation.Server;

public class PlayersCountInfo {
    public int totalCount;
    public int crPosition;

    public PlayersCountInfo() {
        this.totalCount = -1;
        this.crPosition = -1;
    }

    public PlayersCountInfo(int totalCount, int crPosition) {
        this.totalCount = totalCount;
        this.crPosition = crPosition;
    }
}
