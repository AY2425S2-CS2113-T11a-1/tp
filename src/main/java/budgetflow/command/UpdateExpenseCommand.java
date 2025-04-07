package budgetflow.command;

import budgetflow.exception.InvalidDateException;
import budgetflow.exception.InvalidNumberFormatException;
import budgetflow.exception.MissingAmountException;
import budgetflow.exception.MissingCategoryException;
import budgetflow.exception.MissingDateException;
import budgetflow.exception.MissingDescriptionException;
import budgetflow.expense.Expense;
import budgetflow.expense.ExpenseList;
import budgetflow.income.Income;
import budgetflow.parser.DateValidator;
import budgetflow.storage.Storage;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@@author dariusyawningwhiz
/**
 * Command to update an existing expense entry.
 * This command extracts and updates specified attributes of an expense entry
 * such as category, description, amount, and date.
 */
public class UpdateExpenseCommand extends Command {
    public static final String CATEGORY_REGEX = "^[a-zA-Z0-9]+$";
    public static final String DESCRIPTION_REGEX = "^[a-zA-Z0-9]+$";
    public static final int MAX_DIGITS = 7;
    public static final int MAX_DECIMAL_PLACES = 2;

    /**
     * Logger for tracking updates.
     */
    private static final Logger logger =
            Logger.getLogger(UpdateExpenseCommand.class.getName());

    private static final String UPDATE_EXPENSE_COMMAND_PREFIX = "update-expense ";
    private static final int UPDATE_EXPENSE_COMMAND_PREFIX_LENGTH =
            UPDATE_EXPENSE_COMMAND_PREFIX.length();

    private static final String ERROR_MISSING_INDEX = "Error: Index is required.";
    private static final String ERROR_EXPENSE_ENTRY_NOT_FOUND =
            "Error: Expense entry not found.";
    private static final String ERROR_WRONG_INDEX_FORMAT = "Error: Index must be a number.";
    private static final String ERROR_EMPTY_EXPENSE_LIST =
            "Error: No expense entries exist to update.";
    private static final String ERROR_WRONG_DATE_FORMAT =
            "Error: Invalid date format. Usage: DD-MM-YYYY";
    private static final String ERROR_INVALID_AMOUNT = "Error: Invalid amount format.";

    private static final String ERROR_INVALID_CATEGORY = "Error: Invalid category. " +
            "It must contain only alphanumeric characters.";
    private static final String ERROR_INVALID_DESCRIPTION = "Error: Invalid description. " +
            "It must contain only alphanumeric characters.";
    private static final Pattern CATEGORY_PATTERN = Pattern.compile("category/([^\\s]+)\\s*(desc/|amt/|d/|$)");
    private static final Pattern AMT_PATTERN = Pattern.compile(
            "amt/\\s*([1-9][0-9]*(\\.[0-9]*[1-9])?|0\\.[0-9]*[1-9])");
    private static final Pattern DESC_PATTERN = Pattern.compile("desc/([^\\s]+)\\s*(amt/|d/|$)");
    private static final Pattern DATE_PATTERN = Pattern.compile("d/\\s*(\\d{1,2}-\\d{1,2}-\\d{4,})");
    private static final String ERROR_MORE_THAN_7_DIGITS = "Amount exceeds 7 digits. " +
            "Please enter a number with up to 7 digits.";

    private static final String ERROR_MORE_THAN_2_DP = "Amount must have at most 2 decimal places.";
    private static final String EMPTY_SPACE = " ";

    private static final int MINIMUM_INDEX = 0;
    private static final int UPDATE_PARAMETER_GROUP = 1;

    /**
     * Constructs an UpdateExpenseCommand with the given input.
     *
     * @param input The command input string.
     */
    public UpdateExpenseCommand(String input) {
        super(input);
        this.commandType = CommandType.UPDATE;
    }

