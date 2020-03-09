package com.ipiecoles.java.java350.model;

import java.time.LocalDate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    @ParameterizedTest(name = "matricule {0} et performance {1} et dateEmbauche {2} et Temp parciel {3} donne une prime de : {4}")
    @CsvSource({
        "'M22222', 1, 5, 1.0, 2200.0",
        "'T33333', 2, 10, 0.8, 2640.0"
    })
    // Matricule, performance, date d embauche, temps partiel, prime
    public void testPrimeAnnuel(String matricule, Integer performance, Integer dateEmbauche, Double tempPartiel, Double prime) {
        // Given
        final Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(dateEmbauche));
        employe.setMatricule(matricule);
        employe.setPerformance(performance);
        employe.setTempsPartiel(tempPartiel);

        // When
        final Double vraiPrime = employe.getPrimeAnnuelle();

        //Then
        Assertions.assertThat(vraiPrime).isEqualTo(prime);
    }
}