## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).


## Task Jar txt File Rules

The meta data fields are as follows.

Date, Jar, Date Completed

The task description would be on the next line right after and
would be followed by a blank line.

After the blank line another task entry can be added. 


## Meta Data Field Rules 

     DATE
The date must be defined. (I will use method later on to ensure this)

     JAR
The 'Jar' or Task Category must be surrounded by square brackets. The task category container can also be empty.  
(When empty, the 'Jar' it will be uncategorised.)
The square brackets must always be included. 

[Task Category]  OR  []

     DATE COMPLETED
Can be written as:
12-12-2024 OR
literally blank. 


## Example Task Entry

12-12-2024 [Jar] 20-12-2024
Make a Task Entry. 

## Example Jars in Richard's life

I want to keep on top of my yearly goals

- Academic Study
- Workout
- Home
- MoodLamp
- Coding
- Sport

## Improvements over my current To Do list

- Task tracking
- Organisation
- Backups (Imagine accidentally deleting the whole thing and saving it)