    /**
     * Saves the updated expense list to storage.
     *
     * @param incomes     List of incomes.
     * @param expenseList List of expenses.
     */
    private static void updateStorage(List<Income> incomes, ExpenseList expenseList) {
        Storage storage = new Storage();
        storage.saveData(incomes, expenseList);
    }

    /**
     * Extracts the updated category from input.
     *
     * @param input           The input string containing category update.
     * @param currentCategory The current category of the expense.
     * @return The updated category.
     * @throws MissingCategoryException If the category is missing or invalid.
     */
    private static String getUpdatedCategory(String input, String currentCategory)
            throws MissingCategoryException {
        Matcher matcher = CATEGORY_PATTERN.matcher(input);
        if (matcher.find()) {
            String extractedCategory = matcher.group(UPDATE_PARAMETER_GROUP).trim();
            if (!extractedCategory.matches(CATEGORY_REGEX)) {
                logger.severe("Invalid category input detected: " + extractedCategory);
                throw new MissingCategoryException(ERROR_INVALID_CATEGORY);
            }
            return extractedCategory;
        }
        if (currentCategory == null || currentCategory.trim().isEmpty()) {
            throw new MissingCategoryException(ERROR_INVALID_CATEGORY);
        }
        return currentCategory;
    }

    /**
     * Extracts the updated description from input.
     *
     * @param input               The input string containing description update.
     * @param currentDescription  The current description of the expense.
     * @return The updated description.
     * @throws MissingDescriptionException If the description is missing or invalid.
     */
    private static String getUpdatedDescription(String input, String currentDescription)
            throws MissingDescriptionException {
        Matcher matcher = DESC_PATTERN.matcher(input);
        if (matcher.find()) {
            String extractedDescription = matcher.group(UPDATE_PARAMETER_GROUP).trim();
            if (!extractedDescription.matches(DESCRIPTION_REGEX)) {
                logger.severe("Invalid description input detected: "
                        + extractedDescription);
                throw new MissingDescriptionException(ERROR_INVALID_DESCRIPTION);
            }
            return extractedDescription;
        }
        if (currentDescription == null || currentDescription.isEmpty()) {
            throw new MissingDescriptionException(ERROR_INVALID_DESCRIPTION);
        }
        return currentDescription;
    }

    /**
     * Extracts and validates the updated date from the input string.
     * If a valid date is found, it is returned.
     *
     * @param input       The user input containing potential date update.
     * @param currentDate The current date of the expense.
     * @return The updated date if found and valid.
     * @throws InvalidDateException If the extracted date is in an invalid format.
     */
    private static String getUpdatedDate(String input, String currentDate)
            throws InvalidDateException {
        Matcher matcher = DATE_PATTERN.matcher(input);
        if (matcher.find()) {
            String extractedDate = matcher.group(UPDATE_PARAMETER_GROUP).trim();
            if (!extractedDate.matches("\\d{2}-\\d{2}-\\d{4}")) {
                throw new InvalidDateException(ERROR_WRONG_DATE_FORMAT);
            }
            if (!DateValidator.isValidDate(extractedDate)) {
                throw new InvalidDateException(ERROR_WRONG_DATE_FORMAT);
            }

            return extractedDate;
        }

        return currentDate;

    }

    /**
     * Extracts and validates the updated amount from the input string.
     * If a valid amount is found, it is returned.
     *
     * @param input         The user input containing potential amount update.
     * @param currentAmount The current amount of the expense.
     * @return The updated amount if found and valid.
     * @throws InvalidNumberFormatException If the extracted amount is not a valid number,
     *                                      exceeds the maximum number of digits, or has too many
     *                                      decimal places.
     */
    private static Double getUpdatedAmount(String input, Double currentAmount)
            throws InvalidNumberFormatException {
        Matcher matcher = AMT_PATTERN.matcher(input);
        if (matcher.find()) {
            try {
                String amountStr = matcher.group(UPDATE_PARAMETER_GROUP).trim();
                Double amount = Double.parseDouble(amountStr);
                String[] parts = amountStr.split("\\.");
                String integerPart = parts[0];
                if (integerPart.length() > MAX_DIGITS) {
                    throw new InvalidNumberFormatException(ERROR_MORE_THAN_7_DIGITS);
                }
                if (parts.length > 1) {
                    String decimalPart = parts[1];
                    if (decimalPart.length() > MAX_DECIMAL_PLACES) {
                        throw new InvalidNumberFormatException(ERROR_MORE_THAN_2_DP);
                    }
                }
                return amount;
            } catch (NumberFormatException e) {
                throw new InvalidNumberFormatException(ERROR_INVALID_AMOUNT);
            }
        }
        return currentAmount;
    }

