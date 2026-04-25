package com.example._54.controller;

import com.example._54.model.Todo;
import com.example._54.repository.TodoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@Controller
public class TodoController {

    private final TodoRepository repo;

    public TodoController(TodoRepository repo) {
        this.repo = repo;
    }

    // 👉 Trang danh sách
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("todos", repo.findAll());
        return "index";
    }

    // 👉 Mở form
    @GetMapping("/add")
    public String showForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "form";
    }

    // 👉 Lưu
    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute("todo") Todo todo,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "form";
        }

        repo.save(todo);
        return "redirect:/";
    }
}
