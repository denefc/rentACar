package com.turkcell.rentACar.business.constants.messages;

public class BusinessMessages {

    //DATA
    public static final String DATA_LISTED_SUCCESSFULLY = "Data Listed Successfully ";
    public static final String DATA_ADDED_SUCCESSFULLY = "Data Added Successfully ";
    public static final String DATA_GET_SUCCESSFULLY = "Data Get Successfully ";
    public static final String DATA_UPDATED_SUCCESSFULLY = "Data Updated Successfully";
    public static final String DATA_DELETED_SUCCESSFULLY = "Data Deleted successfully ";

    //ADDITIONAL SERVICE
    public static final String ADDITIONAL_SERVICE_NOT_FOUND = "There is no Additional Service with following id: ";
    public static final String ADDITIONAL_SERVICE_ORDERED_BY_RENTAL_CARS = "This Additional Service is Ordered by following rental Id: ";
    public static final String ADDITIONAL_SERVICE_ALREADY_EXISTS="This Additional Service is already exists: ";
    public static final String ORDERED_ADDITIONAL_SERVICE_ALREADY_EXISTS="This OrderedAdditional Service is already exists: ";
    public static final String ORDERED_ADDITIONAL_SERVICE_NOT_FOUND="There is no OrderedAdditional Service with following id: ";


    //BRAND
    public static final String BRAND_ALREADY_EXISTS_ERROR = "Following Brand is already exists: ";
    public static final String BRAND_NOT_FOUND_ERROR = "There is no Brand with following id: ";

    //CAR DAMAGE
    public static final String CAR_DAMAGE_ALREADY_EXISTS = "Following Car Damage is already exists: ";
    public static final String CAR_DAMAGE_NOT_FOUND = "There is no Car Damage with following id: ";

    //RENTAL
    public static final String CAR_NOT_FOUND = "There is no Car with following id: ";
    public static final String CAR_RENT_NOT_FOUND = "There is no Car Rent operation with following id: ";
    public static final String CAR_IS_UNDER_MAINTENANCE_UNKNOWN_RETURN_DATE = "Car is under maintenance and return date is unknown!";
    public static final String CAR_IS_RENTED = "Maintenance is not possible! This car is rented from ";
    public static final String CAR_MAINTENANCE_NOT_FOUND = "There is no car maintenance with following id: ";
    public static final String NO_CHANGES_NO_NEED_TO_UPDATE = "Initial values are completely equal to update values, no need to update!";
    public static final String CAR_UNDER_MAINTENANCE_UNTIL = "This car is under maintenance until: ";
    public static final String RETURN_DATE_CANNOT_BEFORE_CURRENT_DAY = "Return date cannot before current day!";
    public static final String CAR_RENTED_RETURN_UNKNOWN = "Car is rented and return date is not estimated.";
    public static final String CAR_IS_UNDER_MAINTENANCE_RETURN_DATE_KNOWN = "Maintenance update is not possible! This car is rented from ";

    //CAR
    public static final String CAR_ALREADY_EXISTS = "This Car is already exists!";

    public static final String COLOR_NOT_FOUND_ERROR = "There is no Color with following id: ";
    public static final String PAGE_NO_CANNOT_LESS_THAN_ZERO = "Page No can not less than or equal to zero";
    public static final String PAGE_SIZE_CANNOT_LESS_THAN_ZERO = "Page Size can not less than or equal to zero";
    public static final String DAILY_PRICE_CANNOT_LESS_THAN_ZERO = "Daily price can not less than or equal to zero";


    public static final String CITY_NAME_ALREADY_EXISTS = "Following City Name is already exists: ";
    public static final String CITY_ID_ALREADY_EXISTS = "Following City Id is already exists: ";
    public static final String CITY_NOT_FOUND = "There is no City with following id: ";


    public static final String COLOR_ALREADY_EXISTS = "Following Color is already exists: ";


    public static final String CORPORATE_CUSTOMER_NOT_FOUND = "There is no Corporate Customer with following id: ";
    public static final String CORPORATE_CUSTOMER_EMAIL_ALREADY_EXISTS = "Following Email is already exists: ";
    public static final String CORPORATE_CUSTOMER_TAX_NUMBER_ALREADY_EXISTS = "Following Tax Number is already exists: ";
    public static final String CORPORATE_COMPANY_NAME_ALREADY_EXISTS ="Following Company name is already exists: ";


    public static final String CUSTOMER_NOT_FOUND = "There is no Customer with following id: ";
    public static final String CUSTOMER_EMAIL_ALREADY_EXISTS = "Following Email is already exists: ";


