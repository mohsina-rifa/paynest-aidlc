package com.example.paynest.service;

import com.example.paynest.model.Category;
import com.example.paynest.model.Transaction;
import com.example.paynest.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final CategoryService categoryService;

    public TransactionService(TransactionRepository transactionRepository,
                              CategoryService categoryService) {
        this.transactionRepository = transactionRepository;
        this.categoryService = categoryService;
    }


    public Transaction findById(Integer id) {
        return transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public void create(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public Transaction updateById(Integer id, Transaction transaction) {
        Transaction existingTransaction = findById(id);
        existingTransaction.setAmount(transaction.getAmount());

        return transactionRepository.save(existingTransaction);
    }

    public void deleteById(Integer id) {
        Transaction existingTransaction = findById(id);
        transactionRepository.delete(existingTransaction);
    }

    public void addCategory(Integer transactionId, Integer categoryId) {
        Transaction transaction = findById(transactionId);
        Category category = categoryService.findById(categoryId);
        transaction.getCategories().add(category);
    }

    public void removeCategory(Integer transactionId, Integer categoryId) {
        Transaction transaction = findById(transactionId);
        Category category = categoryService.findById(categoryId);
        transaction.getCategories().remove(category);
    }


}
