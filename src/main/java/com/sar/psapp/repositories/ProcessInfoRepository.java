package com.sar.psapp.repositories;

import com.sar.psapp.model.AppUser;
import com.sar.psapp.model.ProcessInfo;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Registered
public interface ProcessInfoRepository extends JpaRepository<ProcessInfo, Long> {
    List<ProcessInfo> findByUser(AppUser user);
}
