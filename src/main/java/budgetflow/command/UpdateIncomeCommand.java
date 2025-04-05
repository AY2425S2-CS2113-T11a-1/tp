package budgetflow.command;

import budgetflow.exception.InvalidNumberFormatException;
import budgetflow.exception.MissingAmountException;
import budgetflow.exception.MissingDateException;
import budgetflow.expense.ExpenseList;
import budgetflow.income.Income;
import budgetflow.parser.DateValidator;
import budgetflow.storage.Storage;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@@ author Yikbing
/**
 * The class handles the logic for updating an existing income record in the system.
 * It processes the input command to extract relevant
 * income details (category, amount, date) and updates the
 * corresponding income entry in the list. It also saves the updated data to storage.
 */
public class UpdateIncomeCommand extends Command {
    private static final Logger logger = Logger.getLogger(UpdateIncomeCommand.class.getName());

    private static final String UPDATE_COMMAND_PREFIX = "update-income ";
    private static final int UPDATE_COMMAND_PREFIX_LENGTH = UPDATE_COMMAND_PREFIX.length();

    private static final String ERROR_INVALID_DATE = "Error: Date is not a valid date";
    private static final String ERROR_WRONG_DATE_FORMAT = "Error: Invalid date format. Usage: DD-MM-YYYY";
    private static final String ERROR_MISSING_INDEX = "Error: Index is required.";
    private static final String ERROR_INCOME_ENTRY_NOT_FOUND = "Error: Income entry not found.";
    private static final String ERROR_WRONG_INDEX_FORMAT = "Error: Index must be a number.";
    private static final String ERROR_INVALID_AMOUNT = "Error: Invalid amount format.";
    private static final String ERROR_EMPTY_INCOME_LIST = "Error: No income entries exist to update.";
    private static final String ASSERT_EMPTY_CATEGORY = "Category cannot be empty.";
    private static final String ASSERT_POSITIVE_AMOUNT = "Amount must be a positive number.";
    private static final String ASSERT_VALID_DATE = "Date must be valid.";

    private static final String CATEGORY_PATTERN = "category/([^ ]+)";
    private static final String AMT_PATTERN = "amt/([^ ]+)";
    private static final String DATE_PATTERN = "d/([^ ]+)";
    private static final String CORRECT_DATE_PATTERN = "\\d{2}-\\d{2}-\\d{4}";
    private static final String CORRECT_AMOUNT_PATTERN = "\\d{1,7}(\\.\\d{1,2})?";
    private static final String VALID_AMOUNT_FORMAT = "^\\d+(\\.\\d+)?$";

    private static final int INDEX_POSITION_IN_1_INDEX = 0;
    private static final int MINIMUM_INDEX = 0;
    private static final int UPDATE_PARAMETER_GROUP = 1;
    private static final int MINIMUM_PARTS_FOR_UPDATE = 2;


    //@@ author Yikbing
    /**
     * Constructs an UpdateIncomeCommand with the given input string.
     *
     * @param input The input command string to be processed,
     *     containing the income ID and update details../gradlew check
     */
    public UpdateIncomeCommand(String input) {
        super(input);
        this.commandType = CommandType.UPDATE;
    }

    //@@ author Yikbing
    /**
     * Executes the income update command by parsing the input, validating the index and other parameters,
     * and updating the corresponding income in the list.
     *
     * @param incomes The list of existing incomes.
     * @param expenseList The expense list (not used in this method, but passed to storage).
     * @throws MissingDateException If the provided date is invalid or missing.
     * @throws InvalidNumberFormatException If the index is in an invalid format.
     * @throws MissingAmountException If the amount is missing in the update input.
     */
    @Override
    public void execute(List<Income> incomes, ExpenseList expenseList) throws MissingDateException,
            InvalidNumberFormatException, MissingAmountException{
        String[] parts = input.substring(UPDATE_COMMAND_PREFIX_LENGTH).trim().split(" ", 2);
        if (parts.length < MINIMUM_PARTS_FOR_UPDATE) {
            throw new InvalidNumberFormatException(ERROR_MISSING_INDEX);
        }

        if (incomes.isEmpty()) {
            throw new InvalidNumberFormatException(ERROR_EMPTY_INCOME_LIST);
        }

        int index;
        try {
            index = Integer.parseInt(parts[INDEX_POSITION_IN_1_INDEX]) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidNumberFormatException(ERROR_WRONG_INDEX_FORMAT);
        }
        if (index < MINIMUM_INDEX || index >= incomes.size()) {
            throw new InvalidNumberFormatException(ERROR_INCOME_ENTRY_NOT_FOUND);
        }

        Income existingIncome = incomes.get(index);
        Income updatedIncome = extractUpdatedIncome(parts[1], existingIncome);
        incomes.set(index, updatedIncome);

        updateStorage(incomes, expenseList);

        this.outputMessage = "Income updated: " + updatedIncome.getCategory() + ", Amount: $" +
                String.format("%.2f", updatedIncome.getAmount()) + ", Date: " + updatedIncome.getDate();
        logger.info("Income updated successfully: " + updatedIncome);
    }

    //@@ author Yikbing
    /**
     * Saves the updated incomes and expense list to storage.
     *
     * @param incomes The updated list of incomes.
     * @param expenseList The expense list to be saved.
     */
    private static void updateStorage(List<Income> incomes, ExpenseList expenseList) {
        Storage storage = new Storage();
        storage.saveData(incomes, expenseList);
    }

