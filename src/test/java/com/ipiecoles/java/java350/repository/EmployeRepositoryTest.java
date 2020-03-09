package com.ipiecoles.java.java350.repository;

import java.time.LocalDate;

import com.ipiecoles.java.java350.model.Employe;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class) // Junit 5
@SpringBootTest
public class EmployeRepositoryTest {

    @Autowired
    EmployeRepository repo;
    @Test
    public void testFindByMatricule(){
        //Given
        Employe e = repo.save(new Employe("jean", "lafrite", "T22222", LocalDate.now(), 1500.00, 1, 1.00));
        //When
        Employe result = repo.findByMatricule("T22222");
        //Then
        Assertions.assertThat(result).isEqualTo(e);
    }
}