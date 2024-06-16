package la.archimedes.jse.tool;

import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 月供计计算器
 *
 */
public class MonthlyPaymentCalcu {

    //总贷款
    BigDecimal totalLoan = new BigDecimal("2630000");
    //年化利率
    BigDecimal annualizedInterestRate = new BigDecimal("0.045");
    //月利率
    BigDecimal monthlyInterestRate = annualizedInterestRate.divide(new BigDecimal("12"));
    //原始月供期数
    int totalMonth = 300;


    /**
     * 计算新的月供（等额本息）
     */
    @Test
    public void testNewMonthlyPayment(){
        //设置变量值
        //提前还贷的金额
        BigDecimal prepaymentAmt = new BigDecimal("100000");
        //已经还款期数
        int numberOfRepayment = 10;
        //原始剩余本金
        totalLoan = new BigDecimal("2581630.47");

        //提前还贷后的剩余本金
        totalLoan = totalLoan.subtract(prepaymentAmt);
        System.out.println("剩余本金："+totalLoan);
        totalMonth -= numberOfRepayment;
        System.out.println("剩余期数："+totalMonth);

        System.out.println("月利率："+monthlyInterestRate);
        BigDecimal ratePow = monthlyInterestRate.add(new BigDecimal("1")).pow(totalMonth);
        BigDecimal s1 = totalLoan.multiply(monthlyInterestRate).multiply(ratePow);
        BigDecimal s2 = ratePow.subtract(new BigDecimal("1"));
        BigDecimal monthlyPayment = s1.divide(s2,2,RoundingMode.UP);
        System.out.println("新月供是："+monthlyPayment);
    }
    
    
    
}