    //@@ author Yikbing
    /**
     * Extracts the updated income details from the input and creates a new Income object.
     *
     * @param input The input string containing the updated income details.
     * @param existingIncome The existing income entry to use for any missing information.
     * @return The updated Income object.
     * @throws MissingAmountException If the amount is missing in the input.
     * @throws MissingDateException If the date is invalid or missing in the input.
     */
    private Income extractUpdatedIncome(String input, Income existingIncome)
            throws MissingAmountException,
             MissingDateException {
        String category = existingIncome.getCategory();
        Double amount = existingIncome.getAmount();
        String date = existingIncome.getDate();


        Pattern categoryPattern = Pattern.compile(CATEGORY_PATTERN);
        Pattern amtPattern = Pattern.compile(AMT_PATTERN);
        Pattern datePattern = Pattern.compile(DATE_PATTERN);


        category = getUpdatedCategory(input, categoryPattern, category);

        amount = getUpdatedAmount(input, amtPattern, amount);

        date = getUpdatedDate(input, datePattern, date);

        assert !category.isEmpty() : ASSERT_EMPTY_CATEGORY;
        assert amount > 0 : ASSERT_POSITIVE_AMOUNT;
        assert DateValidator.isValidDate(date) : ASSERT_VALID_DATE;

        return new Income(category, amount, date);
    }

    //@@ author Yikbing
    /**
     * Extracts and validates the updated date from the input string.
     *
     * @param input The input string containing the updated date.
     * @param datePattern The compiled regular expression pattern for the date.
     * @param date The current date to fall back on if not updated.
     * @return The updated date as a String.
     * @throws MissingDateException If the date is invalid.
     */
    private static String getUpdatedDate(String input, Pattern datePattern, String date)
            throws MissingDateException {
        Matcher matcher;
        matcher = datePattern.matcher(input);
        if (matcher.find()) {
            String extractedDate = matcher.group(UPDATE_PARAMETER_GROUP).trim();
            if (!extractedDate.matches(CORRECT_DATE_PATTERN)) {
                throw new MissingDateException(ERROR_WRONG_DATE_FORMAT);
            }
            if (!DateValidator.isValidDate(extractedDate)) {
                throw new MissingDateException(ERROR_INVALID_DATE);
            }
            date = extractedDate;
        }

        return date;
    }

    //@@ author Yikbing
    /**
     * Extracts and validates the updated amount from the input string.
     *
     * @param input The input string containing the updated amount.
     * @param amtPattern The compiled regular expression pattern for the amount.
     * @param amount The current amount to fall back on if not updated.
     * @return The updated amount as a Double.
     * @throws MissingAmountException If the amount is invalid.
     */
    private static Double getUpdatedAmount(String input, Pattern amtPattern, Double amount)
            throws MissingAmountException {
        Matcher matcher;
        matcher = amtPattern.matcher(input);
        if (matcher.find()) {

            String extractedAmount = matcher.group(UPDATE_PARAMETER_GROUP).trim();
            Pattern numericPattern = Pattern.compile(CORRECT_AMOUNT_PATTERN);
            Matcher numericMatcher = numericPattern.matcher(extractedAmount);
            if (numericMatcher.matches()) {
                amount = Double.parseDouble(extractedAmount);
            } else{
                verifyErrorType(extractedAmount);
            }
        }
        return amount;
    }

    /**
     * Verifies the format of a monetary amount string and throws a {@link MissingAmountException}
     * if the format is invalid. The method checks whether:
     *   The integer part (before the decimal point) exceeds 7 digits.
     *   The decimal part (after the decimal point) exceeds 2 digits.
     * If neither of these conditions is met, a generic invalid amount exception is thrown.
     *
     * @param extractedAmount the monetary amount string to be validated
     * @throws MissingAmountException if:
     *           the integer part exceeds 7 digits,
     *           the decimal part exceeds 2 digits, or
     *           the format is otherwise deemed invalid
     */
    private static void verifyErrorType(String extractedAmount) throws MissingAmountException {

        if (!extractedAmount.matches(VALID_AMOUNT_FORMAT)) {
            throw new MissingAmountException(ERROR_INVALID_AMOUNT);
        }
        String[] parts = extractedAmount.split("\\.");
        String integerPart = parts[0];
        String decimalPart = parts.length > 1 ? parts[1] : "";

        if (integerPart.length() > 7) {
            throw new MissingAmountException("ERROR: Integer part exceeds 7 digits.");
        }

        if (decimalPart.length() > 2) {
            throw new MissingAmountException("ERROR: Decimal part exceeds 2 digits.");
        }

    }

    //@@ author Yikbing
    /**
     * Extracts and validates the updated category from the input string.
     *
     * @param input The input string containing the updated category.
     * @param categoryPattern The compiled regular expression pattern for the category.
     * @param category The current category to fall back on if not updated.
     * @return The updated category as a String.
     */
    private static String getUpdatedCategory(String input, Pattern categoryPattern, String category) {
        assert (!(category.isEmpty())) : ASSERT_EMPTY_CATEGORY;
        Matcher matcher;
        matcher = categoryPattern.matcher(input);
        if (matcher.find()) {
            category = matcher.group(UPDATE_PARAMETER_GROUP).trim();
        }
        return category;
    }
}

