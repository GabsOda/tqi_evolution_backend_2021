package com.github.GabsOda.creditAnalysisAPI.mapper;

import com.github.GabsOda.creditAnalysisAPI.dto.request.ClientDTO;
import com.github.GabsOda.creditAnalysisAPI.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    Client toModel(ClientDTO clientDTO);

    ClientDTO toDto(Client client);

}
