package com.example.demo.controller;
import com.example.demo.service.WpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://classy-valkyrie-22e293.netlify.app")

public class WpController
{
    @Autowired
    WpService mailService;


    @PostMapping("/submit")
    public ResponseEntity<String> submitForm(@RequestParam String name, @RequestParam String email, @RequestParam String number, @RequestParam String destination)
    {
        // Process the form data
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Number: " + number);
        System.out.println("Destination: "+destination);
        boolean bool = mailService.sendWhatsAppMessage(name,email,number,destination);
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
