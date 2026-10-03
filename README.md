# Student Performance & Assessment Management System

A console-based Java OOP project matching the accompanying project report.

## Project structure

```
src/
  Student.java              - Student entity (encapsulation)
  Assessment.java           - Abstract base class for an assessment (abstraction)
  UnitTestAssessment.java   - Assessment subclass (inheritance)
  MidTermAssessment.java    - Assessment subclass (inheritance)
  FinalExamAssessment.java  - Assessment subclass (inheritance, adds getWeightedScore())
  PerformanceManager.java   - Coordinates students/assessments, calculations, rank list
  StorageManager.java       - Loads/saves data to students.txt / assessments.txt
  Main.java                 - Console menu (entry point)
```

## OOP concepts demonstrated

- **Encapsulation** – private fields in `Student` and `Assessment`, accessed via getters.
- **Inheritance** – `UnitTestAssessment`, `MidTermAssessment`, `FinalExamAssessment` extend `Assessment`.
- **Polymorphism** – code iterates over `List<Assessment>` and calls `getExamType()`,
  which resolves to each subclass's own implementation.
- **Abstraction** – `Assessment` is abstract and defines the shared contract for all exam types.
- **Collections** – `ArrayList`, `LinkedHashMap`, and sorting with `Comparator` (rank list).
- **Exception handling** – invalid marks, invalid menu choices, and unknown student IDs are
  all caught and reported without crashing the program.
- **File I/O** – student and assessment records persist across runs in plain text files.

## How to build and run

Requires a JDK (Java 17+; the project uses `switch` expressions).

```bash
cd src
javac *.java
java Main
```

Data is saved automatically to `students.txt` and `assessments.txt` in the same folder when
you choose **Exit**, and reloaded automatically the next time you start the program.

## Sample session

```
STUDENT PERFORMANCE & ASSESSMENT MANAGEMENT SYSTEM

1. Add Student
2. Add Assessment Marks
3. View Student Records
4. Calculate Grade
5. View Performance Summary
6. View Class Rank List
7. Exit
Enter choice: 1
Enter Student ID: S101
Enter Name: Aditi Sharma
Enter Class: 10-A
Student added successfully.
```

## Extending the project

Ideas for taking this further (also listed in the report's Future Scope section):

- Swap the text-file storage for MySQL/SQLite (`StorageManager` is the only class to change).
- Add a Swing or JavaFX GUI on top of `PerformanceManager` (the business logic is already
  separated from the console I/O in `Main`).
- Add authentication so teachers and students see different menus.
- Export report cards to PDF or CSV.
