package budgetflow.command;
import budgetflow.exception.*;
import budgetflow.expense.ExpenseList;
import budgetflow.income.Income;

import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@@author QuyDatNguyen
/**
 * Represents a command to find expenses based on a given keyword.
 * If no matching expenses are found, an exception is thrown.
 */
public class FindExpenseCommand extends Command {
    private static final Logger logger = Logger.getLogger(FindExpenseCommand.class.getName());
    private static final String COMMAND_FIND_EXPENSE = "find-expense";
    private static final String ERROR_MISSING_KEYWORD = "Error: Missing keyword";
    private static final String ERROR_UNFOUNDED_KEYWORD = "Sorry, I cannot find any expenses matching your keyword: ";
    private static final String MATCHING_EXPENSES_MESSAGE = "Here are all matching expenses: ";

    private static final Pattern COMMAND_PATTERN = Pattern.compile("find-expense\s+(/desc|/d|/amt|/category)\s+(.+)");
    private static final String ERROR_INVALID_KEYWORD_FORMAT = "Please enter correct keyword format for tag ";
    public static final String ERROR_NO_TAG_OR_KEYWORD = "Invalid or missing tag/keyword in find-expense command";
    public static final String ERROR_INVALID_TAG = "Please enter valid tag for query";
    public static final String ASSERTION_FAIL_INVALID_FIND_COMMAND = "Invalid find expense command format";

    /**
     * Constructs a FindExpenseCommand with the given input.
     *
     * @param input The full command input string containing the keyword to search for.
     */
    public FindExpenseCommand(String input) {
        super(input);
        this.commandType = CommandType.READ;
    }

    /**
     * Executes the find expense command by searching for expenses that match the provided keyword.
     * If no keyword is provided, a {@link MissingKeywordException} is thrown.
     * If no matching expenses are found, an {@link UnfoundExpenseException} is thrown.
     *
     * @param incomes      The list of incomes (not modified by this command).
     * @param expenseList  The list of expenses to search within.
     * @throws UnfoundExpenseException   If no expenses match the given keyword.
     * @throws MissingKeywordException   If no keyword is provided in the command input.
     * @throws InvalidTagException  If the tag is unknown/ invalid
     * @throws InvalidKeywordException If keyword does not follow the valid format with corresponding tag
     */
    @Override
    public void execute(List<Income> incomes, ExpenseList expenseList) throws UnfoundExpenseException,
            MissingKeywordException, InvalidTagException, InvalidKeywordException {
        assert input.startsWith(COMMAND_FIND_EXPENSE) : ASSERTION_FAIL_INVALID_FIND_COMMAND;
        String[] parsedTagAndKeyword = extractTagAndKeyword();
        String tag = parsedTagAndKeyword[0];
        String keyword = parsedTagAndKeyword[1];
        ExpenseList matchingExpenses = getMatchingExpenses(expenseList, tag, keyword);

        if (matchingExpenses.getSize() == 0) {
            logger.warning("No expenses found for " + tag + " with keyword: " + keyword);
            throw new UnfoundExpenseException(ERROR_UNFOUNDED_KEYWORD + keyword);
        } else {
            this.outputMessage = MATCHING_EXPENSES_MESSAGE + System.lineSeparator() +
                    matchingExpenses;
            logger.info("Matching found: " + matchingExpenses);
        }
    }

    private static ExpenseList getMatchingExpenses(ExpenseList expenseList, String tag, String keyword) throws InvalidTagException, InvalidKeywordException {
        ExpenseList matchingExpenses;
        try {
            matchingExpenses = expenseList.getByTag(tag, keyword);
        } catch (InvalidDateException | InvalidNumberFormatException e) {
            throw new InvalidKeywordException(e.getMessage());
        } return matchingExpenses;
    }

    private String[] extractTagAndKeyword() throws MissingKeywordException, InvalidTagException, InvalidKeywordException {
        Matcher matcher = COMMAND_PATTERN.matcher(input);
        if (!matcher.matches()) {
            logger.warning(ERROR_NO_TAG_OR_KEYWORD);
            throw new MissingKeywordException(ERROR_MISSING_KEYWORD);
        }
        String tag = matcher.group(1);
        String keyword = matcher.group(2).trim();
        // Define correct patterns for validation
        boolean isValid = isValidKeywordPattern(tag, keyword);
        if (!isValid) {
            logger.warning("Invalid format for tag: " + tag + " with keyword: " + keyword);
            throw new InvalidKeywordException(ERROR_INVALID_KEYWORD_FORMAT + tag);
        }
        return new String[] {tag, keyword};
    }

    private static boolean isValidKeywordPattern(String tag, String keyword) throws InvalidTagException {
        String categoryPattern = ".+";
        String descPattern = ".+";
        String amtPattern = "[0-9]+(\\.[0-9]*)?";
        String datePattern = "\\d{2}-\\d{2}-\\d{4}";

        // Validate keyword format based on the tag
        return switch (tag) {
            case "/desc" -> keyword.matches(descPattern);
            case "/date" -> keyword.matches(datePattern);
            case "/amount" -> keyword.matches(amtPattern);
            case "/category" -> keyword.matches(categoryPattern);
            default -> throw new InvalidTagException(ERROR_INVALID_TAG);
        };
    }
}
