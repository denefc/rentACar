package com.turkcell.rentACar.business.outServices;


import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ZiraatBankManager  {
    public boolean makePayment(String cardHolderName,int cardNo,int cvv){

        return true;
    }

}
