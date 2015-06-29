package paket;

import java.util.Scanner;

public class CopyOfATM {
	private static boolean isWorking = true;
	private static int novcanica100 = 10;
	private static int novcanica50 = 20;
	private static int novcanica20 = 30;
	private static int novcanica10 = 60;

	/** Stampa brojcano stanje novcanica u bankomatu */
	public static void printATM() {
		System.out.println("100: " + novcanica100);
		System.out.println("50: " + novcanica50);
		System.out.println("20: " + novcanica20);
		System.out.println("10: " + novcanica10);
		System.out.println("\n");
	}

	public static boolean isWorking() {
		return isWorking;
	}

	public static void setWorking(boolean isWorking) {
		CopyOfATM.isWorking = isWorking;
	}

	public static int getNovcanica100() {
		return novcanica100;
	}

	public static void setNovcanica100(int NewNovcanica100) {
		novcanica100 = NewNovcanica100;
	}

	public static int getNovcanica50() {
		return novcanica50;
	}

	public static void setNovcanica50(int NewNovcanica50) {
		novcanica50 = NewNovcanica50;
	}

	public static int getNovcanica20() {
		return novcanica20;
	}

	public static void setNovcanica20(int NewNovcanica20) {
		novcanica20 = NewNovcanica20;
	}

	public static int getNovcanica10() {
		return novcanica10;
	}

	public static void setNovcanica10(int NewNovcanica10) {
		novcanica10 = NewNovcanica10;
	}

	/** Metoda koja vraca novcano stanje na bankomatu */
	public static int getATMBalance() {
		return novcanica100 * 100 + novcanica50 * 50 + novcanica20 * 20
				+ novcanica10 * 10;
	}

	/** Metoda za isplatu kojoj proslije�ujemo korisni�ko ime */
	public static void withdraw(String userName) {
		Scanner input = new Scanner(System.in);
		// kreiramo objekat user i dodjeljujemo mu vrijednosti objekta �ije smo
		// korisni�ko ime proslijedili u metodu
		User user = UserBase.getUser(userName);
		System.out.println("Enter the amount you want to withdraw: ");
		int amount = input.nextInt();
		if (amount > getATMBalance()) {
			System.out.println("U bankomatu nema dovoljno novca za isplatu.");
		} else if (amount > user.getBalance()) {
			System.out.println("Nemate toliko novaca na ra�unu.");
		} else {
			int brojac100 = 0, brojac50 = 0, brojac20 = 0, brojac10 = 0;
			int isplaceno = 0;
			while (amount >= 100 && brojac100 < novcanica100) {
				amount -= 100;
				brojac100++;
				isplaceno += 100;
			}
			while (amount >= 50 && brojac50 < novcanica50) {
				amount -= 50;
				brojac50++;
				isplaceno += 50;
			}

			while (amount >= 20 && brojac20 < novcanica20) {
				amount -= 20;
				brojac20++;
				isplaceno += 20;
			}
			while (amount >= 10 && brojac10 < novcanica10) {
				amount -= 10;
				brojac10++;
				isplaceno += 10;
			}
			if (amount == 0) {
				novcanica100 -= brojac100;
				novcanica50 -= brojac50;
				novcanica20 -= brojac20;
				novcanica10 -= brojac10;
				user.setBalance(user.getBalance() - isplaceno);
				System.out.println("Dobili  ste: ");
				if (brojac100 > 0)
					System.out.println(" - " + brojac100 + " 100KM novcanica,");
				if (brojac50 > 0)
					System.out.println(" - " + brojac50 + " 50KM novcanica,");
				if (brojac20 > 0)
					System.out.println(" - " + brojac20 + " 20KM novcanica,");
				if (brojac10 > 0)
					System.out.println(" - " + brojac10 + " 10KM novcanica,");
				System.out
						.println("Provjerite iznos. Dodatne reklamacije se ne prihvataju.\n");
			} else {
				System.out.println("Ne mo�emo vam isplatiti tu sumu.\n");
			}

		}
	}
}
