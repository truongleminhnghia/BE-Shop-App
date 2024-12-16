package org.project.beecommerceproject.repositories;

import org.project.beecommerceproject.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

    User findByEmail(String email);
}
