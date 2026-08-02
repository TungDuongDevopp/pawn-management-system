package com.tungduong.pawnmanagementsystem.service;

import com.tungduong.pawnmanagementsystem.helper.ResourceNotFoundException;
import com.tungduong.pawnmanagementsystem.model.Collateral;
import com.tungduong.pawnmanagementsystem.repository.CollateralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CollateralService {
    private final CollateralRepository repository;

    public List<Collateral> getAllCollateral() {
        return repository.findAll();
    }
    public Collateral getCollateralById(Long id){
        return repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Collateral not found"));
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

         Collateral currentCollateral = getCollateralById(collateral.getId());
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
