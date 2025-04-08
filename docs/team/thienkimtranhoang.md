# Tran Hoang Thien Kim - Project Portfolio Page

## Project: BudgetFlow:
Budgetflow is a personal finance management application designed to help students efficiently track their income,
expenses, and savings goals. With a simple, command-based interface, Budgetflow allows users to log their transactions,
filter them by category or date, and analyze their financial habits. It is written in Java with 10kLoC.

### Summary of Contributions
* __New Feature:__ Added the ability to log income entries:
    * **What it does:** This feature allows users to record their income using the `add` command. The user provides the `category`, `amount`, and `date` of the income, which is then saved in the Income List.
    * **Justification:** This feature helps users track their earnings efficiently, allowing them to gain a clearer understanding of their financial inflows. By maintaining a record of their income, users can make informed budgeting decisions.
  * **Highlights:** The feature supports categorization, making it easier for users to differentiate between different
    income sources such as salary, freelance work, and bonuses. Besides, trailing spaces after/before parameters and
    restrictions on values (i.e amount) are also enhanced to ensure consistency throughout the chatbot.

    <br>  

* __New Feature:__ Added the ability to log expense entries:
    * **What it does:** This feature allows users to record their expense using the `log-expense` command. The user
      provides the `category`, `description`, `amount`, and `date` of the income, which is then saved in the Income
      List.
    * **Justification:** This feature helps users track their expenditures efficiently, allowing them to gain a clearer
      understanding of their financial outflows. By maintaining a record of their income, users can make informed
      budgeting decisions.
    * **Highlights:** The feature supports categorization, making it easier for users to differentiate between different
      income sources such as salary, freelance work, and bonuses. Besides, trailing spaces after/before parameters and
      restrictions on values (i.e amount) are also enhanced to ensure consistency throughout the chatbot.

    <br>  

* __New Feature:__ Added the ability to set a savings goal and track progress:
    * **What it does:** This feature enables users to set a financial savings target using the `set-saving-goal` command. Users specify a goal amount and an optional target date, helping them work toward their financial objectives.
    * **Justification:** This feature empowers users to plan their finances strategically by setting clear savings goals. It enhances motivation by providing a measurable target and allowing users to track progress.
    * **Highlights:** The feature ensures users can manage their financial goals effectively, making adjustments as needed to stay on track toward achieving their savings targets.

* __Enhancements to existing features:__
  * Added add-on information of saving goal and saving progress to list income command  [#97](https://github.com/AY2425S2-CS2113-T11a-1/tp/pull/97)
  * Enhance date validator for add income and log-expense command, ensuring the year format stay resonable with 4 digits [[#190]](https://github.com/AY2425S2-CS2113-T11a-1/tp/pull/190)
  * Fixed bugs for Date Validator in Add Income and Log Expense [[#196]](https://github.com/AY2425S2-CS2113-T11a-1/tp/pull/196)
  * Added comprehensive Junit Test Cases for Add Income, Log Expense, Set Saving Goal, Filter Expense, List Income, View Expense, ExpenseList and Parser [[#206]](https://github.com/AY2425S2-CS2113-T11a-1/tp/pull/206)
* __Code Contributed:__ [RepoSense Link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=thienkim&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21)
  <br><br>
* __Project Management:__
    * Managed releases `v1.0` - `v2.0` (2 releases) on GitHub
      <br><br>
* __Documentation:__
    * User Guide:
        * Added documentation for ```add income``` and ```set saving goal``` [[#110]](https://github.com/AY2425S2-CS2113-T11a-1/tp/pull/110)
        * Updated documentation for other commands sequence diagram [[#119]](https://github.com/AY2425S2-CS2113-T11a-1/tp/pull/119)
        * Added comprehensive notes for command formats[[#119]](https://github.com/AY2425S2-CS2113-T11a-1/tp/pull/119)
    * Developer Guide:
        * Added Setting-up instructions [[#113]](https://github.com/AY2425S2-CS2113-T11a-1/tp/pull/113)
        * Added documentation for ```add income``` command and ```set saving goal``` command [[#110]](https://github.com/AY2425S2-CS2113-T11a-1/tp/pull/110)
        * Added documentation for ```FinanceTracker``` class [[#119]](https://github.com/AY2425S2-CS2113-T11a-1/tp/pull/119)
        * Added documentation for ```Income``` class [[#119]](https://github.com/AY2425S2-CS2113-T11a-1/tp/pull/119)
        * Added documentation for `Documentation` part [[#217]](https://github.com/AY2425S2-CS2113-T11a-1/tp/pull/217)
        * Updated more information for non-functional requirement [[#110]](https://github.com/AY2425S2-CS2113-T11a-1/tp/pull/110)
          <br><br>
* __Community:__
    * Reviewed Developer Guide of the team and fixed format error
    * PRs reviewed (with non-trivial review comments):
    * Reported bugs and suggestions for other teams in the class