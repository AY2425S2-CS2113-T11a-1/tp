package budgetflow.command;

import budgetflow.exception.InvalidNumberFormatException;
import budgetflow.exception.MissingDateException;
import budgetflow.exception.MissingAmountException;
import budgetflow.exception.MissingCategoryException;
import budgetflow.exception.MissingDescriptionException;
import budgetflow.exception.MissingExpenseException;
import budgetflow.exception.ExceedsMaxDigitException;

import budgetflow.expense.Expense;
import budgetflow.expense.ExpenseList;
import budgetflow.income.Income;
import budgetflow.parser.DateValidator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Logger;

//@@author thienkimtranhoang
public class LogExpenseCommand extends Command{
    public static final String ERROR_INVALID_DATE = "Error: Date is not a valid date";
    public static final String CATEGORY_REGEX = "[a-zA-Z0-9]+";
    public static final String DESCRIPTION_REGEX = "[a-zA-Z0-9]+";
    public static final int MAX_DIGITS = 7;
    public static final int MAX_DECIMAL_PLACES = 2;
    public static final int GROUP_INDEX = 1;

    private static final Logger logger = Logger.getLogger(LogExpenseCommand.class.getName());
    private static final String LOG_EXPENSE_COMMAND_PREFIX = "log-expense ";
    private static final int LOG_EXPENSE_COMMAND_PREFIX_LENGTH = LOG_EXPENSE_COMMAND_PREFIX.length();
    private static final String ERROR_EMPTY_EXPENSE = "Expense should not be empty";
    private static final String ERROR_MISSING_EXPENSE_CATEGORY = "Error: Expense category is required.";
    private static final String ERROR_MISSING_EXPENSE_DESCRIPTION = "Error: Expense description is required.";
    private static final String ERROR_MISSING_EXPENSE_AMOUNT = "Error: Expense amount is required.";
    private static final String ERROR_MISSING_EXPENSE_DATE = "Error: Expense date is required.";
    private static final String ERROR_INCORRECT_EXPENSE_DATE = "Error: Expense date is in wrong format." +
            "please use DD-MM-YYYY format.";
    private static final String ERROR_MORE_THAN_7_DIGITS = "Amount exceeds 7 digits. " +
            "Please enter a number with up to 7 digits.";
    private static final String ERROR_MORE_THAN_2_DP = "Amount must have at most 2 decimal places.";
    private static final String ERROR_INVALID_CATEGORY = "Error: Category must contain only alphabets or digits.";
    private static final String ERROR_INVALID_DESCRIPTION = "Error: Description must contain only alphabets or digits.";
    private static final String WARNING_MORE_THAN_7_DIGITS = "Amount exceeds 7 digit limit: ";
    private static final String WARNING_MORE_THAN_2_DP = "Amount has more than 2 decimal digits: ";
    public LogExpenseCommand(String input) {
        super(input);
        this.commandType = CommandType.CREATE;
    }

    /**
     * Logs new user expense into the expense list
     * @param expenseList the list storing all expenses
     * @throws MissingDateException if user miss the date of expense or date tag
     * @throws InvalidNumberFormatException if amount does not follow valid number format
     * @throws MissingAmountException if user miss the amount of expense or amount tag
     * @throws MissingCategoryException if user miss the category of expense or expense tag
     * @throws MissingDescriptionException if user miss description of expense or expense tag
     */
    @Override
    public void execute(List<Income> incomes, ExpenseList expenseList) throws MissingDateException,
            InvalidNumberFormatException, MissingAmountException, MissingCategoryException,
            MissingDescriptionException, MissingExpenseException,ExceedsMaxDigitException {
        Expense expense = extractExpense(input);
        expenseList.add(expense);
        this.outputMessage = "Expense logged: " + expense.getCategory() + " | " + expense.getDescription() +
                " | $" + String.format("%.2f", expense.getAmount()) + " | " + expense.getDate();
    }

