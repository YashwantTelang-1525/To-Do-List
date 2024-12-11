package com.check.notes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.check.notes.model.ToDo;
import com.check.notes.model.UserDtls;

public interface ToDoRepository extends JpaRepository<ToDo, Integer>{
	List<ToDo> findByUserOrderByDateAsc(UserDtls user);
}
