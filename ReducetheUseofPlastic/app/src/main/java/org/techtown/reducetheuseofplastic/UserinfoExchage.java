package org.techtown.reducetheuseofplastic;

public class UserinfoExchage {
    public String useremail,bank;
    public int point,account;
    public boolean check;

    public UserinfoExchage(){}
    public UserinfoExchage(String useremail,String bank,int point,int account,boolean check){
        this.useremail=useremail;
        this.bank=bank;
        this.point=point;
        this.account=account;
        this.check=check;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }



}
