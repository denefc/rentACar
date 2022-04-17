package com.turkcell.rentACar.business.outServices;


import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ZiraatBankManager  {
    public boolean makePayment(String cardNo,String cardHolderName,int expirationMonth,int expirationYear,int cvv){

        return true;
    }

}