    /**
     * Executes the command by updating an expense entry in the list.
     *
     * @param incomes     List of incomes (not used in this command but required by signature).
     * @param expenseList List of expenses where the update will be performed.
     * @throws MissingDateException If the date is missing.
     * @throws InvalidNumberFormatException If an invalid number format is found.
     * @throws MissingAmountException If the amount is missing.
     * @throws MissingCategoryException If the category is missing.
     * @throws MissingDescriptionException If the description is missing.
     * @throws InvalidDateException If the date format is invalid.
     */
    //@@author dariusyawningwhiz
    @Override
    public void execute(List<Income> incomes, ExpenseList expenseList)
            throws MissingDateException, InvalidNumberFormatException,
            MissingAmountException, MissingCategoryException,
            MissingDescriptionException, InvalidDateException {

        if (input.length() <= UPDATE_EXPENSE_COMMAND_PREFIX_LENGTH) {
            throw new InvalidNumberFormatException(ERROR_MISSING_INDEX);
        }

        String commandArgs = input.substring(UPDATE_EXPENSE_COMMAND_PREFIX_LENGTH)
                .trim();
        if (commandArgs.isEmpty()) {
            throw new InvalidNumberFormatException(ERROR_MISSING_INDEX);
        }

        String[] parts = commandArgs.split(EMPTY_SPACE, 2);
        if (parts.length < 1) {
            throw new InvalidNumberFormatException(ERROR_MISSING_INDEX);
        }

        if (expenseList.getSize() == MINIMUM_INDEX) {
            throw new InvalidNumberFormatException(ERROR_EMPTY_EXPENSE_LIST);
        }

        int index;
        try {
            index = Integer.parseInt(parts[0]) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidNumberFormatException(ERROR_WRONG_INDEX_FORMAT);
        }

        if (index < MINIMUM_INDEX || index >= expenseList.getSize()) {
            throw new InvalidNumberFormatException(ERROR_EXPENSE_ENTRY_NOT_FOUND);
        }

        Expense existingExpense = expenseList.get(index);

        if (parts.length > 1) {
            extractUpdatedExpense(parts[1], existingExpense);
        }

        expenseList.updateTotalExpenses();
        updateStorage(incomes, expenseList);

        this.outputMessage = "Expense updated: " + existingExpense.getCategory()
                + ", Description: " + existingExpense.getDescription()
                + ", Amount: $" + String.format("%.2f", existingExpense.getAmount())
                + ", Date: " + existingExpense.getDate();
        logger.info("Expense updated successfully: " + existingExpense);
    }

    //@@author dariusyawningwhiz
    /**
     * Extracts and applies updated attributes to an existing expense entry.
     *
     * @param input           The input string containing updated fields.
     * @param existingExpense The expense entry to update.
     */
    private void extractUpdatedExpense(String input, Expense existingExpense)
            throws MissingAmountException, MissingDateException,
            MissingCategoryException, MissingDescriptionException,
            InvalidDateException, InvalidNumberFormatException {

        existingExpense.setCategory(getUpdatedCategory(input,
                existingExpense.getCategory()));
        existingExpense.setAmount(getUpdatedAmount(input, existingExpense.getAmount()));
        existingExpense.setDescription(getUpdatedDescription(input,
                existingExpense.getDescription()));
        existingExpense.setDate(getUpdatedDate(input, existingExpense.getDate()));
    }
}
