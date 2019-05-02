package techno.k9.onesignalv2point134.Beans;

public class BeanIdPass {

    String title;
    String time;
    String id;
    String pass;



    public BeanIdPass() {
    }

    public BeanIdPass(String title, String time, String id, String pass) {
        this.title = title;
        this.time = time;
        this.id = id;
        this.pass = pass;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getId() {
        return id;
    }

    public String getPass() {
        return pass;
    }
}
