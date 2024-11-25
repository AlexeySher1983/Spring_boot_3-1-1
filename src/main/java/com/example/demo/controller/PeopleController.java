package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Person;
import com.example.demo.servise.PersServ;
//not used
import jakarta.validation.Valid;

@Controller
//@RequestMapping("/people")

public class PeopleController {

    private  PersServ service;

    @Autowired
    public PeopleController(PersServ service) {
        this.service = service;
    }

    @GetMapping()
    public String indexOfAllModel(Model model) {

        model.addAttribute("allPeople", service.upindex());
        return "/peoples";
    }

    @GetMapping("/{id}")
    public String showId(@PathVariable("id") int id, Model model) {
        model.addAttribute("showPerson", service.show(id));
        return "/show";

    }

    @GetMapping("/new")
    public String newPerson(Model model) {

        model.addAttribute("personCreated", new Person());

        return"/new";
}


    @PostMapping()
    public String create(@ModelAttribute("personCreated") @Valid Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/new";
        }
        service.save(person);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("personEdit", service.show(id));
        return "/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("personEdit") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "/edit";
        }

        service.update(id, person);
        return "redirect:/";
    }

@DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        service.delete(id);
        return "redirect:/";
    }

}
