package com.example.paynest.controller;

import com.example.paynest.dto.TransactionResponse;
import com.example.paynest.model.Transaction;
import com.example.paynest.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    //    CRUD : create
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Transaction transaction) {
        transactionService.create(transaction);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //    CRUD : read
    @GetMapping("/{id}")
    public TransactionResponse findById(@PathVariable Integer id) {
        return transactionService.findResponseById(id);
    }


    //    CRUD : update
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateById(@PathVariable Integer id, @RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.updateById(id, transaction));
    }

    //    CRUD : delete
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        transactionService.deleteById(id);
    }

    //
    // categories
    //
    @PostMapping("/{id}/categories/{categoryId}")
    public ResponseEntity<Void> addCategory(@PathVariable Integer id,
                                            @PathVariable Integer categoryId) {
        transactionService.addCategory(id, categoryId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/categories/{categoryId}")
    public ResponseEntity<Void> removeCategory(@PathVariable Integer id,
                                               @PathVariable Integer categoryId) {
        transactionService.removeCategory(id, categoryId);
        return ResponseEntity.noContent().build();
    }


}
