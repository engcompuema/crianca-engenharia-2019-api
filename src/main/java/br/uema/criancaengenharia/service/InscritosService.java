package br.uema.criancaengenharia.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import br.uema.criancaengenharia.entity.Inscritos;

public interface InscritosService {
	
	Inscritos createOrUpdate(Inscritos inscritos);

	Page<Inscritos> findAll(Pageable pageRequest);
	
	void importData(MultipartFile file);

	void enviarConfirmacao(Long id);

}
