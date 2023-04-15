package API.Entities;

/**
 * @author Max Day
 * Created At: 2023/04/15
 */

public class User {

    private String uName = "", encyptedPass = "", fName = "", sName = "", dOb = "";

    //TODO im debating wether to put the encrypted password here like fr idk if its a good idea or not my future self will tell me if its a dumb idea or not lmao
    public User(String uname, String encryptedPassword, String fname, String sname, String doB) {
        uName = uname;
        encyptedPass = encryptedPassword;
        fName = fname;
        sName = sname;
        dOb = doB;
    }

    public String getuName() {
        return uName;
    }

    public String getfName() {
        return fName;
    }

    public String getsName() {
        return sName;
    }

    public String getdOb() {
        return dOb;
    }

    public String getEncyptedPass() {
        return encyptedPass;
    }

    public String getTablePath() {
        return String.format("src/tables/%sTable.json", getuName());
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public void setdOb(String dOb) {
        this.dOb = dOb;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public void setEncyptedPass(String encyptedPass) {
        this.encyptedPass = encyptedPass;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }
}
