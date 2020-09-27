package org.example;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Student, Long> {

    List<Student> findByName(String name);

}
