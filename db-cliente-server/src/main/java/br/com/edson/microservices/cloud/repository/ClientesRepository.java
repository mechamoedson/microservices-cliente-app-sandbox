package br.com.edson.microservices.cloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.edson.microservices.cloud.entity.ClienteEntity;

public interface ClientesRepository extends JpaRepository<ClienteEntity, Long> {
	List<ClienteEntity> findByNomeContaining(String nome);
}

