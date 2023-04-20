# MaxDay_POEVer1

## About

This project is generally overcomplicated, but I decided this approach to test how much I know. Some code such as
the DeepEncrypt algo was written by me in 2021, and I have reused it to improve it and see how much I have improved.

## File breakdown

```text
src:
│
│   Main.java
│
├───API
│   ├───Credentials
│   │       DeepEncrypt.java
│   │       EraserThread.java
│   │       LoginManager.java
│   │       MainPage.java
│   │
│   ├───Table
│   │       AsciiArtTable.java
│   │       TaskManager.java
│   │
│   └───Tools
│           FileManager.java
│
└───Tables
        booby_1Table.json
        kyl_1Table.json
        maxTable.json
```

## Commands:

There are two sets of commands. One before you login and one after you have logged in.

### Pre-Login:

    - help - displays this page
    - login - provides page for user login    
    - signup - allows the user to create a new account
    - exit - exits the program

### Post-Login

    - help - displays this page
    - display - shows the updated table
    - dev list - lists all the developers who have accounts on the system
    -  dev task list - lists all the tasks that the developer has assigned to them
    -  dev max duration - lists the develop with the longest task
    - dev max search - lists the developer with the most amount of tasks
    - add - adds info to the table
    - edit - edits the table
    - remove - removes a line from the table
    - logout - logs out the user

## Credits:

[klaus31 for origional Ascii-Art-Table](https://github.com/klaus31/ascii-art-table)


