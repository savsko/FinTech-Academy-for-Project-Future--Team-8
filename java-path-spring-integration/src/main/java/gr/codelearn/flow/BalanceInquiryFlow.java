package gr.codelearn.flow;

import com.google.common.base.Strings;
import gr.codelearn.service.BalanceInquiryService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.MessageChannel;

import java.util.Map;

import static gr.codelearn.listener.RabbitMQListener.COMMON_CHANNEL; //credit flow
import static gr.codelearn.listener.RabbitMQListener.WALLET_CHANNEL;



@Configuration
@EnableIntegration
@AllArgsConstructor
public class BalanceInquiryFlow {

    private BalanceInquiryService balanceInquiryService;

    @Bean
    public MessageChannel balanceInquiryChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow balanceInquiryInternalFlow(MessageChannel postingChannel, MessageChannel errorChannel) {
        return IntegrationFlows
                .from(balanceInquiryChannel())
                //θέλω transform και για checkWaletTransactionFinancially ???
                //.transform(balanceInquiryService::checkWaletTransactionFinancially)
                .<Map<Boolean, Object>, Boolean>route(m -> WALLET_CHANNEL.equals(m.get("channel")), message -> message //αμα αυτό είναι true checkwalet
                        //create two channels
                        .subFlowMapping(true, wf -> wf.transform(balanceInquiryService::checkWalletTransactionFinancially))
                        .subFlowMapping(false, cf-> cf.transform(balanceInquiryService::checkTransactionFinancially))
                        .defaultOutputToParentFlow()
                )
                .<Map<Boolean, Object>, Boolean>route(m -> Strings.isNullOrEmpty((String) m.get("errorMessage")), message -> message
                        .channelMapping(true, postingChannel)
                        .channelMapping(false, errorChannel)
                )
                .get();


    }
}
