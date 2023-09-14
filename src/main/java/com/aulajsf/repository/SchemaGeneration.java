package com.aulajsf.repository;

import java.util.List;

import com.aulajsf.model.entities.Empresa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
/////// Nao necessaria para o projeto
public class SchemaGeneration {
	
	public static void main(String[] args) {
	
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("aula-jsf");
		EntityManager em = emf.createEntityManager();
		
		List<Empresa> lista = em.createQuery("from Empresa", Empresa.class).getResultList();
		
		System.out.println(lista);
		
		em.close();
		emf.close();
		
	}

}
