package com.spartaglobal.musicapiproject.repositories;

import com.spartaglobal.musicapiproject.entities.BulkPurchaseDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BulkPurchaseDiscountRepository extends JpaRepository<BulkPurchaseDiscount, Integer> {
    List<BulkPurchaseDiscount> getBulkPurchaseDiscountByMinimumPurchasedGreaterThanEqual(Integer minimumPurchase);
}