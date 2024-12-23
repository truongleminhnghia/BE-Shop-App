package org.project.beecommerceproject.repositories;

import org.project.beecommerceproject.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {
    boolean existsById(String id);
}
