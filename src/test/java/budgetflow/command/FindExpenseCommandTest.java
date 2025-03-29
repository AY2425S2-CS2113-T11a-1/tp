package budgetflow.command;

import budgetflow.exception.FinanceException;
import budgetflow.exception.UnfoundExpenseException;
import budgetflow.expense.Expense;
import budgetflow.expense.ExpenseList;
import budgetflow.income.Income;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//@@author QuyDatNguyen
class FindExpenseCommandTest {

    private static ExpenseList getListWith5Expenses() {
        ExpenseList expenseList = new ExpenseList();
        expenseList.add(new Expense("food", "Lunch", 12.50, "13-03-2025"));
        expenseList.add(new Expense("transport", "Transport", 3.20, "12-03-2025"));
        expenseList.add(new Expense("food", "LateLunch", 13.50, "14-03-2025"));
        expenseList.add(new Expense("food", "Groceries", 25.0, "11-03-2025"));
        expenseList.add(new Expense("food", "ExpensiveLunch", 30.00, "15-03-2025"));
        return expenseList;
    }

    private static ExpenseList getListWith3Expenses() {
        ExpenseList expenseList = new ExpenseList();
        expenseList.add(new Expense("food", "Lunch", 12.50, "13-03-2025"));
        expenseList.add(new Expense("transport", "Transport", 3.20, "12-03-2025"));
        expenseList.add(new Expense("food", "Groceries", 25.0, "11-03-2025"));
        return expenseList;
    }

    @Test
    void findExpense_found1ExpenseDescTest() throws FinanceException {
        List<Income> incomes = new ArrayList<>();
        ExpenseList expenseList = getListWith3Expenses();
        Command c = new FindExpenseCommand("find-expense /desc Lunch");
        c.execute(incomes, expenseList);
        String expectedOutput = "Here are all matching expenses:" + System.lineSeparator()
                + "food | Lunch | $12.50 | 13-03-2025" + System.lineSeparator();
        assertEquals(expectedOutput, c.getOutputMessage());
    }

    @Test
    void findExpense_foundMultipleExpenseDescTest() throws FinanceException {
        List<Income> incomes = new ArrayList<>();
        ExpenseList expenseList = getListWith5Expenses();
        Command c = new FindExpenseCommand("find-expense /desc Lunch");
        c.execute(incomes, expenseList);
        String expectedOutput = "Here are all matching expenses:" + System.lineSeparator()
                + "food | Lunch | $12.50 | 13-03-2025" + System.lineSeparator()
                + "food | LateLunch | $13.50 | 14-03-2025" + System.lineSeparator()
                + "food | ExpensiveLunch | $30.00 | 15-03-2025" + System.lineSeparator();
        assertEquals(expectedOutput, c.getOutputMessage());
    }

    @Test

    void findExpense_partialMatchDesc() throws FinanceException {
        List<Income> incomes = new ArrayList<>();
        ExpenseList expenseList = getListWith3Expenses();
        Command c = new FindExpenseCommand("find-expense /desc Groc");
        c.execute(incomes, expenseList);
        String expectedOutput = "Here are all matching expenses:" + System.lineSeparator() +
                "food | Groceries | $25.00 | 11-03-2025" + System.lineSeparator();
        assertEquals(expectedOutput, c.getOutputMessage());
    }

    @Test
    void findExpense_foundNoExpenseDescTest() throws UnfoundExpenseException {
        ExpenseList expenseList = new ExpenseList();
        List<Income> incomes = new ArrayList<>();
        try {
            Command c = new FindExpenseCommand("find-expense /desc IAmDummy");
            c.execute(incomes, expenseList);
            fail();
        } catch (FinanceException e) {
            String expectedError = "Sorry, I cannot find any expenses matching your keyword: IAmDummy";
            assertEquals(expectedError, e.getMessage());
        }
    }

    @Test
    void findExpense_foundMatchingCategory() throws FinanceException {
        List<Income> incomes = new ArrayList<>();
        ExpenseList expenseList = getListWith3Expenses();
        Command c = new FindExpenseCommand("find-expense /category food");
        c.execute(incomes, expenseList);
        String expectedOutput = "Here are all matching expenses:" + System.lineSeparator()
                + "food | Lunch | $12.50 | 13-03-2025" + System.lineSeparator()
                + "food | Groceries | $25.00 | 11-03-2025" + System.lineSeparator();
        assertEquals(expectedOutput, c.getOutputMessage());
    }

    @Test
    void findExpense_noMatchingCategory() {
        ExpenseList expenseList = getListWith3Expenses();
        List<Income> incomes = new ArrayList<>();
        try {
            Command c = new FindExpenseCommand("find-expense /category foo");
            c.execute(incomes, expenseList);
        } catch (FinanceException e) {
            String expectedError = "Sorry, I cannot find any expenses matching your keyword: foo";
            assertEquals(expectedError, e.getMessage());
        }
    }

    @Test
    void findExpense_foundMatchingAmount() throws FinanceException {
        List<Income> incomes = new ArrayList<>();
        ExpenseList expenseList = getListWith3Expenses();
        Command c = new FindExpenseCommand("find-expense /amt 12.5");
        c.execute(incomes, expenseList);
        String expectedOutput = "Here are all matching expenses:" + System.lineSeparator()
                + "food | Lunch | $12.50 | 13-03-2025" + System.lineSeparator();
        assertEquals(expectedOutput, c.getOutputMessage());
    }

    @Test
    void findExpense_noMatchingAmount() {
        ExpenseList expenseList = getListWith3Expenses();
        List<Income> incomes = new ArrayList<>();
        try {
            Command c = new FindExpenseCommand("find-expense /amt 2.99");
            c.execute(incomes, expenseList);
        } catch (FinanceException e) {
            String expectedError = "Sorry, I cannot find any expenses matching your keyword: 2.99";
            assertEquals(expectedError, e.getMessage());
        }
    }

