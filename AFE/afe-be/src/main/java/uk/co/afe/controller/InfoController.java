package uk.co.afe.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergey Teryoshin
 * 15.03.2018 22:29
 */
@RestController
public class InfoController {

    @PreAuthorize("permitAll()")
    @GetMapping("/")
    public String test() {
        return "AFE. Back-end server!";
    }

}
