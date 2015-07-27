package paket;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Admin extends User {
	
	// korisničko ime i šifr asu hardkodovani
	private static final String ADMIN_NAME = "burek";
	private static final String ADMIN_PASSWORD = "1234";

	// geteri za ime i sifru administratora
	public static String getAdminName() {
		return ADMIN_NAME;
	}

	public static String getAdminPassword() {
		return ADMIN_PASSWORD;
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
			
			// provjeravamo da li vec postoji korisnik pod tim imenom
			if (UserBase.accountExists(name))
				System.out
						.print("An account under this name already exits.\nTry again.");
			
			// petlja se vrti sve dok ne unesemo ime koje ne nalazi u bazi
		} while (UserBase.accountExists(name));

		// petlja koja provjerava validnost šifre koju želimo dodijeliti korisniku
		do {
			System.out.println("Enter password: ");

			password = input.next();
			if (!passwordCanBeUsed(password))
				System.out
						.print("Password must consist of 4 digits.\nTry Again.");
			
		} while (!passwordCanBeUsed(password));
		
		// unosimo stanje racuna korisnika
		System.out.println("Enter balance: ");
		balance = input.nextInt();
		
		// unosimo novog korisnika u bazu podataka
		UserBase.getUserBase().add(new User(name, password, balance));
		
		// poruka za admina koja potvršuje da je korisnik uspješno dodan
		System.out.println("\nDodali ste korisnika " + name + ".");
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
		
		// provjeravamo da li postoji korisnik kojeg želimo izbrisati
		if (UserBase.accountExists(username)) {
			for (int i = 0; i < UserBase.getUserBase().size(); i++) {
				User user = (User) UserBase.getUserBase().get(i);
				if (user.getUserName().equals(username)) {
					System.out.println("Dou you want to delete " + username
							+ "? Answer with Y or N.");

					// od admina se traži da potvrdi brisanje
					if (input.next().toUpperCase().equals("Y")) {
						UserBase.getUserBase().remove(i);
						System.out.println("You deleted " + username + ".");
					} else
						System.out.println("You didnt delete" + username + ".");
				}
			}
		} else
			// u koliko ne postoji korisnik pod imenom username printa poruku..
			System.out.println("Acount doesnt exist.");

	}

	/** Metoda koja koja provjerava da li je password validan */
	public static boolean passwordCanBeUsed(String password) {
		
		boolean digitPassword = true;
		
		// sifra se mora sastojati od 4 broja 
		if(password.length() != 4) return false;
		
		// sifra mora biti sastavljena isključivo od brojeva
		for (int i = 0; i < password.length(); i++) {
			if (!Character.isDigit(password.charAt(i))) {
				digitPassword = false;
				break;
			}
		}
		return digitPassword;
	}

	/** Metoda koja adminu omogucava dodavanje novcanica */
	public static void addCash() {
		
		Scanner input = new Scanner(System.in);
		
		//
		boolean validCashAdd = false;
		int addAmount, fullAmount;
		
		// poruka koja se ispisuje ukoliko nije moguće dodati toliko novačanica
		String message = "You can enter maximum %2d bills\nTry Again: ";

		// petlja za novcanice od 100
		do {
			System.out
					.println("Enter the number of 100 bills you want to add: ");
			addAmount = input.nextInt();
			fullAmount = addAmount + Bankomat.getNovcanica100();
			
			// broj novcanica pojedinog iznosa je ogranićen na 100 
			if (fullAmount <= 100) {
				
				// ukoliko je dodavanje validno setujemo broj novcanica
				Bankomat.setNovcanica100(fullAmount);
				System.out.println("You added " + addAmount
						+ " 100 bills to the ATM.");
				validCashAdd = true;
			} 
			// poruka adminu ukoliko nije moguće dodati toliko novcanica
			else {
				System.out.printf(message, (100 - Bankomat.getNovcanica100()));
			}
		} while (!validCashAdd);

		// vracamo validCashAdd na false
		validCashAdd = false;
		
		// petlja za novcanice od 50
		do {
			System.out
					.println("Enter the number of 50 bills you want to add: ");
			addAmount = input.nextInt();
			fullAmount = addAmount + Bankomat.getNovcanica50();
			
			// provjeravam oda li je moguce dodati željeni iznos novcanica
			if (fullAmount <= 100) {
				Bankomat.setNovcanica50(fullAmount);
				System.out.println("You added " + addAmount
						+ " 50 bills to the ATM.");
				validCashAdd = true;
			} 
			// formatirana poruka koja se printa ukoliko nije moguće dodati novcanice
			else {
				System.out.printf(message, (100 - Bankomat.getNovcanica50()));
			}
		} while (!validCashAdd);

		// vracamo validCashAdd na false
		validCashAdd = false;
		
		// petlja za novcanice od 20
		do {
			System.out
					.println("Enter the number of 20 bills you want to add: ");
			addAmount = input.nextInt();
			fullAmount = addAmount + Bankomat.getNovcanica20();
			if (fullAmount <= 100) {
				Bankomat.setNovcanica20(fullAmount);
				System.out.println("You added " + addAmount
						+ " 20 bills to the ATM.");
				validCashAdd = true;
			} else {
				int iznos = 100 - Bankomat.getNovcanica20();
				System.out.printf(message, iznos);
			}
		} while (!validCashAdd);

		// vracamo validCashAdd na false
		// i radimo sve isto i za novcanice od 10
		validCashAdd = false;
		do {
			System.out
					.println("Enter the number of 10 bills you want to add: ");
			addAmount = input.nextInt();
			fullAmount = addAmount + Bankomat.getNovcanica10();
			
			// provjeravamo da li je moguce dodati toliko novcanica
			if (fullAmount <= 100) {
				
				Bankomat.setNovcanica10(fullAmount);
				System.out.println("You added " + addAmount
						+ " 10 bills to the ATM.");
				validCashAdd = true;
			} else {
				System.out.printf(message, (100 - Bankomat.getNovcanica10()));
			}
		} while (!validCashAdd);
	}

	/** Metoda kojoj se pokrece administratorski meni */
	public static void adminMenu(File file) {
		
		Scanner input = new Scanner(System.in);
		String password;
		
		// broj mogucih pokusaja da se admin ispravno uloguje
		int numberOftry = 3;
		
		// koja ptovjerava validnost sifre i imena admina
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
		
		// petlja koja u kojoj admin bira koju zadatak zeli uraditi 
		int choice;
		do {
			
			// printanje menia
			System.out.println("\n  To add an user press 1.");
			System.out.println("  To remove an user press 2.");
			System.out
					.println("  To print out the number of bills in the ATM press 3.");
			System.out.println("  To add cash press 4.");
			System.out.println("  To shut down the ATM machine press 5.");
			System.out.println("  To exit menu press 6.\n");
			System.out.println("Your choice: ");
			
			// admin unosi izbor
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
				Bankomat.printATM();
				break;
			case 4:
				addCash();
				break;

			case 5:
				Bankomat.setWorking(false);
				System.out.println("Ugaili ste bankomat.");
				break;
			}
			// ukoliko admin upise 5 bankomat prestaje s radom
		} while (choice < 4);
	}

}
