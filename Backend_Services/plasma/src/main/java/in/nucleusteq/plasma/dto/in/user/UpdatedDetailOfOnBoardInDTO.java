package in.nucleusteq.plasma.dto.in.user;

import in.nucleusteq.plasma.enums.Currency;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
public class UpdatedDetailOfOnBoardInDTO {
    private int id;
    private int ctc;
    private int bonus;
    private int visaExpense;
    private int otherExpense;
    private Currency ctcCurrency;
    private Currency bonusCurrency;
    private Currency visaExpenseCurrency;
    private Currency otherExpenseCurrency;
    private String recruiterManager;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCtc() {
        return ctc;
    }
    public void setCtc(int ctc) {
        this.ctc = ctc;
    }
    public int getBonus() {
        return bonus;
    }
    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
    public int getVisaExpense() {
        return visaExpense;
    }
    public void setVisaExpense(int visaExpense) {
        this.visaExpense = visaExpense;
    }
    public int getOtherExpense() {
        return otherExpense;
    }
    public void setOtherExpense(int otherExpense) {
        this.otherExpense = otherExpense;
    }
    public Currency getCtcCurrency() {
        return ctcCurrency;
    }
    public void setCtcCurrency(Currency ctcCurrency) {
        this.ctcCurrency = ctcCurrency;
    }
    public Currency getBonusCurrency() {
        return bonusCurrency;
    }
    public void setBonusCurrency(Currency bonusCurrency) {
        this.bonusCurrency = bonusCurrency;
    }
    public Currency getVisaExpenseCurrency() {
        return visaExpenseCurrency;
    }
    public void setVisaExpenseCurrency(Currency visaExpenseCurrency) {
        this.visaExpenseCurrency = visaExpenseCurrency;
    }
    public Currency getOtherExpenseCurrency() {
        return otherExpenseCurrency;
    }
    public void setOtherExpenseCurrency(Currency otherExpenseCurrency) {
        this.otherExpenseCurrency = otherExpenseCurrency;
    }
    public String getRecruiterManager() {
        return recruiterManager;
    }
    public void setRecruiterManager(String recruiterManager) {
        this.recruiterManager = recruiterManager;
    }
    
    
    
}
