package com.upwork.challenge.mapper;

public interface EntityDTOMapper<E, DTO> {

    DTO mapEntityToDTO(E entity);

    E mapDTOToEntity(DTO dto);
}
