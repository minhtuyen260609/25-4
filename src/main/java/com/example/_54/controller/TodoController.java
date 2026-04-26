package com.example._54.controller;

import com.example._54.model.Todo;
import com.example._54.repository.TodoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TodoController {

    private final TodoRepository repo;

    public TodoController(TodoRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("todos", repo.findAll());
        return "index";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("todo", new Todo());
        return "form";
    }

    @GetMapping("/todos/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Todo todo = repo.findById(id).orElse(null);

        if (todo == null) {
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy Todo!");
            return "redirect:/";
        }

        model.addAttribute("todo", todo);
        return "form";
    }

    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute("todo") Todo todo,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            return "form";
        }
        repo.save(todo);
        return "redirect:/";
    }

    @GetMapping("/todos/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "Xóa thành công!");
        } else {
            redirectAttributes.addFlashAttribute("message", "Todo không tồn tại!");
        }

        return "redirect:/";
    }
}