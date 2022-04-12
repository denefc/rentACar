package com.turkcell.rentACar.business.requests.createRequests;

import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest {

    @Pattern(regexp = "^[0-9]{16}", message = BusinessMessages.CARD_NO_ERROR_MESSAGE)
    private String cardNo;

    @Pattern(regexp = "^[abcçdefgğhıijklmnoöprsştuüvwqyzABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVWQYZ ]{5,50}",
            message = BusinessMessages.CARD_HOLDER_ERROR_MESSAGE)
    private String cardHolder;

   @Min(value = 1,message = BusinessMessages.CARD_EXPIRATION_MONTH_ERROR_MESSAGE)
   @Max(value = 12,message = BusinessMessages.CARD_EXPIRATION_MONTH_ERROR_MESSAGE )
    private int expirationMonth;

    @Min(value = 2021,message = BusinessMessages.CARD_EXPIRATION_YEAR_ERROR_MESSAGE)
    private int expirationYear;

    @Min(value = 100,message = BusinessMessages.CARD_CVV_ERROR_MESSAGE)
    @Max(value = 999,message = BusinessMessages.CARD_CVV_ERROR_MESSAGE)
    private int cvv;

}