    //@@author dariusyawningwhiz
    private Expense extractExpense (String input) throws InvalidNumberFormatException,
            MissingCategoryException, MissingAmountException, MissingDateException,
            MissingDescriptionException, MissingExpenseException, ExceedsMaxDigitException {
        assert input != null && !input.isEmpty() : ERROR_EMPTY_EXPENSE;
        assert input.startsWith(LOG_EXPENSE_COMMAND_PREFIX) : "Invalid log expense format";

        input = input.substring(LOG_EXPENSE_COMMAND_PREFIX_LENGTH).trim();

        if (input.isEmpty()) {
            throw new MissingExpenseException(ERROR_EMPTY_EXPENSE);
        }

        String category = null;
        String description = null;
        Double amount = null;
        String date = null;

        String categoryPattern = "category/([^ ]+) (desc/|amt/|d/|$)";
        String descPattern = "desc/([^ ]+) (amt/|d/|$)";
        String amtPattern = "amt/\\s*([1-9][0-9]*(\\.[0-9]*[1-9])?|0\\.[0-9]*[1-9])";
        String datePattern = "d/\\s*(\\d{2}-\\d{2}-\\d{4,})";

        java.util.regex.Pattern pattern;
        java.util.regex.Matcher matcher;

        pattern = java.util.regex.Pattern.compile(categoryPattern);
        matcher = pattern.matcher(input);
        if (matcher.find()) {
            category = matcher.group(GROUP_INDEX).trim();
        }
        pattern = java.util.regex.Pattern.compile(descPattern);
        matcher = pattern.matcher(input);
        if (matcher.find()) {
            description = matcher.group(GROUP_INDEX).trim();
        }
        pattern = java.util.regex.Pattern.compile(amtPattern);
        matcher = pattern.matcher(input);
        if (matcher.find()) {
            try {
                amount = Double.parseDouble(matcher.group(GROUP_INDEX));
                String[] parts = matcher.group(GROUP_INDEX).split("\\.");
                String integerPart = parts[0];

                if (integerPart.length() > MAX_DIGITS) {
                    logger.warning(WARNING_MORE_THAN_7_DIGITS + integerPart);
                    throw new ExceedsMaxDigitException(ERROR_MORE_THAN_7_DIGITS);
                }

                if (parts.length > GROUP_INDEX) {
                    String decimalPart = parts[GROUP_INDEX];
                    if (decimalPart.length() > MAX_DECIMAL_PLACES) {
                        logger.warning(WARNING_MORE_THAN_2_DP + decimalPart);
                        throw new ExceedsMaxDigitException(ERROR_MORE_THAN_2_DP);
                    }
                }
            } catch (NumberFormatException e) {
                logger.warning("Invalid amount format: " + input);
                throw new InvalidNumberFormatException();
            }
        }
        pattern = java.util.regex.Pattern.compile(datePattern);
        matcher = pattern.matcher(input);
        if (matcher.find()) {
            date = matcher.group(GROUP_INDEX).trim();
            if (!DateValidator.isValidDate(date)) {
                logger.warning("Invalid date input: " + date);
                throw new MissingDateException(ERROR_INVALID_DATE);
            }
        } else {
            verifyMissingOrIncorrect(input);
        }

        if (category == null || category.isEmpty()) {
            throw new MissingCategoryException(ERROR_MISSING_EXPENSE_CATEGORY);
        }
        if (!category.matches(CATEGORY_REGEX)) {
            throw new MissingCategoryException(ERROR_INVALID_CATEGORY);
        }

        if (description == null || description.isEmpty()) {
            throw new MissingDescriptionException(ERROR_MISSING_EXPENSE_DESCRIPTION);
        }
        if (!description.matches(DESCRIPTION_REGEX)) {
            throw new MissingDescriptionException(ERROR_INVALID_DESCRIPTION);
        }

        if (amount == null) {
            throw new MissingAmountException(ERROR_MISSING_EXPENSE_AMOUNT);
        }
        if (date == null) {
            throw new MissingDateException(ERROR_MISSING_EXPENSE_DATE);
        }
        return new Expense(category, description, amount, date);
    }

    private String extractPattern(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find() ? matcher.group(1).trim() : null;
    }

    private static void verifyMissingOrIncorrect(String input) throws MissingDateException {
        java.util.regex.Pattern pattern;
        java.util.regex.Matcher matcher;
        String invalidDatePattern = "d/(\\S+)";
        pattern = java.util.regex.Pattern.compile(invalidDatePattern);
        matcher = pattern.matcher(input);
        if (matcher.find()) {
            String invalidDate = matcher.group(GROUP_INDEX).trim();
            logger.warning("Invalid date input: " + invalidDate);
            throw new MissingDateException(ERROR_INCORRECT_EXPENSE_DATE);
        } else {
            logger.warning("Missing date input");
            throw new MissingDateException(ERROR_MISSING_EXPENSE_DATE);
        }
    }

}
