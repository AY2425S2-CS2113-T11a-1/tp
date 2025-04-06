package budgetflow.command;

import budgetflow.exception.InvalidDateException;
import budgetflow.exception.InvalidNumberFormatException;
import budgetflow.exception.MissingAmountException;
import budgetflow.exception.MissingDateException;
import budgetflow.exception.MissingCategoryException;
import budgetflow.exception.MissingDescriptionException;
import budgetflow.expense.Expense;
import budgetflow.expense.ExpenseList;
import budgetflow.income.Income;
import budgetflow.storage.Storage;
import budgetflow.parser.DateValidator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.logging.Logger;

public class UpdateExpenseCommand extends Command {
    public static final String CATEGORY_REGEX = "^[a-zA-Z0-9]+$";
    public static final String DESCRIPTION_REGEX = "^[a-zA-Z0-9]+$";
    public static final int MAX_DIGITS = 7;
    public static final int MAX_DECIMAL_PLACES = 2;

    private static final Logger logger = Logger.getLogger(UpdateExpenseCommand.class.getName());

    private static final String UPDATE_EXPENSE_COMMAND_PREFIX = "update-expense ";
    private static final int UPDATE_EXPENSE_COMMAND_PREFIX_LENGTH = UPDATE_EXPENSE_COMMAND_PREFIX.length();

    private static final String ERROR_MISSING_INDEX = "Error: Index is required.";
    private static final String ERROR_EXPENSE_ENTRY_NOT_FOUND = "Error: Expense entry not found.";
    private static final String ERROR_WRONG_INDEX_FORMAT = "Error: Index must be a number.";
    private static final String ERROR_EMPTY_EXPENSE_LIST = "Error: No expense entries exist to update.";
    private static final String ERROR_WRONG_DATE_FORMAT = "Error: Invalid date format. Usage: DD-MM-YYYY";
    private static final String ERROR_INVALID_AMOUNT = "Error: Invalid amount format.";
    private static final String ERROR_INVALID_CATEGORY = "Error: Invalid category. " +
            "It must contain only alphanumeric characters.";
    private static final String ERROR_INVALID_DESCRIPTION = "Error: Invalid description. " +
            "It must contain only alphanumeric characters.";
    private static final Pattern CATEGORY_PATTERN = Pattern.compile("category/([^\\s]+)\\s*(desc/|amt/|d/|$)");
    private static final Pattern AMT_PATTERN = Pattern.compile(
            "amt/\\s*([1-9][0-9]*(\\.[0-9]*[1-9])?|0\\.[0-9]*[1-9])");
    private static final Pattern DESC_PATTERN = Pattern.compile("desc/([^\\s]+)\\s*(amt/|d/|$)");
    private static final Pattern DATE_PATTERN = Pattern.compile("d/\\s*(\\d{2}-\\d{2}-\\d{4,})");
    private static final String ERROR_MORE_THAN_7_DIGITS = "Amount exceeds 7 digits. " +
            "Please enter a number with up to 7 digits.";
    private static final String ERROR_MORE_THAN_2_DP = "Amount must have at most 2 decimal places.";
    private static final String EMPTY_SPACE = " ";

    private static final int MINIMUM_INDEX = 0;
    private static final int UPDATE_PARAMETER_GROUP = 1;

    public UpdateExpenseCommand(String input) {
        super(input);
        this.commandType = CommandType.UPDATE;
    }

    @Override
    public void execute(List<Income> incomes, ExpenseList expenseList)
            throws MissingDateException, InvalidNumberFormatException, MissingAmountException,
            MissingCategoryException, MissingDescriptionException, InvalidDateException {

        if (input.length() <= UPDATE_EXPENSE_COMMAND_PREFIX_LENGTH) {
            throw new InvalidNumberFormatException(ERROR_MISSING_INDEX);
        }

        String commandArgs = input.substring(UPDATE_EXPENSE_COMMAND_PREFIX_LENGTH).trim();
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

        this.outputMessage = "Expense updated: " + existingExpense.getCategory() + ", Description: " +
                existingExpense.getDescription() + ", Amount: $" + String.format("%.2f", existingExpense.getAmount()) +
                ", Date: " + existingExpense.getDate();
        logger.info("Expense updated successfully: " + existingExpense);
    }

    private static void updateStorage(List<Income> incomes, ExpenseList expenseList) {
        Storage storage = new Storage();
        storage.saveData(incomes, expenseList);
    }

    private void extractUpdatedExpense(String input, Expense existingExpense)
            throws MissingAmountException, MissingDateException, MissingCategoryException, MissingDescriptionException,
            InvalidDateException, InvalidNumberFormatException {

        existingExpense.setCategory(getUpdatedCategory(input, existingExpense.getCategory()));
        existingExpense.setAmount(getUpdatedAmount(input, existingExpense.getAmount()));
        existingExpense.setDescription(getUpdatedDescription(input, existingExpense.getDescription()));
        existingExpense.setDate(getUpdatedDate(input, existingExpense.getDate()));
    }

    private static String getUpdatedCategory(String input, String currentCategory)
            throws MissingCategoryException {
        // Matcher for the category in the input
        Matcher matcher = CATEGORY_PATTERN.matcher(input);
        if (matcher.find()) {
            String extractedCategory = matcher.group(UPDATE_PARAMETER_GROUP).trim();

            // Check if the category contains only alphanumeric characters (no symbols allowed)
            if (!extractedCategory.matches(CATEGORY_REGEX)) {  // CATEGORY_REGEX = "^[a-zA-Z0-9]+$"
                logger.severe("Invalid category input detected: " + extractedCategory);
                throw new MissingCategoryException(ERROR_INVALID_CATEGORY);  // Throw the exception for invalid category
            }

            return extractedCategory;  // Return valid category
        }

        // Handle case when category is not found in the input and check if the current category is valid
        if (currentCategory == null || currentCategory.trim().isEmpty()) {
            throw new MissingCategoryException(ERROR_INVALID_CATEGORY);  // Throw exception if no valid category
        }

        return currentCategory;  // Return current category if valid
    }

    private static String getUpdatedDescription(String input, String currentDescription)
            throws MissingDescriptionException {
        Matcher matcher = DESC_PATTERN.matcher(input);
        if (matcher.find()) {
            String extractedDescription = matcher.group(UPDATE_PARAMETER_GROUP).trim();

            // Explicitly check if description contains only alphanumeric characters
            if (!extractedDescription.matches(DESCRIPTION_REGEX)) {
                logger.severe("Invalid description input detected: " + extractedDescription);
                throw new MissingDescriptionException(ERROR_INVALID_DESCRIPTION);
            }

            return extractedDescription;
        }

        if (currentDescription == null || currentDescription.isEmpty()) {
            throw new MissingDescriptionException(ERROR_INVALID_DESCRIPTION);
        }

        return currentDescription;
    }

    private static String getUpdatedDate(String input, String currentDate)
            throws InvalidDateException {
        Matcher matcher = DATE_PATTERN.matcher(input);
        if (matcher.find()) {
            String extractedDate = matcher.group(UPDATE_PARAMETER_GROUP).trim();
            if (!DateValidator.isValidDate(extractedDate)) {
                throw new InvalidDateException(ERROR_WRONG_DATE_FORMAT);
            }
            return extractedDate;
        }
        return currentDate;
    }

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
}
