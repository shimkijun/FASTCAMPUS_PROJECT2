package com.example.user_project.repository;

import com.example.user_project.domain.Block;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BlockRepositoryTest {

    @Autowired
    private BlockRepository blockRepository;

    @Test
    void crud(){
        Block block = new Block();
        block.setName("martin");
        block.setReson("그냥");
        block.setStartDate(LocalDate.now());
        block.setEndDate(LocalDate.now());

        blockRepository.save(block);

        List<Block> blocks = blockRepository.findAll();

        assertEquals(blocks.size(),1);
        assertEquals(blocks.get(0).getName(),"martin");
    }
}