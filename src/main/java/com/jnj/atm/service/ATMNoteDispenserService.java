package com.jnj.atm.service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jnj.atm.dao.ATMNoteDispenserDAO;
import com.jnj.atm.model.ATMNotesDispenser;

/**
 * @author BALU RAMAMOORTHY
 *
 */
@Repository
public class ATMNoteDispenserService {

	private static final int FIFTY = 50;
	private static final int TWENTY = 20;
	private static final int TEN = 10;
	private static final int FIVE = 5;

	@Autowired
	private ATMNoteDispenserDAO atmNoteDispenserDAO;

	/**
	 * This service method is to calculates the minimum number of notes for the
	 * given withdrawal Amount
	 * 
	 * @param withdrawAmount
	 *            Withdraw Amount
	 * @return Map
	 */
	public Map<Integer, Integer> getNotesDenominationToDispense(BigDecimal withdrawAmount) {

		Map<Integer, Integer> currentNoteCountMap = new LinkedHashMap<>();
		currentNoteCountMap.put(FIFTY, atmNoteDispenserDAO.getATMNoteDispenser().getNote50Counter());
		currentNoteCountMap.put(TWENTY, atmNoteDispenserDAO.getATMNoteDispenser().getNote20Counter());
		currentNoteCountMap.put(TEN, atmNoteDispenserDAO.getATMNoteDispenser().getNote10Counter());
		currentNoteCountMap.put(FIVE, atmNoteDispenserDAO.getATMNoteDispenser().getNote5Counter());

		Map<Integer, Integer> dispenseNoteCountMap = new LinkedHashMap<>();
		dispenseNoteCountMap.put(FIFTY, 0);
		dispenseNoteCountMap.put(TWENTY, 0);
		dispenseNoteCountMap.put(TEN, 0);
		dispenseNoteCountMap.put(FIVE, 0);

		for (Map.Entry<Integer, Integer> entry : currentNoteCountMap.entrySet()) {
			if (withdrawAmount.compareTo(new BigDecimal(entry.getKey())) >= 0 && entry.getValue() > 0) {
				if (withdrawAmount.divide(new BigDecimal(entry.getKey())).intValue() >= entry.getValue()) {
					dispenseNoteCountMap.put(entry.getKey(), entry.getValue());
				} else {
					dispenseNoteCountMap.put(entry.getKey(),
							withdrawAmount.divide(new BigDecimal(entry.getKey())).intValue());
				}
				BigDecimal availedNotesAmount = new BigDecimal(
						dispenseNoteCountMap.get(entry.getKey()) * entry.getKey());
				withdrawAmount = withdrawAmount.subtract(availedNotesAmount);
			}
		}
		return dispenseNoteCountMap;
	}

	/**
	 * This method return true if the requested withdraw Amount is able to dispense
	 * by the ATM. Returns false if the requested withdraw amount is unable to
	 * dispense by the ATM.
	 * 
	 * @param withdrawAmount Withdraw Amount
	 * @return true or false
	 */
	public boolean isValidAmountToDispense(BigDecimal withdrawAmount) {
		boolean isValidAmount = false;
		Map<Integer, Integer> denominationToDispense = getNotesDenominationToDispense(withdrawAmount);
		BigDecimal nearestAmtATMCanDispense = new BigDecimal("0.00");
		for (Map.Entry<Integer, Integer> entry : denominationToDispense.entrySet()) {
			nearestAmtATMCanDispense = nearestAmtATMCanDispense.add(new BigDecimal(entry.getKey() * entry.getValue()));
		}
		if (nearestAmtATMCanDispense.compareTo(withdrawAmount) == 0) {
			isValidAmount = true;
		}

		return isValidAmount;
	}

	/**
	 * This method gets the Count of available Notes in the ATM machine along with
	 * the Total Amount.
	 * 
	 * @return ATMNotesDispenser
	 */
	public ATMNotesDispenser getATMNoteDispenser() {
		return atmNoteDispenserDAO.getATMNoteDispenser();
	}

	/**
	 * This method is to update the ATM after every withdrawal of money.
	 * 
	 * @param withdrawAmount
	 *            Withdraw Amount
	 * @return ATMNotesDispenser
	 */
	public ATMNotesDispenser updateATMNoteDispenser(BigDecimal withdrawAmount) {
		Map<Integer, Integer> denominationToDispense = getNotesDenominationToDispense(withdrawAmount);
		return atmNoteDispenserDAO.updateATMNoteDispenser(denominationToDispense);
	}

	/**
	 * This method is to load given money into the ATM.
	 * 
	 * @param notesToLoad
	 *            Counts of each denomination
	 * @return ATMNotesDispenser
	 */
	public ATMNotesDispenser loadATMMoney(Map<Integer, Integer> notesToLoad) {
		return atmNoteDispenserDAO.loadATMMoney(notesToLoad);
	}

}
