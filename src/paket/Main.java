package paket;

import java.io.File;
import java.util.Scanner;

public abstract class Main {

	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
		File file = new File("bazaKorisnika.txt");
		File fajl = new File("evidencija.txt"); //privremeni fajl dok ispravljem greske
		// TODO Auto-generated method stub
		try {
			do {
				UserBase.scanUsers(fajl); // nemoj zaboraviti vratiti ovo fajl na file!!
				System.out.print("Enter your username: ");
				String korisnik = input.next();

				if (korisnik.equals(Admin.getAdminName())) {
					Admin.adminMenu(file);
				} else if (UserBase.accountExists(korisnik)) {
					UserBase.userMenu(korisnik);
				}

				System.out.println("Hvala na povjerenju.");
				UserBase.printUsers(file);
			} while (CopyOfATM.isWorking());
		} catch (Exception ex) {
			System.out.println("Greska.");
		}

	}
}
