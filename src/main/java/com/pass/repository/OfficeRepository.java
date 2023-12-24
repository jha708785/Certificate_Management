package com.pass.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pass.entites.Office;

public interface OfficeRepository extends JpaRepository<Office, Long> {

}
