package com.example.paynest.service;

import com.example.paynest.model.Transaction;
import com.example.paynest.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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

}