    public static final String INDIVIDUAL_CUSTOMER_NOT_FOUND = "There is no Individual Customer with following id: ";
    public static final String INDIVIDUAL_CUSTOMER_EMAIL_ALREADY_EXISTS = "Following Email is already exists: ";
    public static final String INDIVIDUAL_CUSTOMER_NATIONAL_IDENTITY_ALREADY_EXISTS = "Following National Identity is already exists: ";


    public static final String RENTAL_NOT_FOUND = "There is no Rental with following id: ";
    public static final String INVOICE_NOT_FOUND = "There is no Invoice with following id: ";
    public static final String INVOICE_NUMBER_NOT_FOUND = "There is no Invoice Number with following id: ";


    public static final String RENTAL_AND_RETURN_KILOMETER_NOT_VALID = "Return kilometer can not before Rent kilometer.";
    public static final String CAR_IS_UNDER_MAINTENANCE_UNTIL = "This car is under maintenance until: ";
    public static final String RETURN_DATE_CANNOT_BEFORE_RENT_DAY = "Return date cannot before rent day!";
    public static final String CAR_IS_RENTED_UNKNOWN_RETURN_DATE = "Car is Rented and return date is unknown!";
    public static final String CAR_IS_RENTED_RETURN_DATE_KNOWN = "This car Rented from ";
    public static final String CAR_UNDER_MAINTENANCE_RENT_NOT_POSSIBLE_UNTIL = "Rental update is not possible! This car is under maintenance until: ";
    public static final String RETURN_KILOMETER_NOT_VALID_FOR_CAR = "Return kilometer is not valid for following car id: ";


    public static final String USER_NOT_FOUND = "There is no Customer with following id: ";
    public static final String USER_EMAIL_ALREADY_EXISTS = "Following Email is already exists: ";

    public static final String INVALID_PAYMENT = "Invalid payment operation!";
    public static final String INVALID_RENTAL_CAR_INFORMATION = "Invalid rental car information!";
    public static final String PAYMENT_NOT_FOUND = "There is no Payment with following id: ";
    public static final String DELAYED_RETURN_DATE_IS_NOT_VALID = "Delayed return date can not before original return date.";
    public static final String DELAYED_RETURN_KILOMETER_IS_NOT_VALID = "Delayed return Kilometer can not before original return Kilometer.";


    public static final String CARD_NO_ALREADY_EXISTS = "This card no is already exists.";
    public static final String USER_CARD_INFORMATION_ID_NOT_FOUND = "There is no User Card Information with following id: ";


    public static final String CARD_NO_ERROR_MESSAGE = "length must be 16 and all digits have to be an integer";
    public static final String CARD_HOLDER_ERROR_MESSAGE = "Card holder input have to consist of letters and size have to between 5 and 50!";
    public static final String CARD_EXPIRATION_MONTH_ERROR_MESSAGE = "Expiration month have to between 1 and 12.";
    public static final String CARD_EXPIRATION_YEAR_ERROR_MESSAGE = "Expiration year have to between 2022 and 2032.";
    public static final String CARD_CVV_ERROR_MESSAGE = "CVV number have to between 100 and 999.";


    public static final String FIRST_NAME_ERROR_MESSAGE = "First Name input have to consist of letters and size have to between 5 and 50!";
    public static final String LAST_NAME_ERROR_MESSAGE = "Last Name input have to consist of letters and size have to between 5 and 50!";
    public static final String NATIONAL_IDENTITY_ERROR_MESSAGE = "National Identity length must be 11 and all digits have to be an integer";


   // public static final String COMPANY_NAME_ERROR_MESSAGE = "Company Name input have to consist of letters, numbers and size have to between 5 and 50!";
    public static final String TAX_NUMBER_ERROR_MESSAGE = "Tax Number length must be 10 and all digits have to be an integer";


    public static final String COLOR_NAME_ERROR_MESSAGE = "Color Name input have to consist of letters, numbers and size have to between 5 and 50!";


    public static final String CAR_DESCRIPTION_ERROR_MESSAGE = "Car Description input have to consist of letters, numbers and size have to between 2 and 100!";


    public static final String CAR_MAINTENANCE_DESCRIPTION_ERROR_MESSAGE = "Car Maintenance Description input have to consist of letters, numbers and size have to between 2 and 100!";


    public static final String CAR_DAMAGE_DESCRIPTION_ERROR_MESSAGE = "Car Damage Description input have to consist of letters, numbers and size have to between 2 and 100!";


    public static final String BRAND_NAME_ERROR_MESSAGE = "Brand Name input have to consist of letters, numbers and size have to between 2 and 50!";


    public static final String ADDITIONAL_SERVICE_NAME_ERROR_MESSAGE = "Additional Service Name input have to consist of letters, numbers and size have to between 2 and 50!";

    public static final String SORT_PARAMETERS_NOT_AVAILABLE_ERROR_MESSAGE="Select available sort options ! ";
}
