package com.accenture.service;

import com.accenture.repository.entity.Client;
import com.accenture.service.dto.ClientRequestDto;

public interface ClientService {

    void saveClient(ClientRequestDto clientRequestDto);
}
