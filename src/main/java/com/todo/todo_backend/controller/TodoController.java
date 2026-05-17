package com.todo.todo_backend.controller;

import com.todo.todo_backend.entity.Todo;
import com.todo.todo_backend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping
    public List<Todo> getAllTodos(@RequestParam(required = false) LocalDate date) {
        if (date != null) {
            return todoRepository.findByDate(date);
        }
        return todoRepository.findAll();
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        Todo existing = todoRepository.findById(id).orElseThrow();
        existing.setTitle(todo.getTitle());
        existing.setCompleted(todo.isCompleted());
        if (todo.getDate() != null) {
            existing.setDate(todo.getDate());
        }
        return todoRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }
}
