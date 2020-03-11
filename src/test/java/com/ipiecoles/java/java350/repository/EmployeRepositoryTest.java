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

    @Test
    public void testavgPerformanceWhereMatriculeStartsWith(){
        //Given
        repo.save(new Employe("jean", "lafrite", "C22221", LocalDate.now(), 1500.00, 2, 1.00));
        repo.save(new Employe("jeano", "lapin", "C22222", LocalDate.now(), 1500.00, 8, 1.00));
        repo.save(new Employe("robert", "mitchum", "C22223", LocalDate.now(), 1500.00, 5, 1.00));
        repo.save(new Employe("jean", "bonbeur", "C22224", LocalDate.now(), 1500.00, 3, 1.00));
        repo.save(new Employe("castor", "ninja", "C22225", LocalDate.now(), 1500.00, 2, 1.00));
        repo.save(new Employe("rollo", "dmf", "C22226", LocalDate.now(), 1500.00, 4, 1.00));
        // Ajout d un non commecial pour voir si il n est pas pris en compte
        repo.save(new Employe("rollo", "dmf", "T22226", LocalDate.now(), 1500.00, 40, 1.00));
        //When
        Double result = repo.avgPerformanceWhereMatriculeStartsWith("C");
        //Then
        Assertions.assertThat(result).isEqualTo(4.00);
    }
}

