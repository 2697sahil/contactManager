package com.singh.sahil.contactManager.utils;

import com.singh.sahil.contactManager.constant.ContactsConstant;
import com.singh.sahil.contactManager.exception.InvalidRequestException;
import com.singh.sahil.contactManager.openapi.model.ContactDTO;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ContactUtils {

    private static final String EMAIL_PATTERN =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);

    private static final String PHONE_NUMBER_PATTERN = "^[+]?[0-9]{10,13}$";

    private static final Pattern phonePattern = Pattern.compile(PHONE_NUMBER_PATTERN);

    private ContactUtils() {
    }

    private static boolean isValidEmail(String email) {
        if (StringUtils.isEmpty(email)) {
            throw new InvalidRequestException(ContactsConstant.Message.INVALID_EMAIL_ERR_MSG);
        }
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (StringUtils.isEmpty(phoneNumber)) {
            throw new InvalidRequestException(ContactsConstant.Message.INVALID_PHONE_ERR_MSG);
        }
        Matcher matcher = phonePattern.matcher(phoneNumber);
        return matcher.matches();
    }

    /**
     * Validates contact details.
     *
     * @param contactDTO
     * @return {@link Boolean}
     */
    public static boolean isContactValid(ContactDTO contactDTO) {
        validateContactNotEmpty(contactDTO);
        if (!isValidEmail(contactDTO.getEmail())) {
            throw new InvalidRequestException(ContactsConstant.Message.INVALID_EMAIL_ERR_MSG);
        }
        if (!isValidPhoneNumber(contactDTO.getPhoneNumber())) {
            throw new InvalidRequestException(ContactsConstant.Message.INVALID_PHONE_ERR_MSG);
        }
        return true;
    }

    public static void validateContactNotEmpty(ContactDTO contactDTO) {
        if (ObjectUtils.isEmpty(contactDTO)
                || StringUtils.isAnyEmpty(contactDTO.getFirstName(), contactDTO.getLastName(), contactDTO.getEmail(),
                contactDTO.getPhoneNumber())) {
            throw new InvalidRequestException(ContactsConstant.Message.NOT_NULL_CONTACT_DETAILS_ERR_MSG);
        }
    }

    public static void validateContactIdNotEmpty(String contactId) {
        if (StringUtils.isEmpty(contactId)) {
            throw new InvalidRequestException(ContactsConstant.Message.INVALID_CONTACT_ID_ERR_MSG);
        }
    }

}
