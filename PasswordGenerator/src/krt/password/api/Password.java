package krt.password.api;

import krt.utility.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class Password {
	private int minLength = 6;
	private int minNumber = 1;
	private int minLowerCase = 2;
	private int minUpperCase = 2;
	private int minSpecial = 1;
	
	private int maxLength = 12;
	private int maxNumber = 2;
	private int maxLowerCase = 4;
	private int maxUpperCase = 4;
	private int maxSpecial = 2;
	
	private Character[] numbers = {'0','1','2','3','4','5','6','7','8','9'};
	private Character[] lowercases = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	private Character[] uppercases = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	private Character[] specials = {'~','!','@','#','$','%','^','&','*','(',')','_','-','+','=','{','}','[',']','|',';',':','<','>',',','.','?'};
	
	private int numLength = 0;
	private int numNumber = 0;
	private int numLowerCase = 0;
	private int numUpperCase = 0;
	private int numSpecial = 0;
	
	private ArrayList<Character> passChars;
	private Random random = new Random();
	
	private StringBuilder password;

	public Password() {
		password = new StringBuilder();
	}

	private boolean ValidatePassword() {
		if ((password.length() < minLength) || (password.length() > maxLength)) {
			return false;
		}

		int count = Strings.countOccurrences(password, numbers);
		if ((count < minNumber) || (count > maxNumber)) {
			return false;
		}

		count = Strings.countOccurrences(password, specials);
		if ((count < minSpecial) || (count > maxSpecial)) {
			return false;
		}
		
		count = Strings.countOccurrences(password, lowercases);
		if ((count < minLowerCase) || (count > maxLowerCase)) {
			return false;
		}
		
		count = Strings.countOccurrences(password, uppercases);
		if ((count < minUpperCase) || (count > maxUpperCase)) {
			return false;
		}

		return true;
	}
	
	private void getPossibleChars() {
		passChars = new ArrayList<Character>();
		
		if (numNumber < maxNumber) {
			Collection<Character> collection = Arrays.asList(numbers);
			passChars.addAll(collection);
		}

		if (numSpecial < maxSpecial) {
			Collection<Character> collection = Arrays.asList(specials);
			passChars.addAll(collection);
		}
		
		if (numLowerCase < maxLowerCase) {
			Collection<Character> collection = Arrays.asList(lowercases);
			passChars.addAll(collection);
		}
		
		if (numUpperCase < maxUpperCase) {
			Collection<Character> collection = Arrays.asList(uppercases);
			passChars.addAll(collection);
		}
	}
	
	private void generatePassword() {
		getPossibleChars();
		int size = passChars.size();
		numLength = minLength + random.nextInt(maxLength - minLength);

		for (int i = 0; i < numLength; i++) {
			int pos = random.nextInt(size);
			password.append(passChars.get(pos));
		}
		
		return;
	}
	
	public void generate() {
		while (!ValidatePassword()) {
			generatePassword();
		}
	}
	
	public void setMinNumber(int minNumber) {
		this.minNumber = minNumber;
	}

	public void setMinLowerCase(int minLowerCase) {
		this.minLowerCase = minLowerCase;
	}

	public void setMinUpperCase(int minUpperCase) {
		this.minUpperCase = minUpperCase;
	}

	public void setMinSpecial(int minSpecial) {
		this.minSpecial = minSpecial;
	}

	public void setMaxNumber(int maxNumber) {
		this.maxNumber = maxNumber;
	}

	public void setMaxLowerCase(int maxLowerCase) {
		this.maxLowerCase = maxLowerCase;
	}

	public void setMaxUpperCase(int maxUpperCase) {
		this.maxUpperCase = maxUpperCase;
	}

	public void setMaxSpecial(int maxSpecial) {
		this.maxSpecial = maxSpecial;
	}
	
	public String toString() {
		try {
			generate();
		}
		catch (Exception e) {
		}
	
		return password.toString();
	}
}
