package com.example.backendcreditas.model.loan;

public enum LoanType {

    PERSONAL("personal",4),
    COLLATERIZED("collaterized",3),
    PAYROLL("payroll",2);


    private String loanName;
    private int loanTax;

    LoanType(String loanName,int loanTax) {
        this.loanName = loanName;
        this.loanTax = loanTax;
    }

    public int getLoanTax() {
        return loanTax;
    }

    public String getLoanName() {
        return loanName;
    }


}
