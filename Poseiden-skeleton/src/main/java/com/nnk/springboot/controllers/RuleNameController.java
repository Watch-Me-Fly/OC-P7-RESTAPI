package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
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
public class RuleNameController {

    private final RuleNameService service;

    public RuleNameController(RuleNameService service) {
        this.service = service;
    }

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        // show a list of all rule names :
        List<RuleName> allNames = service.getAllRuleNames();
        model.addAttribute("ruleNamesList", allNames);
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName,
                           BindingResult result,
                           Model model) {
        // if form is invalid
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        // save new rule
        service.createRuleName(ruleName);
        // update model
        model.addAttribute("ruleName", service.getAllRuleNames());
        // redirect to the list of all rule names
        return "ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName ruleName = service.getRuleNameById(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id,
                                 @Valid RuleName ruleName,
                                 BindingResult result,
                                 Model model) {
        // check required fields
        if (result.hasErrors()) {
            return "ruleName/update";
        }
        // update
        ruleName.setId(id);
        service.updateRuleName(ruleName);
        // get back the list
        model.addAttribute("ruleName", service.getAllRuleNames());
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        service.deleteRuleName(id);
        model.addAttribute("ruleName", service.getAllRuleNames());
        return "redirect:/ruleName/list";
    }
}
