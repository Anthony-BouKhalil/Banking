package com.bank.beans;

public class Transaction {

    private int transactionId;
    private String timestamp;
    private String type;
    private double amount;
    private int fromAccId;
    private int toAccId;
    
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getFromAccId() {
		return fromAccId;
	}
	public void setFromAccId(int fromAccId) {
		this.fromAccId = fromAccId;
	}
	public int getToAccId() {
		return toAccId;
	}
	public void setToAccId(int toAccId) {
		this.toAccId = toAccId;
	}
    
    
}