package techno.k9.onesignalv2point134.Beans;

public class BeanRegister {

    private String username;
    private String emailAddress;
    private String mobileNumber;
    private String password;
    private String referral;

    public BeanRegister() {
    }

    public BeanRegister(String username, String emailAddress, String mobileNumber, String password, String referral) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.referral = referral;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getReferral() {
        return referral;
    }
}
