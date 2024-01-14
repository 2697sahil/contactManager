package com.singh.sahil.contactManager.utils;

import com.singh.sahil.contactManager.entity.ContactEntity;
import com.singh.sahil.contactManager.openapi.model.ContactDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    ContactMapper INSTANCE = Mappers.getMapper(ContactMapper.class);

    ContactEntity mapDTOToEntity(ContactDTO contactDTO);

    ContactDTO mapEntityToDTO(ContactEntity contactEntity);
}
