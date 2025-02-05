## Task Jar and its objective

This is my first java project to replace my current way of managing productivity which are just two txt files named TO DO and DONE.
Each task shouldn't be longer than a paragraph because it's a task manager not a diary. 

The improvements are:
- Task tracking
- Organisation
- Backups (Imagine accidentally deleting the whole thing and saving it) I guess I'm implementing some sort of version control.

I want to keep on top of my yearly goals

- Academic Study
- Workout
- Home
- MoodLamp
- Coding
- Sport


## Class organisation

FileHandler -> Task -> TaskListModel -> TaskListPanel -> App

## Task Jar txt File Rules

The txt file is intended to be human readable and also editable.

It would consist of the following:

12-12-2024 [Jar] 20-12-2024 1706729400000 1703894400000
Make a Task Entry. 

The task header consists of
Date, Jar, Date Completed, Date(Unix Timestamp), Date Completed (Unix Timestamp)

The task description is on the next line.

A blank line then separates the tasks. 
After the blank line another task entry can be added. 

(I thought about Y2K and found out that Unix time will overflow at 03:14:07 UTC on January 19, 2038,
hopefully there will be a Task Jar 2 by then.)

## Task Header Field Rules 

Example Task header
31-1-2025 [Coding] - 1706729400000 -

     DATE
Is mandatory in the format DD-MM-YYYY or D-M-YYYY.
0s in front of the dates are not included. 

ALL FIELDS AFTER ARE SEPARATED BY ONE WHITESPACE BETWEEN THEM. 

     JAR
The 'Jar' or Task Category MUST be surrounded by square brackets. []
The task category container can also be empty.  
(When empty, the 'Jar' it will be uncategorised.)
e.g [Task Category]  OR  []

     DATE COMPLETED
Either in the same format as the DATE field OR
-
a single dash with NOTHING after it to indicate a null value.

     DATE in Unix Timestamp format (Unique primary identifier for the tasks)
This is mandatory and is created when a task is created. No two of these unix timestamps will be the same. 

     DATE COMPLETED in Unix Timestamp format
A single dash indicates that the value is null.
-
Upon hitting a checkbox in the GUI a Unix Timestamp will be generated for this field and a DD-MM-YYYY will be passed to the DATE COMPLETED field. 