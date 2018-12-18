package com.pd.eweltol.taskManager2.repository;

import com.pd.eweltol.taskManager2.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByClient(String client);
}
