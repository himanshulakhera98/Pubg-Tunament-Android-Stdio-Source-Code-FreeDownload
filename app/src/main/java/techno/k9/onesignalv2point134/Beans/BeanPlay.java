package techno.k9.onesignalv2point134.Beans;

public class BeanPlay {

    String title;
    String time;
    String winPrize;
    String perKill;
    String entryFee;
    String type;
    String version;
    String map;
    String joined;
    String anouncement;
    String id;

    public BeanPlay() {
    }

    public BeanPlay(String title, String time, String winPrize, String perKill, String entryFee, String type, String version, String map, String joined, String anouncement, String id) {
        this.title = title;
        this.time = time;
        this.winPrize = winPrize;
        this.perKill = perKill;
        this.entryFee = entryFee;
        this.type = type;
        this.version = version;
        this.map = map;
        this.joined = joined;
        this.anouncement = anouncement;
        this.id = id;
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

    public String getAnouncement() {
        return anouncement;
    }

    public String getId() {
        return id;
    }
}
