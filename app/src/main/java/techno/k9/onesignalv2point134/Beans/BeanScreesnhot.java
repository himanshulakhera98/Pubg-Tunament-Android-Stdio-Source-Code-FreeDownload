package techno.k9.onesignalv2point134.Beans;

public class BeanScreesnhot {

    String username;
    String uri;
    String id;

    public BeanScreesnhot(String username, String uri,String id) {
        this.username = username;
        this.uri = uri;
        this.id=id;
    }

    public BeanScreesnhot() {
    }

    public String getUsername() {
        return username;
    }

    public String getUri() {
        return uri;
    }


    public String getId() {
        return id;
    }
}
