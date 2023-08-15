package faang.school.paymentservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import faang.school.paymentservice.client.ExchangeRateClient;
import faang.school.paymentservice.dto.exchange.CurrencyApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CurrencyServiceTest {
    @Mock
    private ExchangeRateClient exchangeRateClient;
    @Mock
    private TextToJsonObjectConverter converter;
    @InjectMocks
    private CurrencyService currencyService;

    @Test
    public void testFetchAndSaveCurrencyData() throws JsonProcessingException {
        String responseText = """
                {
                    "Date": "2023-08-12T11:30:00+03:00",
                    "PreviousDate": "2023-08-11T11:30:00+03:00",
                    "PreviousURL": "//www.cbr-xml-daily.ru/archive/2023/08/11/daily_json.js",
                    "Timestamp": "2023-08-14T10:00:00.000+00:00",
                    "Valute": {
                        "AUD": {
                            "CharCode": "AUD",
                            "Value": 64.0503
                        },
                        }
                    }
                }""";

        doReturn(responseText).when(exchangeRateClient).getLatestCurrencyRates();
        doReturn(new CurrencyApiResponse()).when(converter).convert(responseText,
                CurrencyApiResponse.class);

        CurrencyApiResponse response = currencyService.fetchAndSaveCurrencyData();

        assertNotNull(response);
    }
}