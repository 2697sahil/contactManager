package com.singh.sahil.contactManager.service;

import com.singh.sahil.contactManager.constant.ContactsConstant;
import com.singh.sahil.contactManager.entity.ContactEntity;
import com.singh.sahil.contactManager.exception.ContactManagerRuntimeException;
import com.singh.sahil.contactManager.openapi.api.ContactsApi;
import com.singh.sahil.contactManager.openapi.api.ContactsApiDelegate;
import com.singh.sahil.contactManager.openapi.model.ContactDTO;
import com.singh.sahil.contactManager.repository.ContactRepository;
import com.singh.sahil.contactManager.utils.ContactMapper;
import com.singh.sahil.contactManager.utils.ContactUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ContactsService implements ContactsApiDelegate {

    @Autowired
    private ContactMapper contactMapper;

    @Autowired
    private ContactRepository contactRepository;

    /**
     * POST /contacts : Create a new contact
     *
     * @param contactDTO (optional)
     * @return Contact created successfully (status code 201)
     * or Bad request (status code 400)
     * @see ContactsApi#createContact
     */
    @Override
    public ResponseEntity<Void> createContact(ContactDTO contactDTO) {
        //log.info("Request received: {}", contactDTO);
        ContactUtils.isContactValid(contactDTO);
        saveContact(contactDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private void saveContact(ContactDTO contactDTO) {
        ContactEntity contactEntity = contactMapper.mapDTOToEntity(contactDTO);
        try {
            contactRepository.save(contactEntity);
        } catch (Exception e) {
            //log.info(ContactsConstant.Message.JDBC_SAVE_OP_ERR_MSG, e);
            throw new ContactManagerRuntimeException(ContactsConstant.Message.INTERNAL_SERVER_ERR_MSG);
        }
    }

    /**
     * DELETE /contacts/{contactId} : Delete a contact
     *
     * @param contactId ID of the contact to be deleted (required)
     * @return Contact deleted successfully (status code 204)
     * or Contact not found (status code 404)
     * @see ContactsApi#deleteContact
     */
    @Override
    public ResponseEntity<Void> deleteContact(String contactId) {
        ContactUtils.validateContactIdNotEmpty(contactId);
        if (contactRepository.existsById(contactId)) {
            contactRepository.deleteById(contactId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Successfully deleted
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Contact not found
        }
    }

    /**
     * GET /contacts : Search contacts
     *
     * @param firstName First name of the contact (optional)
     * @param lastName  Last name of the contact (optional)
     * @return Successful response (status code 200)
     * or Bad request (status code 400)
     * @see ContactsApi#searchContacts
     */
    @Override
    public ResponseEntity<List<ContactDTO>> searchContacts(String firstName, String lastName) {
        List<ContactEntity> foundContacts = getContactsBySearchParam(firstName, lastName);
        if (!foundContacts.isEmpty()) {
            return new ResponseEntity<>(getContactDTOList(foundContacts), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private List<ContactEntity> getContactsBySearchParam(String firstName, String lastName) {
        List<ContactEntity> contacts = Collections.EMPTY_LIST;
        if (StringUtils.isAnyEmpty(firstName, lastName)) {
            if (StringUtils.isEmpty(firstName)) {
                if (StringUtils.isEmpty(lastName)) {
                    throw new ContactManagerRuntimeException(ContactsConstant.Message.INVALID_SEARCH_PARAM_ERR_MSG);
                }
                contacts = contactRepository.findByLastName(lastName);
            } else {
                contacts = contactRepository.findByFirstName(firstName);
            }
        } else {
            contacts = contactRepository.findByFirstNameAndLastName(firstName, lastName);
        }
        return contacts;
    }

    private List<ContactDTO> getContactDTOList(List<ContactEntity> contacts) {
        List<ContactDTO> foundContacts = contacts.stream()
                .map(contactMapper::mapEntityToDTO)
                .collect(Collectors.toList());
        return foundContacts;
    }

    /**
     * PUT /contacts/{contactId} : Update contact details
     *
     * @param contactId  ID of the contact to be updated (required)
     * @param contactDTO (optional)
     * @return Contact updated successfully (status code 200)
     * or Contact not found (status code 404)
     * @see ContactsApi#updateContact
     */
    @Override
    public ResponseEntity<Void> updateContact(String contactId, ContactDTO contactDTO) {
        ContactUtils.validateContactIdNotEmpty(contactId);
        ContactUtils.validateContactNotEmpty(contactDTO);
        // Check if the contact with the given ID exists
        Optional<ContactEntity> optionalContact = contactRepository.findById(contactId);
        if (optionalContact.isPresent()) {
            ContactDTO existingContact = getUpdatedContactDTO(contactDTO, optionalContact.get());
            saveContact(existingContact);
            return ResponseEntity.noContent().build(); // Successfully updated
        } else {
            return ResponseEntity.notFound().build(); // Contact not found
        }
    }

    private ContactDTO getUpdatedContactDTO(ContactDTO newContact, ContactEntity contactEntity) {
        ContactDTO existingContact = contactMapper.mapEntityToDTO(contactEntity);
        existingContact.setFirstName(StringUtils.isEmpty(newContact.getFirstName()) ? existingContact.getFirstName()
                : newContact.getFirstName());
        existingContact.setLastName(StringUtils.isEmpty(newContact.getLastName()) ? existingContact.getLastName() :
                newContact.getLastName());
        existingContact.setEmail(StringUtils.isEmpty(newContact.getEmail()) ? existingContact.getEmail() :
                newContact.getEmail());
        existingContact.setPhoneNumber(StringUtils.isEmpty(newContact.getPhoneNumber()) ?
                existingContact.getPhoneNumber() : newContact.getPhoneNumber());
        return existingContact;
    }

}
