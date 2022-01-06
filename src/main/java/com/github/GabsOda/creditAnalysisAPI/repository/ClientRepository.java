package com.github.GabsOda.creditAnalysisAPI.repository;

import com.github.GabsOda.creditAnalysisAPI.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository  extends JpaRepository<Client, Long> {

}
