package br.uema.criancaengenharia.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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
		try{
			service.importData(file);
			response.setData("Importação Realizada com Sucesso");
			return ResponseEntity.ok().body(response);
		}catch (Exception e) {
			response.addError("Ocorreu um Erro");
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	/*
	 * @GetMapping public String teste() { System.out.println("Chegou no teste");
	 * try{ this.mailService.enviar("lucas.ferreira@seati.ma.gov.br"); }catch
	 * (Exception e) { e.printStackTrace(); } System.out.println("Chegou aqui");
	 * return "SUCESSO"; }
	 */

	
	@GetMapping(value="{page}/{count}")
	public ResponseEntity<Response<Page<Inscritos>>> findAll(
			@PathVariable int page, 
			@PathVariable int count) {
		
		Response<Page<Inscritos>> response = new Response<>();
		
		Pageable pageable = PageRequest.of(page, count);
		
		Page<Inscritos> inscritos = service.findAll(pageable);
		response.setData(inscritos);
		return ResponseEntity.ok(response);
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
