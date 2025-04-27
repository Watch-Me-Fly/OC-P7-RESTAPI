package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
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
public class BidListController {

    private final BidListService service;

    public BidListController(BidListService service) {
        this.service = service;
    }

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        // show a list of all bids
        model.addAttribute("bidLists", service.getAllBidLists());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid,
                           BindingResult result,
                           Model model) {
        // if form is invalid, return to form
        if (result.hasErrors()) {
            return "bidList/add";
        }
        // save new bid
        service.createBidList(bid);
        // update model for the list page
        model.addAttribute("bidLists", service.getAllBidLists());
        // redirect to list of bids
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("bidList", service.getBidList(id));
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id,
                            @Valid BidList bidList,
                            BindingResult result,
                            Model model) {
        // check required fields, if is invalid, return to form
        if (result.hasErrors()) {
            return "bidList/update";
        }
        // update bid
        bidList.setBidListId(id);
        service.updateBidList(bidList);
        // update model for the list page
        model.addAttribute("bidLists", service.getAllBidLists());
        // redirect to list of bids
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        service.deleteBidList(id);
        model.addAttribute("bidLists", service.getAllBidLists());
        return "redirect:/bidList/list";
    }
}
