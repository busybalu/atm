package com.jnj.atm.controller;

import java.util.Date;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jnj.atm.exceptions.ATMOutOfCashException;
import com.jnj.atm.exceptions.ATMOutOfExpectedCashException;
import com.jnj.atm.exceptions.AccountNotFoundException;
import com.jnj.atm.exceptions.InsufficientFundsException;
import com.jnj.atm.exceptions.InvalidAccountException;
import com.jnj.atm.exceptions.InvalidAmountException;
import com.jnj.atm.model.ErrorDetails;
import com.jnj.atm.msg.ErrorMessages;

/**
 * @author BALU RAMAMOORTHY
 *
 */
@ControllerAdvice
@RestController
public class ATMExceptionHandler extends ResponseEntityExceptionHandler {
	/**
	 * This method respond with the Invalid Account Error when the ATM user request
	 * account details for invalid account.
	 * 
	 * @param ex
	 *            AccountNotFoundException
	 * @param request
	 *            WebRequest
	 * @return ResponseEntity
	 */
	@ExceptionHandler(AccountNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleAccountNotFoundException(AccountNotFoundException ex,
			WebRequest request) {

		ErrorDetails errorDetails = createErrorDetail(ErrorMessages.INVALID_ACCT);

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	/**
	 * This method respond with the Insufficient Funds Error when the ATM user
	 * requested amount which is greater than his balance.
	 * 
	 * @param ex
	 *            InsufficientFundsException
	 * @param request
	 *            WebRequest
	 * @return ResponseEntity
	 */
	@ExceptionHandler(InsufficientFundsException.class)
	public final ResponseEntity<ErrorDetails> handleInsufficientFundsException(InsufficientFundsException ex,
			WebRequest request) {

		ErrorDetails errorDetails = createErrorDetail(ErrorMessages.INSUFFICIENT_FUNDS);

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	/**
	 * This method respond with the Invalid Amount Error when the ATM user requested
	 * amount which is negative, Zero or Invalid.
	 * 
	 * @param ex
	 *            InvalidAmountException
	 * @param request
	 *            WebRequest
	 * @return ResponseEntity
	 */
	@ExceptionHandler(InvalidAmountException.class)
	public final ResponseEntity<ErrorDetails> handleInvalidAmountException(InvalidAmountException ex,
			WebRequest request) {

		ErrorDetails errorDetails = createErrorDetail(ErrorMessages.INVALID_AMOUNT);

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	/**
	 * This method respond with ATM out of Expected Cash Error when the user
	 * requested for the Amount which is insufficient to dispense from the ATM.
	 * 
	 * @param ex
	 *            ATMOutOfExpectedCashException
	 * @param request
	 *            WebRequest
	 * @return ResponseEntity
	 */
	@ExceptionHandler(ATMOutOfExpectedCashException.class)
	public final ResponseEntity<ErrorDetails> handleATMOutOfExpectedCashException(ATMOutOfExpectedCashException ex,
			WebRequest request) {

		ErrorDetails errorDetails = createErrorDetail(ErrorMessages.ATM_OUT_OF_EXPECTED_CASH);

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	/**
	 * This method respond with ATM out of Cash Error when the ATM is out of cash
	 * during the cash withdrawal from ATM.
	 * 
	 * @param ex
	 *            ATMOutOfCashException
	 * @param request
	 *            WebRequest
	 * @return ResponseEntity
	 */
	@ExceptionHandler(ATMOutOfCashException.class)
	public final ResponseEntity<ErrorDetails> handleATMOutOfCashException(ATMOutOfCashException ex,
			WebRequest request) {

		ErrorDetails errorDetails = createErrorDetail(ErrorMessages.ATM_OUT_OF_CASH);

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	/**
	 * 
	 * @param ex
	 *            InvalidAccountException
	 * @param request
	 *            WebRequest
	 * @return ResponseEntity
	 */
	@ExceptionHandler(InvalidAccountException.class)
	public final ResponseEntity<ErrorDetails> handleInvalidAccountException(InvalidAccountException ex,
			WebRequest request) {

		ErrorDetails errorDetails = createErrorDetail(ErrorMessages.INVALID_ACCT_NUMBER);

		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	/**
	 * This method is a common method to prepare the Error Detail based on the give
	 * error message.
	 * 
	 * @param errorMsg
	 *            ErrorMessages
	 * @return ErrorDetails
	 */
	private ErrorDetails createErrorDetail(ErrorMessages errorMsg) {
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setTxnID(UUID.randomUUID().toString());
		errorDetails.setTimestamp(new Date());
		errorDetails.setErrorCode(errorMsg.getCode());
		errorDetails.setErrorReason(errorMsg.getDescription());
		return errorDetails;
	}
}
