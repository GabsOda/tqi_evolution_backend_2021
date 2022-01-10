package com.github.GabsOda.creditAnalysisAPI.repository;

import com.github.GabsOda.creditAnalysisAPI.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository  extends JpaRepository<Client, Long> {

    List<Client> findByEmail(String email);

}
