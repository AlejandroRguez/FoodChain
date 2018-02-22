package blockchain;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;

import logging.LogManager;

public class Account {
	
	 String address;
	 PrivateKey privateKey;
	 PublicKey publicKey;
	 double amount;
	 
	
	 public Account (double amount) {
		MessageDigest m = getMessageDigest();
		KeyPair keyPair = getKeyPair();
		this.privateKey = keyPair.getPrivate();
		this.publicKey = keyPair.getPublic();
		this.address =  m.digest(this.publicKey.toString().getBytes(StandardCharsets.UTF_8)).toString(); 
		if(amount < 0) {
			LogManager.write(Level.SEVERE, "Invalid count creation attempt");
		}
		else this.amount = amount;
	}
	 
	private KeyPair getKeyPair() {
		try {
			return KeyPairGenerator.getInstance("SHA-256").generateKeyPair();
		} catch (NoSuchAlgorithmException e) {
			LogManager.write(Level.SEVERE, "Couldn�t get MessageDigest Instance");
			return null;
		}
	}

	private MessageDigest getMessageDigest() {
		try {
			return MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			LogManager.write(Level.SEVERE, "Couldn�t get MessageDigest Instance");
			return null;
		}
	}

	public String getAddress() {
		return address;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public void send(Account receiver, double amount) {
		Transaction t = new Transaction (this, receiver, null, amount);
		if (t.isValid()) { Blockchain.getInstance().addNewTransaction(t); }
		
	}
}
		
	


