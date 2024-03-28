package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SocialMediaController {

    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    // Endpoint to get all messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok().body(messages);
    }

    // Endpoint to get a message by its ID
    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id) {
        Message message = messageService.getMessageById(message_id);
        return ResponseEntity.ok().body(message);
    }

    // Endpoint to get messages by account ID
    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getMessagesByAccountId(@PathVariable int account_id) {
        List<Message> messages = messageService.getMessagesByAccount(account_id);
        return ResponseEntity.ok().body(messages);
    }

    // Endpoint to register a new account
    @PostMapping("/register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account newAccount) {
        if (newAccount.getUsername().length() > 0 && newAccount.getPassword().length() > 4) {
            if (accountService.isThereMatchingAccountUsername(newAccount.getUsername())) {
                return ResponseEntity.status(409).body(null);
            } else {
                Account registeredAccount = accountService.registerAccount(newAccount);
                return ResponseEntity.ok().body(registeredAccount);
            }
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Endpoint to login an existing account
    @PostMapping("/login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account newAccount) {
        Account loginAccount = accountService.loginAccount(newAccount);
        if (loginAccount != null) {
            return ResponseEntity.ok().body(loginAccount);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    // Endpoint to post a new message
    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message newMessage) {
        Message createdMessage = messageService.postMessage(newMessage);
        if (createdMessage != null) {
            return ResponseEntity.ok().body(createdMessage);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // Endpoint to delete a message by its ID
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Integer> deleteMessage(@PathVariable int message_id) {
        int messageRows = messageService.deleteMessage(message_id);
        if (messageRows > 0) {
            return ResponseEntity.ok().body(messageRows);
        } else {
            return ResponseEntity.ok().build();
        }
    }

    // Endpoint to update a message by its ID
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> patchMessage(@PathVariable int message_id, @RequestBody Message updatedMessage) {
        int messageRows = messageService.updateMessage(message_id, updatedMessage.getMessage_text());
        if (messageRows > 0) {
            return ResponseEntity.ok().body(messageRows);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}