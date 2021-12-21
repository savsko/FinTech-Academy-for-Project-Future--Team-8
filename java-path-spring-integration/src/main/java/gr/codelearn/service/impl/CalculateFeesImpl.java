package gr.codelearn.service.impl;

import gr.codelearn.service.CalculateFees;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class CalculateFeesImpl implements CalculateFees {
    @Override
    public Map<String, Object> calculate(Map<String, Object> payload) {

        String paymentAmountStr = (String) payload.get("paymentAmount");
        BigDecimal paymentAmount = new BigDecimal(paymentAmountStr);
        BigDecimal percentage = new BigDecimal("0.03");
        BigDecimal walletFeeAmount = paymentAmount.multiply(percentage).setScale(2);



        payload.put("feeAmount", walletFeeAmount);
        payload.put("feeCurrency", "EUR");

        return payload;
    }
}
