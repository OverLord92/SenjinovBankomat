package paket;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
	
	// data filds korisnika
	private String userName;
	private String passWord;
	private double balance;

	public User() {

	}

	// konstruktor
	public User(String userName, String password, double balance) {
		
		this.userName = userName;
		this.passWord = password;
		this.balance = balance;

	}

	// geteri i seteri za podatke korisnika
	public double getBalance() {
		return this.balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

}


// klasa za rad sa batom podataka
class UserBase {
	
	private static ArrayList<User> userBase = new ArrayList<>(); //Lista korisnika

	// geter za userBAse
	public static ArrayList getUserBase() {
		return userBase;
	}
	
	/** Metoda koja cita podatke korisnika s fajla */
	public static void scanUsers(File file) {
		try {
			Scanner input = new Scanner(file);
				
			// skeniramo korisnike i unosimo ih u userBase listu
			while(input.hasNext()){
				UserBase.getUserBase().add(new User(input.next(), input.next(), input.nextDouble()));
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/** Metoda koja stampa korisnike na fajl */
	public static void printUsers(File file) throws FileNotFoundException{
		
		Scanner input = new Scanner(file);
		
		PrintWriter wr = null;
			wr = new PrintWriter(new FileOutputStream(file));
			
			// nakon zavrsetka rada bankomata printamo bazu korisnika na fajl
			for(int i = 0; i < userBase.size(); i++){
        
			User user = (User)userBase.get(i);	
				wr.append(user.getUserName() + " " + user.getPassWord() + " " + user.getBalance() + "\n");

			} 
			wr.close();
		
	}

	
	/** Metoda koja vraca korisnika pomocu proslieđenog userName-a */
	public static User getUser(String userName) {
		
		boolean status = false;
		
		// metoda vraca null ukoliko nema korisnika pod tim imenom
		User user = null;
		
		for (User user1: userBase) {
			user = user1;
			if (userName.equals(user.getUserName())) {
				
				// ako je nađen korisnik pod tim imenom izlazimo iz petlje
				status = true;
				break;
			}
		}
		if (status) {
			return user;
		} else {
			return null;
		}
	}
	
	/** Metoda koja provjerava da li postoji korisnik s odre�enim korisni�kim imenom */
	public static boolean accountExists(String userName) {
		
		boolean accountExists = false;
		
		if (getUser(userName) != null) {
			return true;
		} else {
			return false;
		}
	}

	/** Metoda koja provjerava da li je sifra ispravna */
	public static boolean passWordIsValid(String userName, String password) {
		
		boolean passwordIsValid = false;
		User user = getUser(userName);
		
		// provjeravamo da li je sifra validna
		if (password.equals(user.getPassWord())) {
			passwordIsValid = true;
		}
		return passwordIsValid;
	}

	/** Metoda koja stampa listu korisnika */
	public static void printInfo() {
		for (int i = 0; i < userBase.size(); i++) {
			System.out.println(userBase.get(i).getUserName() + " "
					+ userBase.get(i).getPassWord() + " "
					+ userBase.get(i).getBalance());
		}
		System.out.println("\n");
	}
	
	
	/** Metoda koja pokrece korisnicki meni */
	public static void userMenu(String userName){
		
		Scanner input = new Scanner(System.in);
		int choice; 
		
	
		String password;
		int numberOftry = 3;
		
		// pitamo korisnika da unese sifru
		do{
			System.out.print("Enter password: ");
		 password = input.next();
		 
		 // ako je sifra validna izlazimo iz petlje
		 if(passWordIsValid(userName, password)) break;
		 
		 else{
			 --numberOftry;
			 if(numberOftry == 0){
				 System.out.println("You typed the wrong password 3 times. The system will now close.");
				 System.exit(1);
			 }
			 System.out.println("Wrong pasword. You have " + numberOftry + " More tries. Try Again:");
		 }
		}while(!passWordIsValid(userName, password));
		
		
		// printanje korisničkog menija
		do{
			System.out.println("\n  To get your balance press 1.");
			System.out.println("  To withdraw money press 2.");
			System.out.println("  To exit menu press 3.\n");
			System.out.print("Your choice: ");
			choice = input.nextInt();
			
			if(choice == 1) 
			System.out.println("Your balance is: " + getUser(userName).getBalance());
			if(choice == 2) Bankomat.withdraw(userName);
		}while(choice != 3);
	}
}
