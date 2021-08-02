package br.com.clodoaldo.apirest.repositories;

import br.com.clodoaldo.apirest.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
