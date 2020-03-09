package com.ipiecoles.java.java350.model;

import java.time.LocalDate;

import com.ipiecoles.java.java350.model.Employe;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmployeTest {

    @Test //Junit 4 : org.junit.Test, Junit 5 : org.junit.jupiter.api.Test
    public void testAncieneteDateEmbaucheNmoin2(){
        //Given
        final Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(2));

        // When
        final Integer nbAnnees = employe.getNombreAnneeAnciennete();

        // Then
        Assertions.assertThat(nbAnnees).isEqualTo(2);
    }

    @Test
    public void testAncieneteDateEmbaucheNplus2() {
        // Given
        final Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(2));

        // When
        final Integer nbAnnees = employe.getNombreAnneeAnciennete();

      //Then
      Assertions.assertThat(nbAnnees).isEqualTo(0);
}
}