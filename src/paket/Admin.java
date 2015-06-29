package paket;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Admin extends User {
	private static final String AdminName = "burek";
	private static final String AdminPassword = "1234";

	public static String getAdminName() {
		return AdminName;
	}

	public static String getAdminPassword() {
		return AdminPassword;
	}

	/** Metoda koja adminu omogucava dodavanje novog korisnika */
	public static void addUser(File file) {
		String name, password;
		int balance;
		boolean passwordIsUsable = false;
		Scanner input = new Scanner(System.in);
		do {
			System.out.println("Enter name: ");

			name = input.next();
			if (UserBase.accountExists(name))
				System.out
						.println("An account under this name already exits. Try again.");
		} while (UserBase.accountExists(name));

		do {
			System.out.println("Enter password: ");

			password = input.next();
			if (!passwordCanBeUsed(password))
				System.out
						.println("Paasword must consist of 4 digits. Try Again.");
		} while (!passwordCanBeUsed(password));
		System.out.println("Enter balance: ");
		balance = input.nextInt();
		UserBase.getUserBase().add(new User(name, password, balance));
		System.out.println("\nDodali ste korisnika" + name);
		try {
			UserBase.printUsers(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/** Metoda koja adminu omogucava brisanje postojeceg korisnika */
	public static void removeUser(String username) {
		Scanner input = new Scanner(System.in);
		if (UserBase.accountExists(username)) {
			for (int i = 0; i < UserBase.getUserBase().size(); i++) {
				User user = (User) UserBase.getUserBase().get(i);
				if (user.getUserName().equals(username)) {
					System.out.println("Dou you want to delete" + username
							+ "? Answer with Y or N.");

					if (input.next().toUpperCase().equals("Y")) {
						UserBase.getUserBase().remove(i);
						System.out.println("You deleted " + username + ".");
					}
					else System.out.println("You didnt delete" + username + ".");
				}
			}
		}
		else System.out.println("Acount doesnt exist.");

	}

	/** Metoda koja adminu omogucava brisanje postojeceg korisnika */
	public static boolean passwordCanBeUsed(String password) {
		boolean digitPassword = true;
		for (int i = 0; i < password.length(); i++) {
			if (!Character.isDigit(password.charAt(i))) {
				digitPassword = false;
			}
		}
		return (digitPassword && password.length() == 4);
	}

	/** Metoda koja adminu omogucava dodavanje novcanica */
	public static void addCash() {
		Scanner input = new Scanner(System.in);
		boolean validCashAdd = false;
		int addAmount, fullAmount;
		String message = "You can enter maximum %2d bills\nTry Again: ";

		do {
			System.out
					.println("Enter the number of 100 bills you want to add: ");
			addAmount = input.nextInt();
			fullAmount = addAmount + CopyOfATM.getNovcanica100();
			if (fullAmount <= 100) {
				CopyOfATM.setNovcanica100(fullAmount);
				System.out.println("You added " + addAmount
						+ " 100 bills to the ATM.");
				validCashAdd = true;
			} else {
				System.out.printf(message, (100 - CopyOfATM.getNovcanica100()));
			}
		} while (!validCashAdd);

		validCashAdd = false;
		do {
			System.out
					.println("Enter the number of 50 bills you want to add: ");
			addAmount = input.nextInt();
			fullAmount = addAmount + CopyOfATM.getNovcanica50();
			if (fullAmount <= 100) {
				CopyOfATM.setNovcanica50(fullAmount);
				System.out.println("You added " + addAmount
						+ " 50 bills to the ATM.");
				validCashAdd = true;
			} else {
				System.out.printf(message, (100 - CopyOfATM.getNovcanica50()));
			}
		} while (!validCashAdd);

		validCashAdd = false;
		do {
			System.out
					.println("Enter the number of 20 bills you want to add: ");
			addAmount = input.nextInt();
			fullAmount = addAmount + CopyOfATM.getNovcanica20();
			if (fullAmount <= 100) {
				CopyOfATM.setNovcanica20(fullAmount);
				System.out.println("You added " + addAmount
						+ " 20 bills to the ATM.");
				validCashAdd = true;
			} else {
				int iznos = 100 - CopyOfATM.getNovcanica20();
				System.out.printf(message, iznos);
			}
		} while (!validCashAdd);

		validCashAdd = false;
		do {
			System.out
					.println("Enter the number of 10 bills you want to add: ");
			addAmount = input.nextInt();
			fullAmount = addAmount + CopyOfATM.getNovcanica10();
			if (fullAmount <= 100) {
				CopyOfATM.setNovcanica10(fullAmount);
				System.out.println("You added " + addAmount
						+ " 10 bills to the ATM.");
				validCashAdd = true;
			} else {
				System.out.printf(message, (100 - CopyOfATM.getNovcanica10()));
			}
		} while (!validCashAdd);
	}

	/** Metoda kojoj se pokrece administratorski meni */
	public static void adminMenu(File file) {
		Scanner input = new Scanner(System.in);
		String password;
		int numberOftry = 3;
		do {
			System.out.print("Enter password: ");
			password = input.next();
			if (password.equals(getAdminPassword()))
				break;
			else {
				--numberOftry;
				if (numberOftry == 0) {
					System.out
							.println("You typed the wrong password 3 times. The system will now close.");
					System.exit(1);
				}
				System.out.println("Wrong pasword. You have " + numberOftry
						+ " More tries. Try Again:");
			}
		} while (!password.equals(getAdminPassword()));
		int choice;
		do {
			System.out.println("To add an user press 1.");
			System.out.println("To remove an user press 2.");
			System.out
					.println("To print out the number of bills in the ATM press 3.");
			System.out.println("To add cash press 4.");
			System.out.println("To shut down the ATM machine press 5.");
			System.out.println("To exit menu press 6.");
			choice = input.nextInt();

			switch (choice) {
			case 1:
				addUser(file);
				break;
			case 2: {
				System.out
						.println("Enter the name of the user you want to remove: ");
				removeUser(input.next());
				break;
			}
			case 3:
				CopyOfATM.printATM();
				break;
			case 4:
				addCash();
				break;

			case 5:
				CopyOfATM.setWorking(false);
				System.out.println("Ugaili ste bankomat.");
				break;
			}
		} while (choice < 4);
	}

}