    @Test
    void findExpense_invalidAmount() {
        ExpenseList expenseList = getListWith3Expenses();
        List<Income> incomes = new ArrayList<>();
        try {
            Command c = new FindExpenseCommand("find-expense /amt foo");
            c.execute(incomes, expenseList);
        } catch (FinanceException e) {
            String expectedError = "Please enter correct keyword format for tag /amt";
            assertEquals(expectedError, e.getMessage());
        }
    }

    @Test
    void findExpense_foundMatchingDate() throws FinanceException {
        List<Income> incomes = new ArrayList<>();
        ExpenseList expenseList = getListWith3Expenses();
        Command c = new FindExpenseCommand("find-expense /d 13-03-2025");
        c.execute(incomes, expenseList);
        String expectedOutput = "Here are all matching expenses:" + System.lineSeparator()
                + "food | Lunch | $12.50 | 13-03-2025" + System.lineSeparator();
        assertEquals(expectedOutput, c.getOutputMessage());
    }

    @Test
    void findExpense_noMatchingDate() {
        List<Income> incomes = new ArrayList<>();
        ExpenseList expenseList = getListWith3Expenses();
        try {
            Command c = new FindExpenseCommand("find-expense /d 01-01-1000");
            c.execute(incomes, expenseList);
        } catch (FinanceException e) {
            String expectedError = "Sorry, I cannot find any expenses matching your keyword: 01-01-1000";
            assertEquals(expectedError, e.getMessage());
        }
    }

    @Test
    void findExpense_invalidDate() {
        ExpenseList expenseList = getListWith3Expenses();
        List<Income> incomes = new ArrayList<>();
        try {
            Command c = new FindExpenseCommand("find-expense /d foo");
            c.execute(incomes, expenseList);
        } catch (FinanceException e) {
            String expectedError = "Please enter correct keyword format for tag /d";
            assertEquals(expectedError, e.getMessage());
        }
    }

    @Test
    void findExpense_matchingAmountRange() throws FinanceException {
        List<Income> incomes = new ArrayList<>();
        ExpenseList expenseList = getListWith3Expenses();
        Command c = new FindExpenseCommand("find-expense /amtrange 10.0 30.0");
        c.execute(incomes, expenseList);
        String expectedOutput = "Here are all matching expenses:" + System.lineSeparator()
                + "food | Lunch | $12.50 | 13-03-2025" + System.lineSeparator()
                + "food | Groceries | $25.00 | 11-03-2025" + System.lineSeparator();
        assertEquals(expectedOutput, c.getOutputMessage());
    }

    @Test
    void findExpense_noMatchingAmountRange() {
        List<Income> incomes = new ArrayList<>();
        ExpenseList expenseList = getListWith3Expenses();
        try {
            Command c = new FindExpenseCommand("find-expense /amtrange 0.00 1.00");
            c.execute(incomes, expenseList);
        } catch (FinanceException e) {
            String expectedError = "Sorry, I cannot find any expenses matching your keyword: 0.00 1.00";
            assertEquals(expectedError, e.getMessage());
        }
    }

    @Test
    void findExpense_invalidAmountRange() {
        ExpenseList expenseList = getListWith3Expenses();
        List<Income> incomes = new ArrayList<>();
        try {
            Command c = new FindExpenseCommand("find-expense /amtrange foo");
            c.execute(incomes, expenseList);
        } catch (FinanceException e) {
            String expectedError = "Please enter correct keyword format for tag /amtrange";
            assertEquals(expectedError, e.getMessage());
        }
    }

    @Test
    void findExpense_matchingDateRange() throws FinanceException {
        List<Income> incomes = new ArrayList<>();
        ExpenseList expenseList = getListWith3Expenses();
        Command c = new FindExpenseCommand("find-expense /drange 11-03-2025 23-03-2025");
        c.execute(incomes, expenseList);
        String expectedOutput = "Here are all matching expenses:" + System.lineSeparator()
                + "food | Lunch | $12.50 | 13-03-2025" + System.lineSeparator()
                + "transport | Transport | $3.20 | 12-03-2025" + System.lineSeparator()
                + "food | Groceries | $25.00 | 11-03-2025" + System.lineSeparator();
        assertEquals(expectedOutput, c.getOutputMessage());
    }

    @Test
    void findExpense_noMatchingDateRange() {
        List<Income> incomes = new ArrayList<>();
        ExpenseList expenseList = getListWith3Expenses();
        try {
            Command c = new FindExpenseCommand("find-expense /drange 01-01-1000 02-02-2000");
            c.execute(incomes, expenseList);
        } catch (FinanceException e) {
            String expectedError = "Sorry, I cannot find any expenses matching your keyword: 01-01-1000 02-02-2000";
            assertEquals(expectedError, e.getMessage());
        }
    }

    @Test
    void findExpense_invalidDateRange() {
        ExpenseList expenseList = getListWith3Expenses();
        List<Income> incomes = new ArrayList<>();
        try {
            Command c = new FindExpenseCommand("find-expense /drange foo");
            c.execute(incomes, expenseList);
        } catch (FinanceException e) {
            String expectedError = "Please enter correct keyword format for tag /drange";
            assertEquals(expectedError, e.getMessage());
        }
    }

    @Test
    void findExpense_noKeywordsTest() {
        ExpenseList expenseList = getListWith3Expenses();
        List<Income> incomes = new ArrayList<>();
        try {
            Command c = new FindExpenseCommand("find-expense");
            c.execute(incomes, expenseList);
        } catch (FinanceException e) {
            String expectedError = "Error: Missing keyword";
            assertEquals(expectedError, e.getMessage());
        }
    }
}
