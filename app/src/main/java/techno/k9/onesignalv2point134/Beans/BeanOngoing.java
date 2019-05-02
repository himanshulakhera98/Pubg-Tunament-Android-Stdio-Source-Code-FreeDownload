package techno.k9.onesignalv2point134.Beans;

public class BeanOngoing {

    String title;
    String time;
    String winPrize;
    String perKill;
    String entryFee;
    String type;
    String version;
    String map;
    String joined;
    String watchlive;
    String id;


    public BeanOngoing(String title, String time, String winPrize, String perKill, String entryFee, String type, String version, String map, String joined, String watchlive, String id) {
        this.title = title;
        this.time = time;
        this.winPrize = winPrize;
        this.perKill = perKill;
        this.entryFee = entryFee;
        this.type = type;
        this.version = version;
        this.map = map;
        this.joined = joined;
        this.watchlive = watchlive;
        this.id = id;
    }

    public BeanOngoing() {
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getWinPrize() {
        return winPrize;
    }

    public String getPerKill() {
        return perKill;
    }

    public String getEntryFee() {
        return entryFee;
    }

    public String getType() {
        return type;
    }

    public String getVersion() {
        return version;
    }

    public String getMap() {
        return map;
    }

    public String getJoined() {
        return joined;
    }

    public String getWatchlive() {
        return watchlive;
    }

    public String getId() {
        return id;
    }
}
