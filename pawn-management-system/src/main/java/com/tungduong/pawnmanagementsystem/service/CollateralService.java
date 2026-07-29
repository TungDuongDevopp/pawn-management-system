package com.tungduong.pawnmanagementsystem.service;

import com.tungduong.pawnmanagementsystem.model.Collateral;
import com.tungduong.pawnmanagementsystem.model.Customer;
import com.tungduong.pawnmanagementsystem.repository.CollateralRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CollateralService {
    private final CollateralRepository repository;

    public CollateralService(CollateralRepository repository) {
        this.repository = repository;
    }

    public List<Collateral> getAllCollateral() {
        return repository.findAll();
    }
    public Optional<Collateral> getCollateralById(Long id){
        return repository.findById(id);
    }
    public Collateral saveCollateral(Collateral collateral){

        return repository.save(collateral);
    }
    public boolean deleteCollateralById(Long id){
        if(!repository.existsById(id)) return false;
        repository.deleteById(id);

        return true;
    }
    public Collateral updateCollateral(Collateral collateral){
        Optional<Collateral> optional = getCollateralById(collateral.getId());

        if(optional.isEmpty()){
            return null;
        }
         Collateral currentCollateral = optional.get();
            currentCollateral.setName(collateral.getName());
            currentCollateral.setImageUrl(collateral.getImageUrl());
            currentCollateral.setDescription(collateral.getDescription());
            currentCollateral.setStatus(collateral.getStatus());
            currentCollateral.setValuation(collateral.getValuation());
            currentCollateral.setCategory(collateral.getCategory());

        repository.save(currentCollateral);
        return currentCollateral;
    }


}
