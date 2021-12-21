package gr.codelearn.service.impl;

import gr.codelearn.domain.Account;
import gr.codelearn.service.AccountService;
import gr.codelearn.service.BalanceInquiryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.Random;


@Service
@Slf4j
@AllArgsConstructor
public class BalanceInquiryServiceImpl implements BalanceInquiryService {

    private AccountService accountService;

    public Map<String, Object> checkTransactionFinancially(Map<String, Object> payload) {
        log.info("Beginning process to check if transaction is possible financially.");

        // validate debtor
        String debtorIBAN = (String) payload.get("debtorIBAN");
        // supposedly we have already checked if he exists
        Optional<Account> debtorOptional = accountService.findByIban(debtorIBAN);
        Account debtor = debtorOptional.get();

        String paymentAmountStr = (String) payload.get("paymentAmount");
        BigDecimal paymentAmount = new BigDecimal(paymentAmountStr);
        String feeAmountStr = (String) payload.get("feeAmount");
        BigDecimal feeAmount = new BigDecimal(feeAmountStr);

        BigDecimal completeAmount = paymentAmount.add(feeAmount);
        BigDecimal debtorBalance = debtor.getBalance();
        BigDecimal finalBalance = debtorBalance.subtract(completeAmount);


        // if the final balance is below 0
        if (finalBalance.compareTo(BigDecimal.ZERO) < 0) {
            String errorMessage = "Transaction is not possible financially. Debtor does not have enough balance.";
            log.info(errorMessage);
            payload.put("errorMessage", errorMessage);
            payload.put("referralID", null);
            return payload;
        }

        log.info("Process to check if transaction is possible financially has finished successfully.");
        payload.put("referralID", new Random().nextInt());
        return payload;
    }

    @Override
    public Map<String, Object> checkWalletTransactionFinancially(Map<String, Object> payload) {
        log.info("Wallet Beginning process to check if transaction is possible financially.");

        String debtorIBAN = (String) payload.get("debtorIBAN");
        // supposedly we have already checked if he exists
        Optional<Account> debtorOptional = accountService.findByIban(debtorIBAN);
        Account debtor = debtorOptional.get();
        String creditorIBAN = (String) payload.get("creditorIBAN");

        Optional<Account> creditorOptional = accountService.findByIban(creditorIBAN);
        Account creditor = creditorOptional.get();

        String paymentAmountStr = (String) payload.get("paymentAmount");
        BigDecimal feeAmount = (BigDecimal) payload.get("feeAmount");

        BigDecimal paymentAmount = new BigDecimal(paymentAmountStr);
        BigDecimal walletFeeAmount = feeAmount.multiply(BigDecimal.valueOf(0.5));

        BigDecimal WalletAmountDebtor = paymentAmount.add(walletFeeAmount);
        BigDecimal debtorBalance = debtor.getBalance();
        BigDecimal finalBalanceDebtor = debtorBalance.subtract(WalletAmountDebtor);

        //add WalletFeeAmount στον Creditor
        BigDecimal creditorBalance = creditor.getBalance();
        BigDecimal finalBalanceCreditor = creditorBalance.subtract(walletFeeAmount);

        if (finalBalanceDebtor.compareTo(BigDecimal.ZERO) < 0 || finalBalanceCreditor.compareTo(BigDecimal.ZERO)<0) {
            String errorMessage = "Transaction is not possible financially. Debtor and/or Creditor does not have enough balance.";
            log.info(errorMessage);
            payload.put("errorMessage", errorMessage);
            payload.put("referralID", null);
            return payload;
        }

        log.info("Process to check if transaction is possible financially has finished successfully.");
        payload.put("referralID", new Random().nextInt());
        return payload;

    }


}

// Θέλω να κάνω validate εδω μέσα και να το στέλνω στις δύο κλάσεις - inherit
// public  Map<String, Object> validateDebtorAndCreditor(Map<String, Object> payload) {
// validate debtor
//    String debtorIBAN = (String) payload.get("debtorIBAN");
//    String creditorIBAN = (String) payload.get("creditorIBAN");
// supposedly we have already checked if he exists
//    Optional<Account> debtorOptional = accountService.findByIban(debtorIBAN);
//   Account debtor = debtorOptional.get();

//   Optional<Account> creditorOptional = accountService.findByIban(creditorIBAN);
//   Account creditor = creditorOptional.get();

//   return debtor;

// }

