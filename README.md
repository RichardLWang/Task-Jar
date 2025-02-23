# Task Jar

A simple Java-based task management system designed to replace basic text file todo lists with a user-friendly GUI interface while maintaining human-readable storage.

## Table of Contents
- [Features](#features)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [File Format](#file-format)
- [Categories](#categories)
- [Technical Details](#technical-details)

## Features
- Task tracking with dates and categories
- Simple GUI interface
- Human-readable and editable storage format
- Task organization (FIFO/LIFO ordering)
- Completion tracking
- Built-in task categorization

## Getting Started
1. Clone the repository
2. Run App.java
3. Tasks are stored in "Development Task Jar.txt"

## Usage

### Controls
- **New Task**: Creates a blank task at the top of the list
- **FIFO/LIFO**: Toggles task ordering

- **Click Task**: Opens task for editing
- **Save**: Saves changes to current task
- **Finish**: Marks task as complete with current date

## File Format

Tasks are stored in a human-readable text format:

```txt
31-1-2025 [Coding] -
Make a Task Entry.

12-12-2024 [Exercise] 20-12-2024
Complete workout routine.