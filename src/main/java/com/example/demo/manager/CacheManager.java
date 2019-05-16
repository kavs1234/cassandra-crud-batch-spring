package com.example.demo.manager;

import com.example.demo.model.Employee;

import java.util.HashMap;
import java.util.Map;

public class CacheManager {

        static Map<Integer, Employee> cache=new HashMap<>();
        int size;

        Employee getCachedEmployee(int id){
           return  cache.get(cache.get(id));
        }


        private boolean isPresent(int id){
           return cache.containsKey(id);
        }

}
