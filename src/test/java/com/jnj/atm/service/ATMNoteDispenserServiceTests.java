/**
 * 
 */
package com.jnj.atm.service;

import static org.assertj.core.api.BDDAssertions.then;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jnj.atm.main.SimpleAtmApplication;

/**
 * @author BALU RAMAMOORTHY
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SimpleAtmApplication.class })
public class ATMNoteDispenserServiceTests {

	@Autowired
	private ATMNoteDispenserService atmNoteDispenserService;
	/**
	 * Test method for {@link com.jnj.atm.service.ATMNoteDispenserService#getNotesDenominationToDispense(java.math.BigDecimal)}.
	 */
	@Test
	public final void testGetNotesDenominationToDispense() {
		
		//Test Case 1 : Valid Withdraw Amount
		BigDecimal withdrawAmount1 = new BigDecimal("60");
		
		Map<Integer, Integer> expectedDenominations1 = new LinkedHashMap<>();
		expectedDenominations1.put(50, 1);
		expectedDenominations1.put(20, 0);
		expectedDenominations1.put(10, 1);
		expectedDenominations1.put(5, 0);
		
		Map<Integer, Integer> actualDenominations1 = atmNoteDispenserService.getNotesDenominationToDispense(withdrawAmount1);
		then(actualDenominations1).isEqualTo(expectedDenominations1);
		
		//Test Case 2 : Zero Withdraw Amount
		BigDecimal withdrawAmount2 = new BigDecimal("0");
		
		Map<Integer, Integer> expectedDenominations2 = new LinkedHashMap<>();
		expectedDenominations2.put(50, 0);
		expectedDenominations2.put(20, 0);
		expectedDenominations2.put(10, 0);
		expectedDenominations2.put(5, 0);
		
		Map<Integer, Integer> actualDenominations2 = atmNoteDispenserService.getNotesDenominationToDispense(withdrawAmount2);
		then(actualDenominations2).isEqualTo(expectedDenominations2);
		
	}

	/**
	 * Test method for {@link com.jnj.atm.service.ATMNoteDispenserService#isValidAmountToDispense(java.math.BigDecimal)}.
	 */
	@Test
	public final void testIsValidAmountToDispense() {
		//Test Case 1:
		BigDecimal withdrawAmount1 = new BigDecimal("185");
		boolean isValidAmount1 = atmNoteDispenserService.isValidAmountToDispense(withdrawAmount1);
		then(isValidAmount1).isTrue();
		
		//Test Case 2:
		BigDecimal withdrawAmount2 = new BigDecimal("101");
		boolean isValidAmount2 = atmNoteDispenserService.isValidAmountToDispense(withdrawAmount2);
		then(isValidAmount2).isFalse();
	}

	/**
	 * Test method for {@link com.jnj.atm.service.ATMNoteDispenserService#updateATMNoteDispenser(java.math.BigDecimal)}.
	 */
	@Test
	public final void testUpdateATMNoteDispenser() {
		// TODO will do this later
	}

	/**
	 * Test method for {@link com.jnj.atm.service.ATMNoteDispenserService#loadATMMoney(java.util.Map)}.
	 */
	@Test
	public final void testLoadATMMoney() {
		// TODO will do this later
	}

}
