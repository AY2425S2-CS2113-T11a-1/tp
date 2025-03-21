package budgetflow.command;

import budgetflow.expense.ExpenseList;
import budgetflow.income.Income;
import budgetflow.exception.FinanceException;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ListIncomeCommandTest {
    private static List<Income> get3Incomes() {
        List<Income> incomes = new ArrayList<>();
        incomes.add(new Income("Part-timeJob", 300.00, "12-06-2025"));
        incomes.add(new Income("freelance", 100.00, "29-05-2025"));
        incomes.add(new Income("fulltime-job", 5000.00, "01-01-2025"));
        return incomes;
    }

    @Test
    void listIncomeTest_normalTest() throws FinanceException {
        List<Income> incomes = get3Incomes();
        ExpenseList expenseList = new ExpenseList();
        Command c = new ListIncomeCommand();
        c.execute(incomes, expenseList);
        String expectedOutput = "Income Log:" + System.lineSeparator() +
                "Part-timeJob | $300.00 | 12-06-2025" + System.lineSeparator() +
                "freelance | $100.00 | 29-05-2025" + System.lineSeparator() +
                "fulltime-job | $5000.00 | 01-01-2025" + System.lineSeparator() +
                "Total Income: $5400.00" + System.lineSeparator();
        assertEquals(expectedOutput, c.getOutputMessage());
    }

    @Test
    void listIncomeTest_emptyListTest() throws FinanceException {
        List<Income> incomes = new ArrayList<>();
        ExpenseList expenseList = new ExpenseList();
        Command c = new ListIncomeCommand();
        c.execute(incomes, expenseList);
        String expectedOutput = "No incomes have been added yet." + System.lineSeparator();
        assertEquals(expectedOutput, c.getOutputMessage());
    }
}
