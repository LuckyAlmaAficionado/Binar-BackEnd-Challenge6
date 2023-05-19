package com.binar.securityspringboot.service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SortAscDescImpl {
    public Sort getSortingData(String sort, String urutan){
        if(sort != null){
            if(urutan != null){
                if(urutan.toUpperCase().equals("DESC")){
                    return Sort.by(sort).descending();
                }else if (urutan.toUpperCase().equals("ASC")){
                    return Sort.by(sort).ascending();
                }else {
                    return Sort.by(sort);
                }
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
}
