package budgetflow.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import budgetflow.expense.ExpenseList;
import budgetflow.income.Income;
import budgetflow.exception.InvalidKeywordException;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class FilterIncomeByCategoryCommandTest {

    @Test
    void category_valid_returnsMatching() throws Exception {
        List<Income> incomes = new ArrayList<>();
        incomes.add(new Income("Salary", 2500.00, "15-03-2025"));
        incomes.add(new Income("Bonus", 500.00, "20-03-2025"));
        incomes.add(new Income("Salary", 3000.00, "10-04-2025"));
        ExpenseList expenseList = new ExpenseList();

        Command command = new FilterIncomeByCategoryCommand("filter-income category/Salary");
        command.execute(incomes, expenseList);

        String expectedOutput = String.format("Filtered Incomes by Category: %s%n%n", "Salary")
                + String.format("%-20s | %-10s | %-15s%n", "Category", "Amount", "Date")
                + String.format("%-20s-+-%-10s-+-%-15s%n", "-".repeat(20), "-".repeat(10), "-".repeat(15))
                + String.format("%-20s | $%-9.2f | %-15s%n", "Salary", 2500.00, "15-03-2025")
                + String.format("%-20s | $%-9.2f | %-15s%n", "Salary", 3000.00, "10-04-2025");

        assertEquals(expectedOutput, command.getOutputMessage());
    }

    @Test
    void category_noMatch_returnsNone() throws Exception {
        List<Income> incomes = new ArrayList<>();
        incomes.add(new Income("Salary", 2500.00, "15-03-2025"));
        ExpenseList expenseList = new ExpenseList();

        Command command = new FilterIncomeByCategoryCommand("filter-income category/Bonus");
        command.execute(incomes, expenseList);

        String expectedOutput = String.format("Filtered Incomes by Category: %s%n%n", "Bonus")
                + String.format("%-20s | %-10s | %-15s%n", "Category", "Amount", "Date")
                + String.format("%-20s-+-%-10s-+-%-15s%n", "-".repeat(20), "-".repeat(10), "-".repeat(15))
                + "No incomes found under the specified category.";

        assertEquals(expectedOutput, command.getOutputMessage());
    }

    @Test
    void category_missing_throwsException() {
        List<Income> incomes = new ArrayList<>();
        ExpenseList expenseList = new ExpenseList();

        Command command = new FilterIncomeByCategoryCommand("filter-income category/");
        InvalidKeywordException exception = assertThrows(InvalidKeywordException.class,
                () -> command.execute(incomes, expenseList));
        String expectedMessage = "Invalid command. Correct format: Usage: filter-income category/<category>\n"
                + "Example: filter-income category/Salary";
        assertEquals(expectedMessage, exception.getMessage());
    }

    //@@author thienkimtranhoang
    @Test
    void category_multipleMatches_returnsAllMatching() throws Exception {
        List<Income> incomes = new ArrayList<>();
        incomes.add(new Income("Salary", 2500.00, "15-03-2025"));
        incomes.add(new Income("Salary", 3000.00, "10-04-2025"));
        incomes.add(new Income("Bonus", 500.00, "20-03-2025"));
        incomes.add(new Income("Salary", 1500.00, "01-05-2025"));
        ExpenseList expenseList = new ExpenseList();

        // Filter for category 'Salary', multiple incomes match
        Command command = new FilterIncomeByCategoryCommand("filter-income category/Salary");
        command.execute(incomes, expenseList);

        String expectedOutput = String.format("Filtered Incomes by Category: %s%n%n", "Salary")
                + String.format("%-20s | %-10s | %-15s%n", "Category", "Amount", "Date")
                + String.format("%-20s-+-%-10s-+-%-15s%n", "-".repeat(20), "-".repeat(10), "-".repeat(15))
                + String.format("%-20s | $%-9.2f | %-15s%n", "Salary", 2500.00, "15-03-2025")
                + String.format("%-20s | $%-9.2f | %-15s%n", "Salary", 3000.00, "10-04-2025")
                + String.format("%-20s | $%-9.2f | %-15s%n", "Salary", 1500.00, "01-05-2025");

        assertEquals(expectedOutput, command.getOutputMessage());
    }

    //@@author thienkimtranhoang
    @Test
    void category_caseInsensitive_returnsMatching() throws Exception {
        List<Income> incomes = new ArrayList<>();
        incomes.add(new Income("salary", 2500.00, "15-03-2025"));
        incomes.add(new Income("Bonus", 500.00, "20-03-2025"));
        incomes.add(new Income("Salary", 3000.00, "10-04-2025"));
        ExpenseList expenseList = new ExpenseList();

        // Filter with different case
        Command command = new FilterIncomeByCategoryCommand("filter-income category/sAlArY");
        command.execute(incomes, expenseList);

        String expectedOutput = String.format("Filtered Incomes by Category: %s%n%n", "sAlArY")
                + String.format("%-20s | %-10s | %-15s%n", "Category", "Amount", "Date")
                + String.format("%-20s-+-%-10s-+-%-15s%n", "-".repeat(20), "-".repeat(10), "-".repeat(15))
                + String.format("%-20s | $%-9.2f | %-15s%n", "salary", 2500.00, "15-03-2025")
                + String.format("%-20s | $%-9.2f | %-15s%n", "Salary", 3000.00, "10-04-2025");

        assertEquals(expectedOutput, command.getOutputMessage());
    }

    //@@author thienkimtranhoang
    @Test
    void category_emptyString_throwsException() {
        List<Income> incomes = new ArrayList<>();
        ExpenseList expenseList = new ExpenseList();

        // Empty category string
        Command command = new FilterIncomeByCategoryCommand("filter-income category/");
        InvalidKeywordException exception = assertThrows(InvalidKeywordException.class,
                () -> command.execute(incomes, expenseList));
        String expectedMessage = "Invalid command. Correct format: Usage: filter-income category/<category>\n"
                + "Example: filter-income category/Salary";
        assertEquals(expectedMessage, exception.getMessage());
    }
}
