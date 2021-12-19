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

        payload.put("feeAmount", BigDecimal.ZERO);
        payload.put("feeCurrency", "EUR");
        return payload;
    }
}
