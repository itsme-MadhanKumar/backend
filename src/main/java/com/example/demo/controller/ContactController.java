package com.example.demo.controller;

import com.example.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://mellifluous-klepon-d47a2d.netlify.app")
public class ContactController
{
    @Autowired
    private ContactService contactService;


    @PostMapping("/query")
    public ResponseEntity<String> contact(@RequestParam String name,@RequestParam String email,@RequestParam String subject,
                                         @RequestParam String  phone, @RequestParam String message)
    {
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Subject: " + subject);
        System.out.println("Phone: " + phone);
        System.out.println("Message: "+message);

        boolean bool = contactService.sendWhatsAppMessage(name,email,subject,phone,message);

        if(bool)
        {
            return ResponseEntity.ok("Form submitted successfully");
        }
        else
        {
            return ResponseEntity.badRequest().body("Form submission failed: Invalid input data.");
        }
    }
}
