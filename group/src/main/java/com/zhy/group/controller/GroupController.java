package com.zhy.group.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupController {
    @RequestMapping(value = "/group", method = RequestMethod.GET)
    public String get() {
        return "this is a group.";
    }
}
