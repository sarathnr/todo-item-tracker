package com.simplesystem.todoitems.dao.repository;

import com.simplesystem.todoitems.dao.entity.Todo;
import com.simplesystem.todoitems.enums.TodoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByDueDateBeforeAndStatusNot(LocalDateTime now, TodoStatus pastDue);
}
