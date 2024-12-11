package com.check.notes.controller;

import com.check.notes.model.ToDo;
import com.check.notes.model.UserDtls;
import com.check.notes.service.ToDoService;
import com.check.notes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;
import java.util.List;

import org.springframework.ui.Model;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ToDoService toDoService;

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        UserDtls user = userService.getUserByEmail(principal.getName());

        // Get the next task and remaining tasks
        ToDo upcomingTask = toDoService.getUpcomingTask(user);
        List<ToDo> remainingTasks = toDoService.getRemainingTasks(user);

        // Add to the model to render in the view
        model.addAttribute("upcomingTask", upcomingTask);
        model.addAttribute("remainingTasks", remainingTasks);

        return "user/home";
    }

    
    @GetMapping("/addToDo")
    public String showAddToDoForm() {
        return "user/addToDo";  // This should match the name of your Thymeleaf template (addToDo.html)
    }

    @PostMapping("/addToDo")
    public String addToDo(@RequestParam("task") String task,
                          @RequestParam("priority") String priority,
                          @RequestParam("date") String date,
                          Principal principal) {
        // Retrieve the logged-in user
        UserDtls user = userService.getUserByEmail(principal.getName());
        
        // Create a new ToDo item and save it
        ToDo todo = new ToDo();
        todo.setTask(task);
        todo.setPriority(priority);
        todo.setDate(java.sql.Date.valueOf(date)); // Convert string date to Date type
        todo.setUser(user);

        toDoService.saveToDo(todo);
        return "redirect:/user/"; // Redirect to home after saving
    }
    
    @GetMapping("/updateToDo/{id}")
    public String showUpdateToDoForm(@PathVariable("id") Integer id, Model model) {
        ToDo todo = toDoService.getToDoById(id);
        model.addAttribute("todo", todo);
        return "user/updateToDo";  // Name of the Thymeleaf template for updating
    }

    @PostMapping("/updateToDo/{id}")
    public String updateToDo(@PathVariable("id") Integer id,
                             @RequestParam("task") String task,
                             @RequestParam("priority") String priority,
                             @RequestParam("date") String date) {
        ToDo todo = toDoService.getToDoById(id);
        todo.setTask(task);
        todo.setPriority(priority);
        todo.setDate(java.sql.Date.valueOf(date));
        toDoService.saveToDo(todo);  // Save the updated ToDo
        return "redirect:/user/";  // Redirect to the home page
    }
    
    @GetMapping("/deleteToDo/{id}")
    public String deleteToDo(@PathVariable("id") Integer id) {
        toDoService.deleteToDoById(id);  // Delete the ToDo by ID
        return "redirect:/user/";  // Redirect to the home page
    }
    
    @GetMapping("/doneToDo/{id}")
    public String doneToDo(@PathVariable("id") Integer id) {
        toDoService.deleteToDoById(id);  // Delete the task by ID
        return "redirect:/user/";  // Redirect to home page after deletion
    }

}
