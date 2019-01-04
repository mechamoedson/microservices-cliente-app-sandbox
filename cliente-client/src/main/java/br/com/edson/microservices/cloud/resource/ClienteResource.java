package br.com.edson.microservices.cloud.resource;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.edson.cloud.dto.ClienteDTO;
import br.com.edson.microservices.util.CustomErrorType;

@RestController
@RequestMapping("/cliente")
public class ClienteResource {

	private static final String HTTP_HOST_API_DB_SERVER = "http://db-cliente-server";
	public static final Logger logger = LoggerFactory.getLogger(ClienteResource.class);

	@Autowired
	private RestTemplate restTemplate;

	// -------> retorna os clientes <----------\\
	@CrossOrigin
	@HystrixCommand(fallbackMethod = "fallback", groupKey = "getClientes", commandKey = "getClientes", threadPoolKey = "getClientesThread")
	@GetMapping
	public String getClientes() {
		final String url = HTTP_HOST_API_DB_SERVER + "/cliente";
		return restTemplate.getForObject(url, String.class);
	}

	public String fallback(Throwable hystrixCommand) {
		return "Fall Back \n" + hystrixCommand.getMessage();
	}

	// ---------> cria um cliente <---------\\
	@CrossOrigin
	@HystrixCommand(fallbackMethod = "createFallback", groupKey = "createCliente", commandKey = "createCliente", threadPoolKey = "createClienteThread")
	@PostMapping(path = "", consumes = "application/json", produces = "application/json")
	public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteDTO cliente) {

		final String url = HTTP_HOST_API_DB_SERVER + "/cliente";
		return new ResponseEntity<>(restTemplate.postForObject(url, cliente, ClienteDTO.class), HttpStatus.CREATED);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ResponseEntity<ClienteDTO> createFallback(ClienteDTO cliente) {

		return new ResponseEntity(
				new CustomErrorType(
						"Não foi possível atualizar. ClienteEntity com id " + cliente.toString() + " não encontrado."),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ---------> atualiza um cliente <---------\\
	@CrossOrigin
	@HystrixCommand(fallbackMethod = "updateFallback", groupKey = "updateCliente", commandKey = "updateCliente", threadPoolKey = "updateClienteThread")
	@PutMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
	public ResponseEntity<ClienteDTO> updateCliente(@PathVariable("id") long id, @RequestBody ClienteDTO cliente) {

		final String url = HTTP_HOST_API_DB_SERVER + "/cliente/{id}";

		final Map<String, Long> params = new HashMap<>();
		params.put("id", id);

		restTemplate.put(url, cliente, params);

		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity<ClienteDTO> updateFallback(long id, ClienteDTO cliente) {
		return new ResponseEntity(new CustomErrorType(
				"Não foi possível atualizar. ClienteEntity com id " + id + " não encontrado. " + cliente.toString()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// ---------> deleta um cliente <---------\\
	@CrossOrigin
	@HystrixCommand(fallbackMethod = "deleteFallback", groupKey = "deleteCliente", commandKey = "deleteCliente", threadPoolKey = "deleteClienteThread")
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<ClienteDTO> deleteCliente(@PathVariable("id") long id) {

		final String url = HTTP_HOST_API_DB_SERVER + "/cliente/{id}";

		final Map<String, Long> params = new HashMap<>();
		params.put("id", id);

		ResponseEntity<ClienteDTO> resp = this.getClienteById(id);

		restTemplate.delete(url, params);

		return new ResponseEntity<>(resp.getBody(), HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity<ClienteDTO> deleteFallback(long id) {
		return new ResponseEntity(
				new CustomErrorType("Não foi possível deletar. ClienteEntity com id " + id + " não encontrado."),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@CrossOrigin
	@HystrixCommand(fallbackMethod = "clienteByIdFallback", groupKey = "clienteById", commandKey = "clienteById", threadPoolKey = "clienteByIdThread")
	@GetMapping(path = "/{id}")
	private ResponseEntity<ClienteDTO> getClienteById(@PathVariable("id") long id) {

		final String url = HTTP_HOST_API_DB_SERVER + "/cliente/id/{id}";

		final Map<String, Long> params = new HashMap<>();
		params.put("id", id);

		ClienteDTO resp = restTemplate.getForObject(url, ClienteDTO.class, params);

		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ResponseEntity<ClienteDTO> clienteByIdFallback(@PathVariable("id") long id) {
		return new ResponseEntity(
				new CustomErrorType("Não foi possível consultar. ClienteEntity com id " + id + " não encontrado."),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
