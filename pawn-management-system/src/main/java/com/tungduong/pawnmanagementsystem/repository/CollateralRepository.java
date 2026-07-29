package com.tungduong.pawnmanagementsystem.repository;

import com.tungduong.pawnmanagementsystem.model.Collateral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  CollateralRepository  extends JpaRepository<Collateral,Long> {

}
