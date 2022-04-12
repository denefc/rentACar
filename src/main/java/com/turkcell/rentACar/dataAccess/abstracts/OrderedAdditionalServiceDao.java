package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderedAdditionalServiceDao extends JpaRepository<OrderedAdditionalService,Integer> {

    List<OrderedAdditionalService> getByRental_RentalId(int rentalId);

    boolean existsByAdditionalService_AdditionalServiceIdAndRental_RentalId(int additionalServiceId,int rentalId);

}
