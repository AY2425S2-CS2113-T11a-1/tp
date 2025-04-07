# A.A. Gde Yogi Pramana - Project Portfolio Page

## Project: BudgetFlow:
Budgetflow is a personal finance management application designed to help students efficiently track their income,
expenses, and savings goals. With a simple, command-based interface, Budgetflow allows users to log their transactions,
filter them by category or date, and analyze their financial habits. It is written in Java with 4kLoC.

### Summary of Contributions
* __New Feature:__ Added the ability to List Income:
    * **What it does:** Allows users to display all recorded incomes along with a detailed summary of their saving goal progress, including total income, current savings, and percentage progress.
    * **Justification:** Enhances financial transparency, helping users track their financial goals clearly and conveniently.
    * **Highlights:** Features an intuitive, structured table layout, precise savings goal calculations, and a clearly formatted summary, significantly improving readability and user experience.

* __New Feature:__ Added the ability to Filter Income by Category:
    * **What it does:** Enables users to filter and display incomes by specific categories using the `filter-income category/<category>` command.
    * **Justification:** Allows detailed analysis of income streams, empowering users to manage and review their financial records efficiently.
    * **Highlights:** Implements case-insensitive filtering, structured table outputs, robust error handling, and informative user guidance on incorrect input.

* __New Feature:__ Added the ability to Filter Income by Date:
    * **What it does:** Allows users to filter and display incomes within a specified date range using the command `filter-income date from/DD-MM-YYYY to/DD-MM-YYYY`.
    * **Justification:** Assists users in financial planning by providing clear insights into income received over selected timeframes.
    * **Highlights:** Comprehensive date validation (including leap years), structured and readable table format, and detailed error handling for invalid date entries.

* __New Feature:__ Added the ability to Filter Income by Amount:
    * **What it does:** Lets users filter incomes based on specified minimum and maximum amounts using the command `filter-income amount from/<minAmount> to/<maxAmount>`.
    * **Justification:** Supports users in analyzing their financial data by highlighting specific transaction amounts within user-defined thresholds.
    * **Highlights:** Precise numeric filtering, intuitive command syntax, structured output format, and robust error checking for incorrect parameters.

* __New Feature:__ Update UI for Log Expense with Table-wise:
    * **What it does:** Improved the user interface by formatting expense logging outputs into clear and consistent table layouts.
    * **Justification:** Ensures readability and enhances the user’s ability to quickly interpret their expense records.
    * **Highlights:** Consistent table structure, clear expense categorization, amounts, and dates, resulting in improved clarity and user engagement.

* __New Feature:__ Update UI for Welcome and Exit Screen:
    * **What it does:** Redesigned the application's welcome and exit messages to provide clear, concise, and engaging instructions for users.
    * **Justification:** Enhances first impressions and ensures users feel guided from the moment they start until they exit the application.
    * **Highlights:** Professional and aesthetically pleasing layout, informative messages, and improved user guidance, significantly enhancing the overall user experience.

  
* __Enhancements to existing features:__
    * Fix bugs for 'Program does not give example of add command when entering wrong command' [133](https://github.com/AY2425S2-CS2113-T11a-1/tp/issues/133)
    * Fix bugs for 'Program does not give correct example command of log-expense' [134](https://github.com/AY2425S2-CS2113-T11a-1/tp/issues/134)
    * Fix bugs for 'Unable to set savings goal' and 'set saving goal' [139](https://github.com/AY2425S2-CS2113-T11a-1/tp/issues/139) and [141](https://github.com/AY2425S2-CS2113-T11a-1/tp/issues/141)
    * Fix bugs for 'Conflicting Command Naming For Finding Income' [159](https://github.com/AY2425S2-CS2113-T11a-1/tp/issues/159) 
    * Fix bugs for 'Use switch case for better code readability' [172](https://github.com/AY2425S2-CS2113-T11a-1/tp/issues/172)


* __Code Contributed:__ [RepoSense Link](https://nus-cs2113-ay2425s2.github.io/tp-dashboard/?search=igoy&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-02-21&tabOpen=true&tabType=authorship&tabAuthor=IgoyAI&tabRepo=AY2425S2-CS2113-T11a-1%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)


* __Documentation:__
    * User Guide:
        * Added documentation for `list-income`, `filter income by category`, `filter income by date` and `filter income by amount ` command [#116](https://github.com/AY2425S2-CS2113-T11a-1/tp/pull/116) and [#124](https://github.com/AY2425S2-CS2113-T11a-1/tp/pull/124)
    * Developer Guide:
        * Added `list-income`, `filter income by category`, `filter income by date` and `filter income by amount ` for Implementation [#116](https://github.com/AY2425S2-CS2113-T11a-1/tp/pull/116) and [#124](https://github.com/AY2425S2-CS2113-T11a-1/tp/pull/124)


* __Community:__
    * PRs reviewed