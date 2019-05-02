package techno.k9.onesignalv2point134.Beans;

public class BeanImage {


    public static String friendName="";

    String url1,url2,url3,url4,url5;

    public BeanImage(String url1, String url2, String url3, String url4, String url5) {
        this.url1 = url1;
        this.url2 = url2;
        this.url3 = url3;
        this.url4 = url4;
        this.url5 = url5;
    }

    public BeanImage() {
    }

    public String getUrl1() {
        return url1;
    }

    public String getUrl2() {
        return url2;
    }

    public String getUrl3() {
        return url3;
    }

    public String getUrl4() {
        return url4;
    }

    public String getUrl5() {
        return url5;
    }
}
