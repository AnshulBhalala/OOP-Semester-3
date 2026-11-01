# MiniBank — Console Banking Application

MiniBank is a Java console project for the CEUE203 OOP practicals. It lets a user create savings and current accounts, perform common transactions, view statements, and keep account data between runs.

## Features

- Create savings accounts (minimum opening balance ₹500) and current accounts.
- Generate sequential, unique account numbers based on saved accounts.
- Deposit, withdraw, and transfer money using `BigDecimal`.
- Enforce positive transaction amounts, savings minimum balance, and a current account overdraft limit of ₹1,000.
- View one account, list accounts, and inspect transaction history.
- Save account and transaction records to text files under `data/` and reload them at startup.
- Handle unknown accounts, invalid account types, invalid amounts, and insufficient funds with clear messages.
- Synchronize service operations and account balance access so concurrent deposits preserve the correct balance. Transfers use a consistent account lock order.

## OOP concepts

Encapsulation, an abstract `Account`, `SavingsAccount` and `CurrentAccount` inheritance, polymorphic withdrawal limits, the `Transactable` interface, constructors, checked domain exceptions, collections, file handling, and synchronized operations.

## Project structure

```text
MINI-BANK/
├── README.md
├── DEMO-SCRIPT.md
├── pom.xml
├── run.ps1
├── run.sh
├── lib/junit-platform-console-standalone.jar
├── src/minibank/
│   ├── Main.java
│   ├── model/       # Account types, Transaction, Transactable
│   ├── service/     # BankService
│   ├── util/        # FileStore
│   └── exception/   # BankException and specific exceptions
└── src/test/java/minibank/service/BankServiceTest.java
```

## Prerequisites

- JDK 17 or later
- PowerShell on Windows, or Bash on Linux/macOS
- Maven is optional; a standalone JUnit 5 runner is included in `lib/`.

## Compile and run

From PowerShell:

```powershell
cd MINI-BANK
.\run.ps1
```

If this is the first PowerShell launch in a restricted session, run `Set-ExecutionPolicy -Scope Process -ExecutionPolicy Bypass -Force`, then run `.\run.ps1`.

On Linux/macOS:

```bash
cd MINI-BANK
chmod +x run.sh
./run.sh
```

The scripts compile the application into `build/` and start `minibank.Main`. To compile manually:

```powershell
javac -encoding UTF-8 -d build (Get-ChildItem src/minibank -Recurse -Filter *.java).FullName
java -cp build minibank.Main
```

## Unit tests

The JUnit 5 suite covers account creation, deposits, withdrawals, insufficient funds, invalid amounts and account types, transfers, unknown account numbers, concurrent deposits, and persistence after a service restart.

Run with the included console runner from PowerShell:

```powershell
New-Item -ItemType Directory -Force build | Out-Null
$junit = 'lib/junit-platform-console-standalone.jar'
$sources = Get-ChildItem src -Recurse -Filter *.java
javac -encoding UTF-8 -cp $junit -d build $sources.FullName
java -jar $junit execute --class-path build --scan-class-path
```

On Linux/macOS:

```bash
mkdir -p build
javac -encoding UTF-8 -cp 'lib/junit-platform-console-standalone.jar' -d build $(find src -name '*.java')
java -jar lib/junit-platform-console-standalone.jar execute --class-path build --scan-class-path
```

Maven users can also run `mvn test`.

## Persistence

The `FileStore` writes `accounts.txt` and `transactions.txt` to `MINI-BANK/data/`. That folder is created on first run and ignored by Git. Restart the application from the same `MINI-BANK` directory to load the same records. To reset a demo, exit the application and remove `data/`.

## Sample demonstration

Use the menu to create one savings account and one current account, deposit funds, withdraw within the account rules, transfer between accounts, review the balances and transaction history, then exit and restart to confirm persistence. [`DEMO-SCRIPT.md`](DEMO-SCRIPT.md) gives a step-by-step recording guide. No accounts are preloaded.
