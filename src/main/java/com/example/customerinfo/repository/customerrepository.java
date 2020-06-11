package com.example.customerinfo.repository;

import com.example.customerinfo.model.*;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface customerrepository extends JpaRepository<customer, Long> {
	List<customer> findByActive(boolean active);
	List<customer> findByNameContaining(String name);
}
