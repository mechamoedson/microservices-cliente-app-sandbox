package br.com.edson.microservices.cloud.resource;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edson.cloud.dto.ClienteDTO;
import br.com.edson.cloud.enums.Risco;
import br.com.edson.microservices.cloud.entity.ClienteEntity;
import br.com.edson.microservices.cloud.repository.ClientesRepository;
import br.com.edson.microservices.util.CustomErrorType;

@RestController
@RequestMapping("/cliente")
public class DbClienteServerResource {

	public static final Logger logger = LoggerFactory.getLogger(DbClienteServerResource.class);

	ClientesRepository clienteRepository;

	public DbClienteServerResource(ClientesRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	//---------> lista os clientes da base <---------\\
	@GetMapping
	public ResponseEntity<List<ClienteEntity>> getClientes() {
		final List<ClienteEntity> clientes = clienteRepository.findAll();
		if (clientes.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(clientes, HttpStatus.OK);
	}

	
	//---------> retorna um cliente pelo nome <---------\\
	@GetMapping("/{nome}")
	public ResponseEntity<List<ClienteEntity>> getClienteByNome(@PathVariable("nome") String nome) {
		final List<ClienteEntity> clientes = clienteRepository.findByNomeContaining(nome);
		if (clientes.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(clientes, HttpStatus.OK);
	}
	
	//---------> retorna um cliente pelo id <---------\\
	@GetMapping("/id/{id}")
	public ResponseEntity<ClienteEntity> getClienteById(@PathVariable("id") Long id) {
		Optional<ClienteEntity> cliente = clienteRepository.findById(id);
		if (!cliente.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
	}

	
	//---------> cria um cliente na base <---------\\
	@PostMapping(path = "", consumes = "application/json", produces = "application/json")
	public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteDTO cliente) {

		final ClienteEntity entity = new ClienteEntity();
		copyToEntity(cliente, entity);
		calculaRisco(entity);

		BeanUtils.copyProperties(clienteRepository.save(entity), cliente);
		
		return new ResponseEntity<>(cliente, HttpStatus.CREATED);
	}

	
	//---------> atualiza um cliente <---------\\
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<ClienteEntity> updateCliente(@PathVariable("id") long id, @RequestBody ClienteDTO cliente) {

		final Optional<ClienteEntity> currentCliente = clienteRepository.findById(id);

		if (!currentCliente.isPresent()) {
			logger.error("Não foi possível atualizar. ClienteEntity com id {} não encontrado.", id);
			return new ResponseEntity(
					new CustomErrorType("Não foi possível atualizar. ClienteEntity com id " + id + " não encontrado."),
					HttpStatus.NOT_FOUND);
		}

		copyToEntity(cliente, currentCliente.get());
		calculaRisco(currentCliente.get());

		clienteRepository.save(currentCliente.get());
		return new ResponseEntity<>(currentCliente.get(), HttpStatus.OK);
	}
	
	
	//---------> apaga um cliente da base<---------\\
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@DeleteMapping(path="/{id}")
	public ResponseEntity<ClienteEntity> deleteCliente(@PathVariable("id") long id) {

		final Optional<ClienteEntity> currentCliente = clienteRepository.findById(id);

		if (!currentCliente.isPresent()) {
			logger.error("Não foi possível deletar. ClienteEntity com id {} não encontrado.", id);
			return new ResponseEntity(
					new CustomErrorType("Não foi possível atualizar. ClienteEntity com id " + id + " não encontrado."),
					HttpStatus.NOT_FOUND);
		}

		clienteRepository.delete(currentCliente.get());
		return new ResponseEntity<>(currentCliente.get(), HttpStatus.OK);
	}
	

	private void calculaRisco(ClienteEntity cliente) {
		
		if (Risco.B.equals(cliente.getRisco())) {
			cliente.setTaxaRisco(0.1);
		} else if (Risco.C.equals(cliente.getRisco())) {
			cliente.setTaxaRisco(0.2);
		} else {
			cliente.setRisco(Risco.A);
			cliente.setTaxaRisco(0);
		}
	}

	private void copyToEntity(ClienteDTO cliente, ClienteEntity entity) {
		entity.setLimCredito(cliente.getLimCredito());
		entity.setNome(cliente.getNome());
		entity.setRisco(cliente.getRisco());
	}

}
