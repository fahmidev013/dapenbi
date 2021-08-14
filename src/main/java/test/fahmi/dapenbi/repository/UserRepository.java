package test.fahmi.dapenbi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import test.fahmi.dapenbi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findByIdNumber (String IdNumber);
	List<User> findByName(String name);
}
