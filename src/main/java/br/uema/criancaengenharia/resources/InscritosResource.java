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
import br.uema.dto.Response;

@RestController
@RequestMapping("/api/inscritos")
@CrossOrigin(origins = "*")
public class InscritosResource {

	@Autowired
	private InscritosService service;

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

	
	@GetMapping(value="{page}/{count}")
	public ResponseEntity<Response<Page<Inscritos>>> findAll(
			@PathVariable int page, 
			@PathVariable int count,
			@RequestHeader(value = "Authorization", required = false) String authorization) {
		
		Response<Page<Inscritos>> response = new Response<>();
		
		Pageable pageable = PageRequest.of(page, count);
		
		Page<Inscritos> Inscritoses = service.findAll(pageable);
		response.setData(Inscritoses);
		return ResponseEntity.ok(response);
	}
}
