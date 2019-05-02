package techno.k9.onesignalv2point134.Beans;

public class BeanTHistory {

    String username;
    String paytmNumber;
    String amountToPay;
    String date;
    String status;
    String id;

    public BeanTHistory() {
    }

    public BeanTHistory(String username, String paytmNumber, String amountToPay, String date, String status, String id) {
        this.username = username;
        this.paytmNumber = paytmNumber;
        this.amountToPay = amountToPay;
        this.date = date;
        this.status = status;
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public String getPaytmNumber() {
        return paytmNumber;
    }

    public String getAmountToPay() {
        return amountToPay;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }
}
