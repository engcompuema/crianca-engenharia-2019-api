package br.uema.criancaengenharia.resources;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.uema.criancaengenharia.entity.Inscritos;
import br.uema.criancaengenharia.service.InscritosService;
import br.uema.criancaengenharia.service.impl.MailService;
import br.uema.dto.Response;

@RestController
@RequestMapping("/api/inscritos")
@CrossOrigin(origins = "*")
public class InscritosResource {

	@Autowired
	private InscritosService service;

	@Autowired
	private MailService mailService;

	@PostMapping(consumes = "multipart/form-data")
	public ResponseEntity<Response<String>> importData(@RequestParam("file") MultipartFile file) {
		Response<String> response = new Response<>();
		try {
			service.importData(file);
			response.setData("Importação Realizada com Sucesso");
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.addError("Ocorreu um Erro");
			return ResponseEntity.badRequest().body(response);
		}
	}

	@PostMapping("{id}/sendConfirmacao")
	public ResponseEntity<Response<String>> sendConfirmacao(@PathVariable("id") Long id) {
		Response<String> response = new Response<>();
		try {
			if(this.mailService.enviar(id)) {
				response.setData("E-mail Enviado Com Sucesso!");
				return ResponseEntity.ok().body(response);
			}else {
				response.setData("Erro ao enviar o Email.");
				return ResponseEntity.badRequest().body(response);
			}
			//this.mailService.enviar("luanfelcomp@gmail.com");
			
		} catch (Exception e) {
			response.setData("Erro ao enviar o Email.");
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	@PostMapping("{id}/checkin")
	public ResponseEntity<Response<String>> doCheckin(@PathVariable("id") Long id) {
		Response<String> response = new Response<>();
		try {
			service.doCheckin(id);
			response.setData("Checkin realizado com Sucesso");
			return ResponseEntity.ok().body(response);
		} catch (Exception e) {
			response.setData("Erro ao realizar o checkin.");
			return ResponseEntity.badRequest().body(response);
		}
	}

	@GetMapping(value = "{page}/{count}")
	public ResponseEntity<Response<Page<Inscritos>>> findAll(@PathVariable int page, @PathVariable int count) {

		Response<Page<Inscritos>> response = new Response<>();

		Pageable pageable = PageRequest.of(page, count);

		Page<Inscritos> inscritos = service.findAll(pageable);
		response.setData(inscritos);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "presentes/{page}/{count}")
	public ResponseEntity<Response<Page<Inscritos>>> findAllPresentes(@PathVariable int page, @PathVariable int count) {

		Response<Page<Inscritos>> response = new Response<>();

		Pageable pageable = PageRequest.of(page, count);

		Page<Inscritos> inscritos = service.findAllPresentes(pageable);
		response.setData(inscritos);
		return ResponseEntity.ok(response);
	}
	

	@GetMapping(value = "{cpf}/buscar")
	public ResponseEntity<Response<Inscritos>> findByCpf(@PathVariable ("cpf") String cpf) {

		Response<Inscritos> response = new Response<>();

		Optional<Inscritos> inscrito = service.findByCpf(cpf);
		if(inscrito.isPresent()) {
			response.setData(inscrito.get());
			return ResponseEntity.ok(response);
		}else {
			response.addError("CPF Não cadastrado");
			return ResponseEntity.badRequest().body(response);
		}
	
	}

	/*
	 * @PostMapping("{id}/enviar") public ResponseEntity<Response<String>>
	 * enviarConfirmaçãoById(@PathVariable Long id){ Response<String> response = new
	 * Response<>();
	 * 
	 * try { service.enviarConfirmacao(id); }catch(Exception e) {
	 * 
	 * }
	 * 
	 * }
	 */
}
