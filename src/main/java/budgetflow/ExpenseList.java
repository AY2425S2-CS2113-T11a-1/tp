package budgetflow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ExpenseList {
    public static final String EMPTY_EXPENSE_LIST_MESSAGE =
            "There is currently no expense in your list right now. Please add more expenses to continue";
    private ArrayList<Expense> innerList = new ArrayList<>();
    private double totalExpenses;

    public ExpenseList() {}

    public ExpenseList(Expense... expenses) {
        final List<Expense> initialTags = Arrays.asList(expenses);
        innerList.addAll(initialTags);
    }

    public ExpenseList(Collection<Expense> expenses) {
        innerList.addAll(expenses);
    }

    public int getSize() {
        return innerList.size();
    }

    /**
     * Finding an expense from the finance tracker based on its index.
     *
     * @param index the index of the expense to be queries
     * @return Expense matching description if found, null if no expenses is found
     */
    public Expense get (int index) {
        return innerList.get(index);
    }

    /**
     * Finding an expense from the finance tracker based on its description.
     *
     * @param keyword the description of the expense to be queries
     * @return all expenses matching description if found, null otherwise
     */
    public ExpenseList get(String keyword) {
        ExpenseList outExpenses = new ExpenseList();
        for (int i = 0; i < this.getSize(); i++) {
            String desc = this.get(i).getDescription();
            if (desc != null && desc.contains(keyword)) {
                outExpenses.add(this.get(i));
            }
        }
        return outExpenses;
    }

    public void add(Expense expense) {
        innerList.add(expense);
        totalExpenses += expense.getAmount();
    }

    public void delete(Expense expense) {
        totalExpenses -= expense.getAmount();
        innerList.remove(expense);
    }

    public void delete(int index) {
        Expense deleteExpense = this.get(index);
        totalExpenses -= deleteExpense.getAmount();
        innerList.remove(index);
    }

    /**
     * Showing the list of expenses and its information regarding category, description, amount and date
     *
     * @return out string of expense list
     */
    @Override
    public String toString() {
        if (innerList.isEmpty()) {
            return EMPTY_EXPENSE_LIST_MESSAGE;
        }
        String outString = "";
        for (Expense expense: innerList) {
            if (expense == null) {
                break;
            }
            outString = outString + expense.getCategory() + " | " +
                    expense.getDescription() + " | $" +
                    String.format("%.2f", expense.getAmount()) + " | " +
                    expense.getDate() + System.lineSeparator();
        }
        return outString;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }
}
