package org.example.bank2;

public class Account {

    //客户id
    private String accountno;
    //客户名称
    private String accountname;
    //美元金额
    private Double USD_balance;
    //人民币金额
    private Double RMB_balance;

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname;
    }

    public Double getUSD_balance() {
        return USD_balance;
    }

    public void setUSD_balance(Double USD_balance) {
        this.USD_balance = USD_balance;
    }

    public Double getRMB_balance() {
        return RMB_balance;
    }

    public void setRMB_balance(Double RMB_balance) {
        this.RMB_balance = RMB_balance;
    }
}

