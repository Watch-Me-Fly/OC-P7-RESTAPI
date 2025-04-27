package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@Slf4j
public class CurveController {

    private final CurveService service;

    public CurveController(CurveService service) {
        this.service = service;
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvePoints", service.getAllCurvePoints());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurveForm(CurvePoint curvePoint) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint,
                           BindingResult result,
                           Model model) {
        // if form is invalid, return
        if (result.hasErrors()) {
            return "curvePoint/add";
        }
        // create new curve point
        service.createCurvePoint(curvePoint);
        // return curve list
        model.addAttribute("curvePointList", service.getAllCurvePoints());
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id,
                                 Model model) {
        model.addAttribute("curvePoint", service.getCurvePointById(id));
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id,
                            @Valid CurvePoint curvePoint,
                            BindingResult result,
                            Model model) {
        // check if invalid
        if (result.hasErrors()) {
            return "curvePoint/update";
        }
        curvePoint.setId(id);
        // update
        service.updateCurvePoint(curvePoint);
        // update list
        model.addAttribute("curvePointList", service.getAllCurvePoints());
        // redirect
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id,
                            Model model) {
        service.deleteCurvePoint(id);
        model.addAttribute("curvePointList", service.getAllCurvePoints());
        return "redirect:/curvePoint/list";
    }
}
