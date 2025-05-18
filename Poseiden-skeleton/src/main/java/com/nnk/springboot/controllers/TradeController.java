package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.service.TradeService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
public class TradeController {

    private static final Logger log = LoggerFactory.getLogger(TradeController.class);
    private final TradeService service;

    public TradeController(TradeService service) {
        this.service = service;
    }

    @RequestMapping("/trade/list")
    public String home(Model model, HttpServletRequest request)
    {
        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            model.addAttribute("user", user);
        }

        model.addAttribute("trades", service.getAllTrades());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTrade(Trade bid) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade,
                           BindingResult result,
                           Model model) {
        // checks if valid, or returns to the add form
        if (result.hasErrors()) {
            return "trade/add";
        }
        // save new rating
        service.createTrade(trade);
        // update model to get the list
        model.addAttribute("tradeList", service.getAllTrades() );
        // redirect to the list of trades
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("trade", service.getTradeById(id));
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id,
                              @Valid Trade trade,
                              BindingResult result,
                              Model model) {
        // check required fields
        if (result.hasErrors()) {
            return "trade/update";
        }
        // update bid
        trade.setTradeId(id);
        service.updateTrade(trade);
        // update the list
        model.addAttribute("tradeList", service.getAllTrades() );
        // return the list
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id,
                              Model model) {
        service.deleteTrade(id);
        model.addAttribute("tradeList", service.getAllTrades() );
        return "redirect:/trade/list";
    }
}
