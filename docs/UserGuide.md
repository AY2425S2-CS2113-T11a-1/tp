# BudgetFlow User Guide

### Welcome to Budgetflow!

Managing finances as a student can be overwhelming—keeping track of expenses, ensuring savings goals are met, and
understanding spending habits all require time and effort. **Budgetflow** is here to simplify this process for you!

Designed with students in mind, Budgetflow helps you **effortlessly track income and expenses, set savings goals, and
gain insights into your financial habits.** Whether you're saving for your next trip, managing your daily coffee budget,
or planning your monthly expenses, Budgetflow ensures you stay in control.

With a **simple, command-based interface**, Budgetflow is lightweight, efficient, and fast—perfect for students who
prefer typing over navigating complex apps. You can log your transactions, filter them based on category or date, and
even compare monthly expenses at ease.  
* [Features](#features)
  * [Logging an Expense](#1-logging-an-expense)
  * [Updating an Expense Entry](#2-updating-an-expense-entry)
  * [Deleting an Income](#3-deleting-an-income)
  * [Deleting an Expense](#4-deleting-an-expense)
  * [Comparing two Monthly Expenses](#5-comparing-two-monthly-expenses-)
  * [Adding an Income](#6-adding-an-income)
  * [Updating an Income](#7-updating-an-income-)
  * [Setting a Savings Goal](#8-setting-a-savings-goal)
  * [Viewing all expenses](#9-viewing-all-expenses)
  * [Filtering expenses](#10-filtering-expenses)
    * [Filtering expenses based on category](#101-filtering-expenses-based-on-category)
    * [Filtering expenses based on description](#102-filtering-expenses-based-on-description)
    * [Filtering expenses based on amount](#103-filtering-expenses-based-on-amount)
    * [Filtering expenses based on amount range](#104-filtering-expenses-based-on-amount-range)
    * [Filtering expenses based on date](#105-filtering-expenses-based-on-date)
    * [Filtering expenses based on date range](#106-filtering-expenses-based-on-date-range)
  * [Listing Income](#11-listing-income)
  * [Filter Income](#12-filtering-incomes)
    * [Filtering Income by Category](#121-filtering-income-by-category)
    * [Filtering Income by Amount](#122-filtering-income-by-amount)
    * [Filtering Income by Amount Range](#123-filtering-income-by-amount-range)
    * [Filtering Income by Date](#124-filtering-income-by-date)
  * [Exiting the Application](#13-exiting-the-application)
  * [help](#14-help)
* [FAQ](#faq)
* [Command Summary](#command-summary)
---

Notes about the command format:
* When entering commands in the system, please ensure you include a space between each parameter.
  Example of incorrect format:
  ```add category/1amt/1.125456789 d/11-11-2025```
  Example of correct format:
 ```add category/1amt 1.125456789 d/11-11-2025```
This note applies to all commands. The above is an example of add income command.
  The system expects space separation between the category parameter, amount value, and date parameter. Without proper spacing, the system may misinterpret your input or generate unexpected results.
* For all commands involving monetary amounts (e.g., income or expense entries), the following rules apply:
✅ The integer part of the amount must not exceed 7 digits.
✅ The decimal part must not exceed 2 digits (i.e., cents).
These constraints reflect realistic daily usage for students, the primary target users of this application. As such, it is unlikely that users would need to record transactions involving more than 7 digits, and dollar amounts conventionally support up to 2 decimal places.
* This app is meant for users who prefer to type in CLI-command in English. For other language characters, the chatbot may not be able to parse them. 

## Features

### 1. Logging an Expense
**Description:** Logs a new expense with a category, description, amount, and date.  
Note: the inputs must follow the specified order, and there should be proper spacing between each component.  
**Command:**
```plaintext
log-expense category/<CATEGORY> desc/<DESCRIPTION> amt/<AMOUNT> d/<DATE>
```
**Example:**
```plaintext
log-expense category/Coffee desc/Coffee amt/3.50 d/06-03-2025
```
**Output:**
```plaintext
Saving goal retrieved: 0.0
Expense logged: Coffee | Coffee | $3.50 | 06-03-2025
```

---

### 2. Updating an Expense Entry (Not income)
**Description:** Updates an instance of an Expense entry.  
**How it works:** The user only has to write the index and the part they would like to update.  
For example, if the user only wants to update the amount, they can run:

```plaintext
update-expense INDEX amt/[UPDATED_AMOUNT]
```

if user only wants to update the category and amount, they can run:

```plaintext
update-expense INDEX amt/[UPDATED_AMOUNT] category/[UPDATED_CATEGORY]
```

Note: `amount`, `category`, `date` and `description` can be in any order

**Input:**
```plaintext
update-expense [INDEX] category/[UPDATED_CATEGORY] desc/[UPDATED_DESCRIPTION] amt/[UPDATED_AMOUNT] d/[UPDATED_DATE]
```
**Output:**
```plaintext
Expense updated: [UPDATED_CATEGORY], Description: [UPDATED_DESCRIPTION], Amount: [UPDATED_AMOUNT], Date: [UPDATED_DATE]
```

---

### 3. Deleting an Income
**Description:** Deletes an instance of an Income entry.
**Command:**
```plaintext
delete-income <NAME OF INCOME ENTRY>
```
**Output:**
```plaintext
Saving goal retrieved: [REMAINING OF SAVING GOAL]
Income deleted: [NAME OF INCOME ENTRY]
```

---

### 4. Deleting an Expense
**Description:** Deletes an instance of an Expense entry.  
**Command:**
```plaintext
delete-expense <INDEX OF EXPENSE ENTRY>
```
**Output:**
```plaintext
Saving goal retrieved: <REMAINING OF SAVING GOAL>
Income deleted: <DESCRIPTION OF EXPENSE ENTRY>, $<AMT>
```

---

### 5. Comparing two Monthly Expenses 
**Description:** Compares Monthly expenses between two months  
**Command:**
```plaintext
compare <MM-YYYY> <MM-YYYY>
```
**Output:**
```plaintext
Total expenses for MM-YYYY: [AMOUNT]
Total expenses for MM-YYYY: [AMOUNT]
```

---
### 6. Adding an Income
**Description:** Add a new income with a category, amount, and date.  
Note: the inputs must follow the specified order, and there should be proper spacing between each component.  
**Command:**
```plaintext
add category/<CATEGORY> amt/<AMOUNT> d/<DATE>
```
**Example:**
```plaintext
add category/Salary amt/2000.00 d/06-03-2025
```
**Output:**
```plaintext
Income added: Salary, Amount: $2000.00, Date: 06-03-2025
```

---

### 7. Updating an Income 
**Description:** Updates an instance of an Income entry.  
**How it works** The user only has to write the Index and the thing that the user would like to update (note: need spacing between each part).  
E.g. If the user only wants to update the amt, he can run `update-income INDEX amt/[UPDATED_AMOUNT]`  
if user only wants to update the category and amount, they can run:
```plaintext
update-expense INDEX amt/[UPDATED_AMOUNT] category/[UPDATED_CATEGORY]
```
Note: amount, category and date can be in any order (no description)
**Command:**
```plaintext
update-income [INDEX] category/[UPDATED_CATEGORY], amt/[UPDATED_AMOUNT] d/[UPDATED_DATE]
```
**Output:**
```plaintext
Income updated: [UPDATED_CATEGORY], Amount: [UPDATED_AMOUNT], Date: [UPDATED_DATE]
```

### 8. Setting a Savings Goal
**Description:** Sets a savings goal with a specified amount and optional target date.  
**Command:**
```plaintext
set-saving-goal <GOAL_AMOUNT> 
```
**Example:**
```plaintext
set-saving-goal 100000
```
**Output:**
```plaintext
Saving goal set to: $100000.00
```
---

### 9. Viewing all expenses
**Description:** Views all existing expenses that have been logged with the total sum of expenses' amount.   
**Command:**
```plaintext
view-all-expense
```
**Output:**
* For empty list:
```plaintext
No expenses have been logged yet.
```
* For non-empty list:
```plaintext
Expenses log:
[INFORMATION OF EXPENSES]
```

Output example: 
```plaintext
Expenses log:
1 | Coffee | Coffee | $3.50 | 06-03-2025
2 | drink | Juice | $6.50 | 06-04-2015
Total Expenses: $10.00
```
---

### 10. Filtering expenses
**Overview:** Filters expenses based the keyword and given condition.  
 
#### 10.1. Filtering expenses based on category
**Description:** Filters all expenses with category that exactly match with keyword.
**Command:**
```plaintext
find-expense /category <CATEGORY>
```
* tag `/category` indicates filtering by category.
* keyword `<CATEGORY>` containing the name of category we want to find expenses from.
* __NOTE__: the tag and keywords are __CASE SENSITIVE__

**Output:**
```plaintext
Here are all matching expenses:
[INFORMATION OF MATCHING EXPENSES]
```
**Example:**
```plaintext
find-expense /category drink
Here are all matching expenses:
drink | Juice | $6.50 | 06-04-2015
```
#### 10.2. Filtering expenses based on description
**Description:** Filters all expenses with description that contain keyword.
**Command:**
```plaintext
find-expense /desc <DESC>
```
* tag `/desc` indicates filtering by description
* keyword `<DESC>` containing the name of category we want to find expenses from.
* __NOTE__: the tag and keywords are __CASE SENSITIVE__

**Output:**
```plaintext
Here are all matching expenses:
[INFORMATION OF MATCHING EXPENSES]
```
**Example:**
```plaintext
find-expense /desc Coffee
Here are all matching expenses:
Coffee | Coffee | $3.50 | 06-03-2025
```
#### 10.3. Filtering expenses based on amount
**Description:** Filters all expenses that matches certain amount value.
**Command:**
```plaintext
find-expense /amt <AMT>
```
* tag `/amt` indicates filtering by amount
* keyword `<AMT>` containing the amount value of expenses we want to search from.
* __NOTE__: 
  * the tag is __CASE SENSITIVE__. 
  * Value of `<AMT>` must be a valid integer or decimal number with digit before dot. Some examples of keyword that may lead to error: `string`, `.12` (no digit before dot), `5..6` (multiple dots).

**Output:**
```plaintext
Here are all matching expenses:
[INFORMATION OF MATCHING EXPENSES]
```
**Example:**
```plaintext
find-expense /amt 3.50
Here are all matching expenses:
Coffee | Coffee | $3.50 | 06-03-2025
---
```
#### 10.4. Filtering expenses based on amount range
**Description:** Filters all expenses with amount value within indicated range.
**Command:**
```plaintext
find-expense /amtrange <AMT1> <AMT2>
```
* tag `/amtrange` indicates filtering by amount range.
* keywords `<AMT1>` and `<AMT2>` indicating the range of amount value we want to filter expenses from.
  * `<AMT1>`: The lower bound of value range used for filtering.
  * `<AMT2>`: The upper bound of value range used for filtering.
* __NOTE__:
    * the tag is __CASE SENSITIVE__.
    * Values of `<AMT1>` and `<AMT2>` must be valid integer or decimal number with digit before dot. Some examples of keyword that may lead to error: `string`, `.12` (no digit before dot), `5..6` (multiple dots).
    * `<AMT1>` and `<AMT2>` must be separated by at least 1 space ` `.

**Output:**
```plaintext
Here are all matching expenses:
[INFORMATION OF MATCHING EXPENSES]
```
**Example:**
```plaintext
find-expense /amtrange 3.00 6.50
Here are all matching expenses:
Coffee | Coffee | $3.50 | 06-03-2025
drink | Juice | $6.50 | 06-04-2015
---
```

#### 10.5. Filtering expenses based on date
**Description:** Filters all expenses that matches a certain date.
**Command:**
```plaintext
find-expense /d <DATE>
```
* tag `/d` indicates filtering by date.
* keyword `<DATE>` indicate the date to filter expenses from.
* __NOTE__:
    * the tag is __CASE SENSITIVE__.
    * Value of `<DATE>` must follow format dd-MM-yyyy.

**Output:**
```plaintext
Here are all matching expenses:
[INFORMATION OF MATCHING EXPENSES]
```
**Example:**
```plaintext
find-expense /d 06-03-2025
Here are all matching expenses:
Coffee | Coffee | $3.50 | 06-03-2025
---
```
#### 10.6. Filtering expenses based on date range
**Description:** Filters all expenses with the date lies within an indicated date range.
**Command:**
```plaintext
find-expense /drange <DATE1> <DATE2>
```
* tag `/drange` indicates filtering by date range.
* keywords `<DATE1>` and `<DATE2>` respectively indicating the first date to start searching from and the last date to search to.
* __NOTE__:
    * the tag is __CASE SENSITIVE__.
    * Values of `<DATE1>` and `<DATE2>` must follow format dd-MM-yyyy.
    * `<DATE1>` and `<DATE2>` must be separated by at least 1 space ` `.

**Output:**
```plaintext
Here are all matching expenses:
[INFORMATION OF MATCHING EXPENSES]
```
**Example:**
```plaintext
find-expense /drange 06-03-2022 07-12-2025
Here are all matching expenses:
Coffee | Coffee | $3.50 | 06-03-2025
---
```

### 11. Listing Income

**Overview:** Lists all recorded income entries.

**Description:** Provides a comprehensive list of all income records.

**Command:**

```plaintext
list-income
```

**Output:**

```plaintext
Here are all recorded income entries:
[INFORMATION OF ALL INCOME ENTRIES]
```

**Example:**

```plaintext
list-income
Here are all recorded income entries:
Salary | Monthly Salary | $3000.00 | 01-04-2025
Freelance | Website Development | $500.00 | 05-04-2025
---
```

---

### 12. Filtering incomes
**Overview:** Filters incomes based the keyword and given condition.

#### 12.1 Filtering income by Category
**Description:** Filters all income entries that exactly match the provided category keyword.

**Command:**

```plaintext
filter-income category/category
```
- Tag category/ specifies the category for filtering.
- Tag and keyword are CASE INSENSITIVE.
- The keyword <category> must not be empty.
- 
**Output:**

```plaintext
Filtered Incomes by Category: <category>

Category             | Amount     | Date           
---------------------+------------+----------------
[Matching income entries displayed here]

If no entries match, the output will be:
No incomes found under the specified category.
```

**Example:**

```plaintext
filter-income category/Salary

Filtered Incomes by Category: Salary

Category             | Amount     | Date           
---------------------+------------+----------------
Salary               | $3000.00   | 01-04-2025   
---
```

---

#### 12.2 Filtering Income by Amount

**Description:** Filters income entries within a specified amount range.

**Command:**

```plaintext
filter-income amount from/<minAmount> to/<maxAmount>
```
- Tag from/ specifies the minimum amount.
- Tag to/ specifies the maximum amount.
- Both tags and values are CASE SENSITIVE.
- Values must be valid integers or decimal numbers, starting with a digit (valid: 100, 500.50; invalid: string, .50, 3..2).
- The minimum amount must be less than or equal to the maximum amount.

**Output:**

```plaintext
Filtered Incomes by Amount Range: <minAmount> to <maxAmount>

Category             | Amount     | Date           
---------------------+------------+----------------
[Matching income entries displayed here]

If no entries match, the output will be:
No incomes found in the specified amount range.
```

**Example:**

```plaintext
filter-income amount from/100.00 to/1000.00

Filtered Incomes by Amount Range: 100.00 to 1000.00

Category             | Amount     | Date           
---------------------+------------+----------------
Freelance            | $500.00    | 05-04-2025     
Salary               | $900.00    | 01-04-2025     
---
```

---

### 14. Filtering Income by Date

**Description:** Filters income entries within a specified date range.

**Command:**

```plaintext
filter-income date from/DD-MM-YYYY to/DD-MM-YYYY
```
- Tag from/ specifies the start date of the range.
- Tag to/ specifies the end date of the range.
- Both tags and date values are CASE SENSITIVE.
- Dates must follow the format DD-MM-YYYY and must be valid calendar dates (valid: 01-01-2025; invalid: 31-02-2025, string, 12/12/2025).
- The start date must be earlier than or equal to the end date.

**Output:**

```plaintext
Output:

Filtered Incomes by Date: <fromDate> to <toDate>

Category             | Amount     | Date           
---------------------+------------+----------------
[Matching income entries displayed here]

If no entries match, the output will be:
No incomes found in the specified date range. 
```

**Example:**

```plaintext
Filtered Incomes by Date (01-03-2025 to 31-03-2025)

Category             | Amount     | Date           
---------------------+------------+----------------
Salary               | $1000.00   | 06-03-2025     
---
```

### 16. Exiting the Application
**Description:** Safely exits the BudgetFlow application.  
**Command:**
```plaintext
exit
```
**Output:**
```plaintext
============================================================
              Thank you for using Budgetflow!
                          Goodbye!
============================================================
```

### 14. help
**Description:** The `help` Command provides users with a comprehensive list of all available commands 
and their syntax for managing incomes and expenses within the budget management system.
**Command:**
```plaintext
help
```
**Output:**
```plaintext
<ALL COMMANDS>
```

## FAQ

**Q: Does BudgetFlow support multiple currencies?**  
A: Currently, BudgetFlow only supports tracking in one currency at a time.

**Q: Can I edit or delete an entry after logging it?**  
A: Yes! You can use the `update-expense` or `delete-expense` commands to modify or remove an expense entry. Similarly, you can use `delete-income` to remove an income entry.

---

## Command Summary
| **Command**                                                                                                           | **Description**                                                          |
|-----------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------|
| `add category/<CATEGORY> amt/<AMOUNT> d/<DATE>`                                                                       | Adds an income entry with the specified category, amount, and date.      |
| `log-expense category/<CATEGORY> desc/<DESCRIPTION> amt/<AMOUNT> d/<DATE>`                                            | Logs an expense entry with details.                                      |
| `view-all-expense`                                                                                                    | Displays all logged expenses.                                            |
| `list income`                                                                                                         | Shows all recorded incomes.                                              |
| `update-expense [INDEX] category/[UPDATED_CATEGORY] desc/[UPDATED_DESCRIPTION] amt/[UPDATED_AMOUNT] d/[UPDATED_DATE]` | Updates an expense entry at index `<INDEX>`.                             |
| `find-expense /category <CATEGORY>`                                                                                   | Filters expense based on category                                        |
| `find-expense /desc <DESCRIPTION>`                                                                                    | Filters expense based on description                                     |
| `find-expense /amt <AMT>`                                                                                             | Filters expense based on amount                                          |
| `find-expense /amtrange <AMT1> <AMT2>`                                                                                | Filters expense within an amount range                                   |
| `find-expense /d <DATE>`                                                                                              | Filters expense based on date                                            |
| `find-expense /drange <DATE1> <DATE2>`                                                                                | Filters expense within a date range                                      |
| `filter-income date from/<DATE1> to/<DATE2>`                                                                          | Filters income entries within a specific date range.                     |
| `filter-income amount from/<AMOUNT1> to/<AMOUNT1>`                                                                    | Filters income entries within a specified amount range.                  |
| `filter-income category/<CATEGORY>`                                                                                   | Filters income by category.                                              |
| `delete-income <CATEGORY OF INCOME ENTRY>`                                                                            | Deletes an income entry with the category `<NAME OF INCOME ENTRY>`.      |
| `delete-expense <DESCRIPTION OF EXPENSE ENTRY>`                                                                       | Deletes an expense entry with the description `<NAME OF EXPENSE ENTRY>`. |
| `update-income [INDEX] category/[UPDATED_CATEGORY] amt/[UPDATED_AMOUNT] d/[UPDATED_DATE]`                             | Updates an income entry at index `<INDEX>`.                              |
| `compare <Month1> <Month2>`                                                                                           | Compares total expenses between `<Month1>` and `<Month2>`.               | 
| `exit`                                                                                                                | Exits the application safely.                                            |
| `help`                                                                                                                | Displays a comprehensive list of all commands                            |

