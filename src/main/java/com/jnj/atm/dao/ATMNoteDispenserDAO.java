package com.jnj.atm.dao;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.jnj.atm.model.ATMNotesDispenser;

/**
 * @author BALU RAMAMOORTHY
 *
 */
@Repository
public class ATMNoteDispenserDAO {

	private static final ATMNotesDispenser atmNoteDispenser = new ATMNotesDispenser();
	private static final int FIFTY = 50;
	private static final int TWENTY = 20;
	private static final int TEN = 10;
	private static final int FIVE = 5;

	// This static block is to initialize the Notes to the ATM.
	static {
		initATMNotesDispenser();
	}

	/**
	 * This static method is to initialize the Notes to the ATM.
	 */
	private static void initATMNotesDispenser() {
		atmNoteDispenser.setNote50Counter(20);
		atmNoteDispenser.setNote20Counter(30);
		atmNoteDispenser.setNote10Counter(30);
		atmNoteDispenser.setNote5Counter(20);
		atmNoteDispenser.setTotalAmountInATM(calculateTotalAmountInATM());
	}

	/**
	 * This method is to return current available notes in the ATM and its Total
	 * Amount.
	 * 
	 * @return ATMNotesDispenser
	 */
	public ATMNotesDispenser getATMNoteDispenser() {
		return atmNoteDispenser;
	}

	/**
	 * This method is to Update the ATM Currency Note Counter after withdrawal of
	 * money.
	 * 
	 * @param dispensedNoteCount Counts of Dispensed Notes
	 * @return ATMNotesDispenser
	 */
	public synchronized ATMNotesDispenser updateATMNoteDispenser(Map<Integer, Integer> dispensedNoteCount) {
		atmNoteDispenser.setNote50Counter(
				atmNoteDispenser.getNote50Counter() - getDenominationCount(dispensedNoteCount, FIFTY));
		atmNoteDispenser.setNote20Counter(
				atmNoteDispenser.getNote20Counter() - getDenominationCount(dispensedNoteCount, TWENTY));
		atmNoteDispenser
				.setNote10Counter(atmNoteDispenser.getNote10Counter() - getDenominationCount(dispensedNoteCount, TEN));
		atmNoteDispenser
				.setNote5Counter(atmNoteDispenser.getNote5Counter() - getDenominationCount(dispensedNoteCount, FIVE));
		atmNoteDispenser.setTotalAmountInATM(calculateTotalAmountInATM());
		return atmNoteDispenser;
	}

	/**
	 * This method is to load money to the ATM.
	 * 
	 * @param loadingNoteCount
	 *            Notes to Load
	 * @return ATMNotesDispenser
	 */
	public synchronized ATMNotesDispenser loadATMMoney(Map<Integer, Integer> loadingNoteCount) {
		if (!loadingNoteCount.isEmpty()) {
			atmNoteDispenser.setNote50Counter(
					atmNoteDispenser.getNote50Counter() + getDenominationCount(loadingNoteCount, FIFTY));
			atmNoteDispenser.setNote20Counter(
					atmNoteDispenser.getNote20Counter() + getDenominationCount(loadingNoteCount, TWENTY));
			atmNoteDispenser.setNote10Counter(
					atmNoteDispenser.getNote10Counter() + getDenominationCount(loadingNoteCount, TEN));
			atmNoteDispenser
					.setNote5Counter(atmNoteDispenser.getNote5Counter() + getDenominationCount(loadingNoteCount, FIVE));
			atmNoteDispenser.setTotalAmountInATM(calculateTotalAmountInATM());
		}
		return atmNoteDispenser;
	}

	private int getDenominationCount(Map<Integer, Integer> loadingNoteCount, Integer denomination) {
		return null != loadingNoteCount.get(denomination) ? loadingNoteCount.get(denomination) : 0;
	}

	/**
	 * This method is to calculate Total Amount in the atm at the given point of
	 * time.
	 * 
	 * @return BigDecimal Total Amount in ATM
	 */
	private static BigDecimal calculateTotalAmountInATM() {
		return new BigDecimal(atmNoteDispenser.getNote50Counter() * FIFTY)
				.add(new BigDecimal(atmNoteDispenser.getNote20Counter() * TWENTY))
				.add(new BigDecimal(atmNoteDispenser.getNote10Counter() * TEN))
				.add(new BigDecimal(atmNoteDispenser.getNote5Counter() * FIVE));
	}
}
