/**
 * Copyright 2018 onwards - Sunit Katkar (sunitkatkar@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sunitkatkar.blogspot.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sunitkatkar.blogspot.tenant.dto.BooksCreationDto;
import com.sunitkatkar.blogspot.tenant.model.Book;
import com.sunitkatkar.blogspot.tenant.model.CustomUserDetails;
import com.sunitkatkar.blogspot.tenant.model.Role;
import com.sunitkatkar.blogspot.tenant.service.BookService;
import com.sunitkatkar.blogspot.tenant.service.RoleService;
import com.sunitkatkar.blogspot.util.TenantContextHolder;
 

@Controller
public class LoginController {
	
//	@Autowired
//	private BookService bookService;
	
	@Autowired
	private RoleService roleService;

    @RequestMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        getLoggedInUsername().ifPresent(f -> {
            model.addAttribute("userName", f);
        });
        getTenantName().ifPresent(d -> {
            model.addAttribute("tenantName", d);
        });
        
//        List<Book> books = bookService.findAll();
//        model.addAttribute("books", books != null ? books : new ArrayList<Book>());
        
        return "index";
    }

    @RequestMapping("/user/index")
    public String userIndex(Model model) {
        getLoggedInUsername().ifPresent(f -> {
            model.addAttribute("userName", f);
        });
        getTenantName().ifPresent(d -> {
            model.addAttribute("tenantName", d);
        });
        getRoles().ifPresent(f -> {
        	model.addAttribute("roles", f);
        });
        return "user/index";
    }

//    @PostMapping("/save")
//    public String saveBooks(@ModelAttribute BooksCreationDto form, Model model) {
////		/* bookService.saveAll(form.getBooks()); */
//     
//        model.addAttribute("books", bookService.findAll());
//        return "redirect:/books/all";
//    }   
    
    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    private Optional<List<Role>> getRoles() {
    	return Optional.ofNullable(roleService.findAll());		
    }
    
    private Optional<String> getLoggedInUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = null;
        if (auth != null && !auth.getClass().equals(AnonymousAuthenticationToken.class)) {
            // User user = (User) auth.getPrincipal();
            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
            userName = userDetails.getUsername();
        }

        return Optional.ofNullable(userName);
    }

    private Optional<String> getTenantName() {
    	return Optional.ofNullable(TenantContextHolder.getTenant());
    	
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String tenantName = null;
//        if (auth != null && !auth.getClass().equals(AnonymousAuthenticationToken.class)) {
//            // User user = (User) auth.getPrincipal();
//            CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
//            tenantName = userDetails.getTenant();
//        }
//        return Optional.ofNullable(tenantName);
    }
}
