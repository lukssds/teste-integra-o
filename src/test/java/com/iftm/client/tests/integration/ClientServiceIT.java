package com.iftm.client.tests.integration;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.iftm.client.dto.ClientDTO;
import com.iftm.client.services.ClientService;
import com.iftm.client.tests.factory.ClientFactory;

@SpringBootTest
@Transactional
public class ClientServiceIT {
	
	@Autowired
	private ClientService service;

	private long existingId;
	private long countTotalClients;
	private String existingName;
	private String existingCPF;


	@BeforeEach
	void setUp() throws Exception {
		existingId = 1L;
		countTotalClients = 12L;
		existingName = "Conceição Evaristo";
		existingCPF = "10619244881";
	}

	/*
	  Implementar um teste que ao receber um id existente deve excluir o cliente
	  com o código do id e verificar se realmente decrementou o número de clientes
	  incluídos na base de dados.
	 */
	@Test
	public void deleteShouldExcludClientWhenIdExists() {

		service.delete(existingId);
		List<ClientDTO> result = service.findAll();
		Assertions.assertEquals(countTotalClients - 1, result.size());
	}

	
	/*Implementar um teste que deverá testar o findById.*/
	@Test
	public void findByIdShouldReturnNameAndCPFOfClientWhenIdExists() {

		ClientDTO result = service.findById(existingId);

		Assertions.assertEquals(existingName, result.getName());
		Assertions.assertEquals(existingCPF, result.getCpf());

	}

	
	/*Implementar um teste que deverá testar o insert.*/
	@Test
	public void InsertShouldCreateANewClient() {

		ClientDTO cliente = ClientFactory.createClientDTO();
		service.insert(cliente);

		List<ClientDTO> result = service.findAll();

		Assertions.assertEquals(countTotalClients, result.size());

	}

	/*Implementar um teste que deverá testar o update.*/	
	@Test
	public void UpdateShouldAtualizateClientData() {

		ClientDTO cliente = ClientFactory.createClientDTO();
		ClientDTO result = service.update(existingId, cliente);

		Assertions.assertEquals(cliente.getId(), result.getId());
		Assertions.assertEquals(cliente.getName(), result.getName());
		Assertions.assertEquals(cliente.getCpf(), result.getCpf());
		Assertions.assertEquals(cliente.getIncome(), result.getIncome());
		Assertions.assertEquals(cliente.getBirthDate(), result.getBirthDate());

	}
}
