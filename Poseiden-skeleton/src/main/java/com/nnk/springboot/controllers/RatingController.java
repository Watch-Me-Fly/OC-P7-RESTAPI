package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@Slf4j
public class RatingController {

    private final RatingService service;

    public RatingController(RatingService service) {
        this.service = service;
    }

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        // show a list of all ratings
        List<Rating> allRatings = service.getAllRatings();
        // add to model
        model.addAttribute("ratingsList", allRatings);
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating,
                           BindingResult result,
                           Model model) {
        // if data is invalid, return
        if (result.hasErrors()) {
            return "rating/add";
        }
        // save new rating
        service.createRating(rating);
        // update model
        model.addAttribute("ratingsList", service.getAllRatings());
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id,
                                 Model model) {
        model.addAttribute("rating", service.getRatingById(id));
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id,
                               @Valid Rating rating,
                               BindingResult result,
                               Model model) {
        // check required files, if invalid, return to form
        if (result.hasErrors()) {
            return "rating/update";
        }
        // update rating
        rating.setId(id);
        service.updateRating(rating);
        // update model
        model.addAttribute("ratingsList", service.getAllRatings());
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        service.deleteRating(id);
        model.addAttribute("ratingsList", service.getAllRatings());
        return "redirect:/rating/list";
    }
}
