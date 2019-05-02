package techno.k9.onesignalv2point134.Beans;

public class BeanWithDraw {
    String paytmNumber;
    String amountRS;
    String username;
    String id;

    public BeanWithDraw(String paytmNumber, String amountRS, String username, String id) {
        this.paytmNumber = paytmNumber;
        this.amountRS = amountRS;
        this.username = username;
        this.id = id;
    }

    public BeanWithDraw() {
    }

    public String getPaytmNumber() {
        return paytmNumber;
    }

    public String getAmountRS() {
        return amountRS;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }
}
