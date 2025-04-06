package budgetflow.command;

import budgetflow.exception.FinanceException;
import budgetflow.expense.ExpenseList;
import budgetflow.income.Income;

import java.util.List;

//@@ author Yikbing
/**
 * Represents the Help Command which provides detailed instructions on how to use Budgetflow.
 * <p>
 * The help menu is structured into sections and displays the command syntax along with examples.
 * It is designed to be clear, comprehensive, and easy to read.
 *
 * @author IgoyAI
 * @author Yikbing
 */
public class HelpCommand extends Command {

    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String RESET = "\u001B[0m";

    public HelpCommand() {
        super();
        this.commandType = CommandType.READ;
    }

    //@@ author Yikbing
    @Override
    public void execute(List<Income> incomes, ExpenseList expenseList) throws FinanceException {
        StringBuilder helpMessage = new StringBuilder();
        helpMessage.append("To add an Income: add category/<CATEGORY> amt/<AMOUNT> d/<DD-MM-YYYY>\n" + CYAN);
        helpMessage.append("To add an Expense: log-expense category/<CATEGORY> desc/<DESCRIPTION> amt/<AMOUNT>" +
                " d/<DD-MM-YYYY>\n" + RESET);
        helpMessage.append("To View all expenses: view-all-expense\n" + CYAN);
        helpMessage.append("To view all Income: list income\n" + RESET);
        helpMessage.append("To delete an Expense: delete-expense <INDEX>>\n" + CYAN);
        helpMessage.append("To delete an Income: delete-income <CATEGORY>\n" + RESET);
        helpMessage.append("To find an Expense:\n" + CYAN +
                "find-expense /desc <DESCRIPTION>\n" + YELLOW +
                "OR find-expense /d <DD-MM-YYYY>\n" + CYAN +
                "OR find-expense /amt <AMOUNT>\n" + YELLOW +
                "OR find-expense /category <CATEGORY>\n" + CYAN +
                "OR find-expense /amtrange <AMOUNT 1> <AMOUNT 2>\n" + YELLOW +
                "OR find-expense /drange <DATE 1> <DATE 2>\n" + CYAN);
        helpMessage.append("To compare expenses from 2 months: compare <MM-YYYY> <MM-YYYY>\n" + RESET);
        helpMessage.append("To Update an expense: update-expense <INDEX> category/<CATEGORY> OR amt/<AMOUNT> OR " +
                "desc/<DESCRIPTION> d/<DD-MM-YYYY>\n" + CYAN);
        helpMessage.append("To Update an Income :\n" + RESET +
                "update-income category/<CATEGORY>\n" + YELLOW +
                "OR update-income amt/<AMOUNT>\n" + RESET +
                "OR update-income d/<DD-MM-YYYY>\n" + YELLOW +
                "OR any other combination of the three\n" + RESET);
        helpMessage.append("To filter income by date: filter-income date from/<DD-MM-YYYY> to/<DD-MM-YYYY>\n" + CYAN);
        helpMessage.append("To filter income by amount: filter-income amount from/<AMOUNT 1> to/<AMOUNT 2>\n" + RESET);
        helpMessage.append("To filter income by category: filter-income category/<CATEGORY>\n" + CYAN);
        helpMessage.append("To exit the application: exit" + RESET).append(System.lineSeparator());
        String border = "=".repeat(60);

        helpMessage.append(border).append(System.lineSeparator());
        helpMessage.append(centerText("HELP MENU", 60)).append(System.lineSeparator());
        helpMessage.append(border).append(System.lineSeparator());
        helpMessage.append(System.lineSeparator());

        // 1. Add Income
        helpMessage.append("1. Add Income:").append(System.lineSeparator());
        helpMessage.append("   Syntax : add category/[CATEGORY] " +
                "amt/[AMOUNT] d/[DD-MM-YYYY]").append(System.lineSeparator());
        helpMessage.append("   Example: add category/Salary " +
                "amt/5000 d/15-03-2023").append(System.lineSeparator());
        helpMessage.append(System.lineSeparator());

        // 2. Add Expense
        helpMessage.append("2. Add Expense:").append(System.lineSeparator());
        helpMessage.append("   Syntax : log-expense category/[CATEGORY] " +
                "desc/[DESCRIPTION] amt/[AMOUNT] d/[DD-MM-YYYY]").append(System.lineSeparator());
        helpMessage.append("   Example: log-expense category/Food " +
                "desc/Lunch amt/15 d/20-03-2023").append(System.lineSeparator());
        helpMessage.append(System.lineSeparator());

        // 3. View All Expenses
        helpMessage.append("3. View All Expenses:").append(System.lineSeparator());
        helpMessage.append("   Syntax : view-all-expense").append(System.lineSeparator());
        helpMessage.append("   Example: view-all-expense").append(System.lineSeparator());
        helpMessage.append(System.lineSeparator());

        // 4. View All Income
        helpMessage.append("4. View All Income:").append(System.lineSeparator());
        helpMessage.append("   Syntax : list income").append(System.lineSeparator());
        helpMessage.append("   Example: list income").append(System.lineSeparator());
        helpMessage.append(System.lineSeparator());

        // 5. Delete Expense
        helpMessage.append("5. Delete Expense:").append(System.lineSeparator());
        helpMessage.append("   Syntax : delete-expense [DESCRIPTION]").append(System.lineSeparator());
        helpMessage.append("   Example: delete-expense Lunch").append(System.lineSeparator());
        helpMessage.append(System.lineSeparator());

        // 6. Delete Income
        helpMessage.append("6. Delete Income:").append(System.lineSeparator());
        helpMessage.append("   Syntax : delete-income [CATEGORY]").append(System.lineSeparator());
        helpMessage.append("   Example: delete-income Salary").append(System.lineSeparator());
        helpMessage.append(System.lineSeparator());

        // 7. Find Expense
        helpMessage.append("7. Find Expense:").append(System.lineSeparator());
        helpMessage.append("   Syntax : find-expense /desc [DESCRIPTION] OR /d [DD-MM-YYYY] " +
                "OR /amt [AMOUNT]").append(System.lineSeparator());
        helpMessage.append("            OR /category [CATEGORY] OR /amtrange [AMOUNT 1] " +
                "[AMOUNT 2] OR /drange [DATE 1] [DATE 2]").append(System.lineSeparator());
        helpMessage.append("   Example: find-expense /desc Lunch").append(System.lineSeparator());
        helpMessage.append(System.lineSeparator());

        // 8. Compare Expenses
        helpMessage.append("8. Compare Expenses:").append(System.lineSeparator());
        helpMessage.append("   Syntax : compare [MM-YYYY] [MM-YYYY]").append(System.lineSeparator());
        helpMessage.append("   Example: compare 03-2023 04-2023").append(System.lineSeparator());
        helpMessage.append(System.lineSeparator());

        // 9. Update Expense
        helpMessage.append("9. Update Expense:").append(System.lineSeparator());
        helpMessage.append("   Syntax : update-expense category/[CATEGORY] OR amt/[AMOUNT] " +
                "OR desc/[DESCRIPTION] d/[DD-MM-YYYY]").append(System.lineSeparator());
        helpMessage.append("   Example: update-expense desc/Brunch amt/20 d/21-03-2023")
                .append(System.lineSeparator());
        helpMessage.append(System.lineSeparator());

        // 10. Update Income
        helpMessage.append("10. Update Income:").append(System.lineSeparator());
        helpMessage.append("    Syntax : update-income category/[CATEGORY] OR amt/[AMOUNT] " +
                "OR d/[DD-MM-YYYY]").append(System.lineSeparator());
        helpMessage.append("    Example: update-income amt/5500").append(System.lineSeparator());
        helpMessage.append(System.lineSeparator());

        // 11. Filter Income
        helpMessage.append("11. Filter Income:").append(System.lineSeparator());
        helpMessage.append("    a. By Date:").append(System.lineSeparator());
        helpMessage.append("       Syntax : filter-income date from/DD-MM-YYYY " +
                "to/DD-MM-YYYY").append(System.lineSeparator());
        helpMessage.append("       Example: filter-income date from/01-03-2023 " +
                "to/31-03-2023").append(System.lineSeparator());
        helpMessage.append("    b. By Amount:").append(System.lineSeparator());
        helpMessage.append("       Syntax : filter-income amount from/[AMOUNT 1] " +
                "to/[AMOUNT 2]").append(System.lineSeparator());
        helpMessage.append("       Example: filter-income amount from/1000 to/5000")
                .append(System.lineSeparator());
        helpMessage.append("    c. By Category:").append(System.lineSeparator());
        helpMessage.append("       Syntax : filter-income category/[CATEGORY]")
                .append(System.lineSeparator());
        helpMessage.append("       Example: filter-income category/Salary")
                .append(System.lineSeparator());
        helpMessage.append(System.lineSeparator());

        // 12. Exit
        helpMessage.append("12. Exit:").append(System.lineSeparator());
        helpMessage.append("    Syntax : exit").append(System.lineSeparator());
        helpMessage.append("    Example: exit").append(System.lineSeparator());
        helpMessage.append(System.lineSeparator());
        helpMessage.append(border).append(System.lineSeparator());

        this.outputMessage = helpMessage + RESET;
    }

    /**
     * Centers the given text within the specified width.
     *
     * @param text  The text to center.
     * @param width The total width for centering.
     * @return A string with the text centered.
     */
    private String centerText(String text, int width) {
        if (text.length() >= width) {
            return text;
        }
        int padding = (width - text.length()) / 2;
        return " ".repeat(padding) + text;
    }
}
