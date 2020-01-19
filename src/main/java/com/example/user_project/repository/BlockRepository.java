package com.example.user_project.repository;

import com.example.user_project.domain.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block,Long> {
}
