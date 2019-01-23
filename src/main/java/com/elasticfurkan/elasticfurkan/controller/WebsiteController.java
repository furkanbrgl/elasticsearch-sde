package com.elasticfurkan.elasticfurkan.controller;

import com.elasticfurkan.elasticfurkan.dto.SearchResponseDTO;
import com.elasticfurkan.elasticfurkan.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
@RequestMapping("/websites")
public class WebsiteController {

    @Autowired
    private WebsiteService websiteService;

        @GetMapping("/bulk")
        public String insertBook() {
            return websiteService.addBulkData();

        }

    @GetMapping("/{category}")
    public SearchResponseDTO insertBook(@PathVariable(value = "category") String category,
                                        @RequestParam("content") String content,
                                        @RequestParam(value = "page", required = false) Integer page) {
        if ("all".equalsIgnoreCase(category))
            category = null;
        return websiteService.getEsWebsitesByCategoryAndTitle(category, content, (page == null ? 0 : page - 1));
    }

}
