package budgetflow.command;

import budgetflow.exception.FinanceException;
import budgetflow.expense.ExpenseList;
import budgetflow.income.Income;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//@@ author Yikbing
public class UpdateIncomeCommandTest {

    //@@ author Yikbing
    @Test
    public void updateIncome_validInputCategory_updatesIncome() throws FinanceException {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command c1 = new AddIncomeCommand("add category/Salary amt/2500.00 d/15-03-2025");
        c1.execute(incomes, expenseList);
        Command c2 = new UpdateIncomeCommand("update-income 1 category/Salary1");
        c2.execute(incomes, expenseList);
        String expectedOutput = "Income updated: Salary1, Amount: $2500.00, Date: 15-03-2025";
        assertEquals(expectedOutput, c2.getOutputMessage());

    }

    //@@ author Yikbing
    @Test
    public void updateIncome_validInputAmt_updatesIncome() throws FinanceException {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command c1 = new AddIncomeCommand("add category/Salary amt/2500.00 d/15-03-2025");
        c1.execute(incomes, expenseList);
        Command c2 = new UpdateIncomeCommand("update-income 1 amt/5000");
        c2.execute(incomes, expenseList);
        String expectedOutput = "Income updated: Salary, Amount: $5000.00, Date: 15-03-2025";
        assertEquals(expectedOutput, c2.getOutputMessage());

    }

    //@@ author Yikbing
    @Test
    public void updateIncome_validInputDate_updatesIncome() throws FinanceException {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command c1 = new AddIncomeCommand("add category/Salary amt/2500.00 d/15-03-2025");
        c1.execute(incomes, expenseList);
        Command c2 = new UpdateIncomeCommand("update-income 1 d/28-02-2025");
        c2.execute(incomes, expenseList);
        String expectedOutput = "Income updated: Salary, Amount: $2500.00, Date: 28-02-2025";
        assertEquals(expectedOutput, c2.getOutputMessage());

    }

    //@@ author Yikbing
    @Test
    public void updateIncome_allValidInputs_updatesIncome() throws FinanceException {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command c1 = new AddIncomeCommand("add category/Salary amt/2500.00 d/15-03-2025");
        c1.execute(incomes, expenseList);
        Command c2 = new UpdateIncomeCommand("update-income 1 category/Salary1 amt/5000 d/28-02-2025");
        c2.execute(incomes, expenseList);
        String expectedOutput = "Income updated: Salary1, Amount: $5000.00, Date: 28-02-2025";
        assertEquals(expectedOutput, c2.getOutputMessage());

    }

    //@@ author Yikbing
    @Test
    public void updateIncome_inValidInputCategory_noChangeToIncome() throws FinanceException {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command c1 = new AddIncomeCommand("add category/Salary amt/2500.00 d/15-03-2025");
        c1.execute(incomes, expenseList);
        Command c2 = new UpdateIncomeCommand("update-income 1 category/");
        c2.execute(incomes, expenseList);
        String expectedOutput = "Income updated: Salary, Amount: $2500.00, Date: 15-03-2025";
        assertEquals(expectedOutput, c2.getOutputMessage());


    }

    //@@ author Yikbing
    @Test
    public void updateIncome_inValidInputAmt_throwsException() throws FinanceException {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command c1 = new AddIncomeCommand("add category/Salary amt/2500.00 d/15-03-2025");
        c1.execute(incomes, expenseList);
        Command c2 = new UpdateIncomeCommand("update-income 1 amt/invalidamount");

        try{
            c2.execute(incomes, expenseList);
            fail();
        } catch (FinanceException e) {
            String expectedError = "Error: Invalid amount format.";
            assertEquals(expectedError, e.getMessage());
        }

    }

    //@@ author Yikbing
    @Test
    public void updateIncome_inValidIntegerAmt_throwsException() throws FinanceException {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command c1 = new AddIncomeCommand("add category/Salary amt/2500.00 d/15-03-2025");
        c1.execute(incomes, expenseList);
        Command c2 = new UpdateIncomeCommand("update-income 1 amt/12345678");

        try{
            c2.execute(incomes, expenseList);
            fail();
        } catch (FinanceException e) {
            String expectedError = "ERROR: Integer part exceeds 7 digits.";
            assertEquals(expectedError, e.getMessage());
        }

    }

    //@@ author Yikbing
    @Test
    public void updateIncome_inValidDecimalAmt_throwsException() throws FinanceException {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command c1 = new AddIncomeCommand("add category/Salary amt/2500.00 d/15-03-2025");
        c1.execute(incomes, expenseList);
        Command c2 = new UpdateIncomeCommand("update-income 1 amt/25.123");

        try{
            c2.execute(incomes, expenseList);
            fail();
        } catch (FinanceException e) {
            String expectedError = "ERROR: Decimal part exceeds 2 digits.";
            assertEquals(expectedError, e.getMessage());
        }

    }

    //@@ author Yikbing
    @Test
    public void updateIncome_inValidInputDate_throwsException() throws FinanceException {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command c1 = new AddIncomeCommand("add category/Salary amt/2500.00 d/15-03-2025");
        c1.execute(incomes, expenseList);
        Command c2 = new UpdateIncomeCommand("update-income 1 d/99-99-9999");

        try{
            c2.execute(incomes, expenseList);
            fail();
        } catch (FinanceException e) {
            String expectedError = "Error: Date is not a valid date";
            assertEquals(expectedError, e.getMessage());
        }

    }

    //@@ author Yikbing
    @Test
    public void updateIncome_inValidDateFormat_throwsException() throws FinanceException {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command c1 = new AddIncomeCommand("add category/Salary amt/2500.00 d/15-03-2025");
        c1.execute(incomes, expenseList);
        Command c2 = new UpdateIncomeCommand("update-income 1 d/Mar12");

        try{
            c2.execute(incomes, expenseList);
            fail();
        } catch (FinanceException e) {
            String expectedError = "Error: Invalid date format. Usage: DD-MM-YYYY";
            assertEquals(expectedError, e.getMessage());
        }

    }

    //@@ author Yikbing
    @Test
    public void updateIncome_emptyList_throwsException(){
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        Command c = new UpdateIncomeCommand("update-income 1 amt/200");

        try{
            c.execute(incomes, expenseList);
            fail();
        } catch (FinanceException e) {
            String expectedError = "Error: No income entries exist to update.";
            assertEquals(expectedError, e.getMessage());
        }

    }

}
