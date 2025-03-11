package ru.veezday.dev.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.veezday.dev.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
