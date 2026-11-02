# Object Oriented Programming (CEUE203) — Practical Repository

**Student:** Anshul Bhalala<br>
**Student ID:** 25IT007<br>
**Department:** K.D. Patel Department of Information Technology<br>
**College:** CSPIT, CHARUSAT<br>
**Academic year:** 2026–2027 | **Semester:** 3

This repository contains the Java OOP practical programs for Part A and the MiniBank console application for Part B. Each Part A question is kept in its own folder so it can be compiled and run independently.

## Repository layout

```text
OOP-Practical_Minibank-25IT007/
├── README.md
├── .gitignore
├── Part-A/
│   ├── README.md
│   ├── 25IT007_AnshulBhalala_Part-A_Merged_File_OOP_CEUE203.pdf
│   ├── Practical_01/ ... Practical_14/
│   │   ├── Practical_NN.pdf
│   │   ├── README.md
│   │   └── TaskN_.../       # independent Java solution for each question
└── MINI-BANK/
    ├── README.md
    ├── DEMO-SCRIPT.md
    ├── pom.xml
    ├── run.ps1 / run.sh
    ├── src/minibank/        # model, service, util, exception, and Main
    ├── src/test/java/       # JUnit 5 tests
    └── lib/                 # JUnit Platform standalone runner
```

## Part A — Practicals 1–14

Each practical folder includes its completed journal PDF and question folders. Practicals 1–13 contain three questions, including the advanced learner exercise from the practical list. Practical 14 contains its two listed questions. The complete journal is also available in `Part-A/`.

To compile and run a question, change into its folder and compile its Java files. For example:

```powershell
cd Part-A/Practical_13/Task1_GenericContainers
javac *.java
java Driver
```

Some questions put `main` in the named class instead of a separate `Driver`; the practical README identifies the corresponding launch class. Practical 14's Maven tasks include JUnit tests and package-oriented source layouts; see that practical's README for commands.

## Part B — MiniBank

MiniBank is a menu-driven console application supporting savings and current accounts, deposits, withdrawals, transfers, transaction history, input validation, synchronized operations, and file persistence. Its source, test suite, run scripts, and recording guide are in [`MINI-BANK/`](MINI-BANK/).

Prerequisites: JDK 17 or later. From PowerShell, run `cd MINI-BANK; .\run.ps1`. On Linux/macOS, run `cd MINI-BANK && ./run.sh`.

## Technologies

- Java 17 or later
- JUnit 5 for automated tests
- Java collections, concurrency utilities, and NIO file handling
- Git and GitHub

## Student information

| Field | Details |
|---|---|
| Name | Anshul Bhalala |
| Student ID | 25IT007 |
| Subject | Object Oriented Programming (CEUE203) |
| Department | K.D. Patel Department of Information Technology |
| College | CSPIT, CHARUSAT |
| Academic year | 2026–2027 |
| Semester | 3 |
