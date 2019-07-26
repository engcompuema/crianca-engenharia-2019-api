package br.uema.criancaengenharia.service;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import br.uema.criancaengenharia.entity.Inscritos;

public interface InscritosService {
	
	Inscritos createOrUpdate(Inscritos inscritos);

	Page<Inscritos> findAll(Pageable pageRequest);
	
	Page<Inscritos> findAllPresentes(Pageable pageRequest);
	
	void importData(MultipartFile file);

	void enviarConfirmacao(Long id);

	Optional<Inscritos> findByCpf(String cpf);

	Inscritos doCheckin(Long id);

}
