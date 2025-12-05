package com.sachin.expensemanager.service;

import com.sachin.expensemanager.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CachedCountServiceImpl implements CachedCountService{

    private final ExpenseRepository expenseRepository;

    @Override
    @Cacheable(value="expenseCount")
    public Long getCount() {
        System.out.println("Count logic called----------------------");
        return expenseRepository.count();
    }

    @CacheEvict(value = "expenseCount", allEntries = true)
    @Override
    public void resetCount() {
        System.out.println("Count cleared----------------------");
    }



}
