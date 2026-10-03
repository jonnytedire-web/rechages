package com.tullave.recharges.repository;

import com.tullave.recharges.domain.entities.Recharge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RechargeRepository  extends JpaRepository<Recharge, Long> {

}
