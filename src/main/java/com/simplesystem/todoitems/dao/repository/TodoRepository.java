package com.simplesystem.todoitems.dao.repository;

import com.simplesystem.todoitems.dao.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

}