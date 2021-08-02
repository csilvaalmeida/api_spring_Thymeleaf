package br.com.clodoaldo.apirest.repositories;



import br.com.clodoaldo.apirest.models.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
 //   Iterable<Phone> findById(Person person);
  //  Iterable<Phone> findBytbPersonId(Person person);
     List <Phone> findAllBytbPersonId (Long person);

}



