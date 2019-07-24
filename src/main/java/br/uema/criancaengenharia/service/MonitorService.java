package br.uema.criancaengenharia.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import br.uema.criancaengenharia.entity.Monitor;

public interface MonitorService {
	
	Monitor createOrUpdate(Monitor monitor);

	Page<Monitor> findAll(Pageable pageRequest);
	
	void importData(MultipartFile file);

}
