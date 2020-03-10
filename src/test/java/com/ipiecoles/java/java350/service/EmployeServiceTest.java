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
}