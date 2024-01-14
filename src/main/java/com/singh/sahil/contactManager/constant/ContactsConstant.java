package com.singh.sahil.contactManager.constant;

public interface ContactsConstant {

    public interface Message {
        String NOT_NULL_CONTACT_DETAILS_ERR_MSG = "Invalid contact details, all fields are mandatory and can't be null";
        String INVALID_EMAIL_ERR_MSG = "Invalid email provided.";
        String INVALID_PHONE_ERR_MSG = "Invalid phone number provided.";
        String JDBC_SAVE_OP_ERR_MSG = "Exception occurred while saving data into db: {}";
        String INTERNAL_SERVER_ERR_MSG = "Internal Server Error, we are on it!";
        String INVALID_CONTACT_ID_ERR_MSG = "Invalid contactId, kindly check & retry.";
        String INVALID_SEARCH_PARAM_ERR_MSG = "Kindly provide firstName or lastName or both to get search results.";
    }

}
