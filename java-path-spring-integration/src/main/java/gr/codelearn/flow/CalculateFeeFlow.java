package gr.codelearn.flow;

import com.google.common.base.Strings;
import gr.codelearn.service.AccountLookupService;
import gr.codelearn.service.CalculateFees;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

import java.util.Map;

@Configuration
@EnableIntegration
@AllArgsConstructor
public class CalculateFeeFlow {
    private CalculateFees calculateFees;

    @Bean
    public MessageChannel feeChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow calculateFeesInternalFlow(MessageChannel accountsLookupChannel, MessageChannel errorChannel) {
        return IntegrationFlows
                .from(feeChannel())
                .transform(calculateFees::calculate)
                .<Map<String, Object>, Boolean>route(m -> Strings.isNullOrEmpty((String) m.get("errorMessage")), message -> message
                        .channelMapping(true, accountsLookupChannel)
                        .channelMapping(false, errorChannel)
                )
                .get();

    }
}
