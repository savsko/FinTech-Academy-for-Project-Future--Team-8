package gr.codelearn.controller;

import gr.codelearn.domain.Account;
import gr.codelearn.service.AccountService;
import gr.codelearn.service.FeederService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("api")
public class PaymentController {

    private AccountService accountService;
    private FeederService feederService;

    @PostMapping("feeder")
    public boolean feederEndpoint(@RequestBody Map<String, Object> payload) {
        feederService.feederRequest(payload);
        return true;
    }

    @GetMapping("accounts")
    public List<Account> findAllAccounts() {
        // for testing purposes
        return accountService.findAll();
    }


}

