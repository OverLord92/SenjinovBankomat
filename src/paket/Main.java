package paket;

import java.io.File;
import java.util.Scanner;

public abstract class Main {

	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
		
		// fajl s kojeg citamo korisnike
		File file = new File("bazaKorisnika.txt");
		
		try {
			do {
				// skeniramo korisnike
				UserBase.scanUsers(file); 
				
				// korisnik bankomata se uloguje
				System.out.print("Enter your username: ");
				String korisnik = input.next();

				// prvo provjeravamo da li je admin
				// ako jest porecemo aminMenu
				if (korisnik.equals(Admin.getAdminName())) {
					Admin.adminMenu(file);
					} 
				
				// ako nije admin provjeravamo da li korisnik postoji u bazi podataka
				else if (UserBase.accountExists(korisnik)) {
					UserBase.userMenu(korisnik);
				}

				
				System.out.println("Hvala na povjerenju.\n");
				UserBase.printUsers(file);
			} while (Bankomat.isWorking());
		} catch (Exception ex) {
			System.out.println("Greska.");
		}

	}
}
