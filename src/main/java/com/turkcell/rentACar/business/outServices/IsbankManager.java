package com.turkcell.rentACar.business.outServices;


import org.springframework.stereotype.Service;

@Service
public class IsbankManager{

    public boolean makePayment(String cardNo,String cardHolderName,int expirationMonth,int expirationYear,int cvv){

        return true;
    }

}
