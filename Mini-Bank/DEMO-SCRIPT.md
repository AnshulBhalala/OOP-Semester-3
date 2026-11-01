# MiniBank video demonstration

Suggested length: 4–6 minutes. For a clean recording, exit the application and remove `MINI-BANK/data/` first. The program does not preload accounts.

1. **Introduction:** “This is MiniBank, my CEUE203 console banking application. I’ll create accounts and demonstrate transactions and saved data.”
2. **Show the code layout:** Briefly show the `model`, `service`, `util`, and `exception` packages.
3. **Launch:** From the `MINI-BANK` folder run `.\run.ps1`.
4. **Create savings account:** Choose `1`, enter `Riya Desai`, choose type `1`, and enter opening balance `1000`. Record the generated account number as **S**.
5. **Create current account:** Choose `1`, enter `Aarav Shah`, choose type `2`, and enter opening balance `1000`. Record its number as **C**.
6. **Deposit:** Choose `2`, enter **S**, then `500`. Expected: “Deposit successful”; the balance becomes ₹1500.
7. **Withdraw:** Choose `3`, enter **S**, then `200`. Expected: “Withdrawal successful”; the balance becomes ₹1300.
8. **Transfer:** Choose `4`, enter **S** as the source and **C** as the destination, then enter `300`. Expected balances: savings ₹1000 and current ₹1300.
9. **Show results:** Use `5` for each account, `6` to list accounts, and `7` for transaction history.
10. **Show an error safely:** Choose `3`, enter **S**, then `600`. The savings minimum is ₹500, so the request is rejected and the balance remains ₹1000.
11. **Exit and restart:** Choose `0`, run `.\run.ps1` again, then use `6` and `7`. The same accounts and transactions should appear.
12. **Conclusion:** Mention encapsulation, inheritance, polymorphism, checked exceptions, synchronized operations, and file persistence.

Use the account numbers printed during recording in place of **S** and **C**. If the computer already has demo data, exit and remove `MINI-BANK/data/` before recording.
