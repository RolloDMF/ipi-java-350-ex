package com.ipiecoles.java.java350.service;

import javax.persistence.EntityExistsException;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest {

    @InjectMocks
    private EmployeService employeService;
    @Mock
    private EmployeRepository repo;

    @Test
    public void testEmbaucheEmploye() throws EntityExistsException, EmployeException {
      //Given
        String nom = "Doe";
        String prenom = "Jhon";
        Poste poste = Poste.COMMERCIAL;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;
        //findLastMatricule => 00345 / null
        //findByMatricule => null
        Mockito.when(repo.findLastMatricule()).thenReturn("00345");
        Mockito.when(repo.findByMatricule("C00346")).thenReturn(null);

      //When
        employeService.embaucheEmploye(nom, prenom, poste, niveauEtude, tempsPartiel);

      //Then
        //en bdd verifier si l employé est bien créé
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(repo, Mockito.times(1)).save(employeCaptor.capture());
        Assertions.assertThat(employeCaptor.getValue().getMatricule()).isEqualTo("C00346");
    }
    
    
    @ParameterizedTest()
    //salaire, pourcentage d augmentation, resultat
    @CsvSource({
        "150, 70, 100, 2.00, 1", // cas 1
        "12, 85, 100, 15.00, 10", // cas 2
        "6, 104, 100, 15.00, 6", // cas 3
        "1, 120, 100, 15.00, 2", // cas 4
        "1, 130, 100, 15.00, 5", // cas 5
        "1, 130, 100, 2.00, 6", // cas 5 avec perf superieure a la moyenne 
    })
    public void testCalculPerformenceCommercial(Integer performance, Long caTraite, Long objectifCa, Double perfMoyenne, Integer perfAttendue) throws EmployeException {
      //Given
        String matricule = "C00001";

        // Creation d un employé test
        Employe employe = new Employe();
        employe.setMatricule(matricule);
        employe.setPerformance(performance);

        Mockito.when(repo.findByMatricule(matricule)).thenReturn(employe);
        Mockito.when(repo.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(perfMoyenne);

      //When
        employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa);

      //Then
        //en bdd verifier si l employé est bien créé
        ArgumentCaptor<Employe> employeCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(repo, Mockito.times(1)).save(employeCaptor.capture());
        Assertions.assertThat(employeCaptor.getValue().getPerformance()).isEqualTo(perfAttendue);
    }

    @Test
    public void testCalculPerformenceCommercialObjectifNull() throws EmployeException {
      //Given
        String matricule = "C00001";
        Long caTraite = 100l;
        Long objectifCa = null;

        // Creation d un employé test
        Employe employe = new Employe();
        employe.setMatricule(matricule);

      //When Then
      EmployeException e = org.junit.jupiter.api.Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));
      org.junit.jupiter.api.Assertions.assertEquals("L'objectif de chiffre d'affaire ne peut être négatif ou null !", e.getMessage());
    }

    @Test
    public void testCalculPerformenceCommercialMatriculeeNull() throws EmployeException {
      //Given
        String matricule = "M00001";
        Long caTraite = null;
        Long objectifCa = 20l;

        // Creation d un employé test
        Employe employe = new Employe();
        employe.setMatricule(matricule);

      //When Then
      EmployeException e = org.junit.jupiter.api.Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));
      org.junit.jupiter.api.Assertions.assertEquals("Le chiffre d'affaire traité ne peut être négatif ou null !", e.getMessage());
    }

    @Test
    public void testCalculPerformenceCommercialMatriculeFaux() throws EmployeException {
      //Given
        String matricule = "M00001";
        Long caTraite = 100l;
        Long objectifCa = 20l;


      //When Then
      EmployeException e = org.junit.jupiter.api.Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));
      org.junit.jupiter.api.Assertions.assertEquals("Le matricule ne peut être null et doit commencer par un C !", e.getMessage());
    }

    @Test
    public void testCalculPerformenceCommercialEmployeNull() throws EmployeException {
      //Given
        String matricule = "C00346";
        Long caTraite = 100l;
        Long objectifCa = 20l;

        // Creation d un employé test
        Employe employe = new Employe();
        employe.setMatricule(matricule);

        Mockito.when(repo.findByMatricule(matricule)).thenReturn(null);

      //When Then
      EmployeException e = org.junit.jupiter.api.Assertions.assertThrows(EmployeException.class, () -> employeService.calculPerformanceCommercial(matricule, caTraite, objectifCa));
      org.junit.jupiter.api.Assertions.assertEquals("Le matricule " + matricule + " n'existe pas !", e.getMessage());
    }
}