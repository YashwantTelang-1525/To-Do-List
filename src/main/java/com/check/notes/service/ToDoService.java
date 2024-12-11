package com.check.notes.service;

import com.check.notes.model.ToDo;
import com.check.notes.model.UserDtls;
import com.check.notes.repository.ToDoRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToDoService {

    @Autowired
    private ToDoRepository toDoRepository;

    public void saveToDo(ToDo toDo) {
        toDoRepository.save(toDo);
    }
    
    public ToDo getToDoById(Integer id) {
        Optional<ToDo> todo = toDoRepository.findById(id);
        return todo.orElse(null);
    }
    
    public void deleteToDoById(Integer id) {
        toDoRepository.deleteById(id);
    }
    
    public ToDo getUpcomingTask(UserDtls user) {
        List<ToDo> tasks = toDoRepository.findByUserOrderByDateAsc(user);
        return tasks.isEmpty() ? null : tasks.get(0); // Get the earliest task if it exists
    }

    public List<ToDo> getRemainingTasks(UserDtls user) {
        List<ToDo> tasks = toDoRepository.findByUserOrderByDateAsc(user);
        return tasks.size() <= 1 ? List.of() : tasks.subList(1, tasks.size()); // Remaining tasks, excluding the first
    }
    
    
}
