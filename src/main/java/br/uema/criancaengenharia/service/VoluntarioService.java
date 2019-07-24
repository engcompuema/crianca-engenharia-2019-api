package br.uema.criancaengenharia.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import br.uema.criancaengenharia.entity.Voluntario;

public interface VoluntarioService {
	
	Voluntario createOrUpdate(Voluntario voluntario);

	Page<Voluntario> findAll(Pageable pageRequest);
	
	void importData(MultipartFile file);

}
