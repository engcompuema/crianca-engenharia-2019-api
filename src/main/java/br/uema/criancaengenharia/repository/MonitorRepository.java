package br.uema.criancaengenharia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.criancaengenharia.entity.Monitor;

public interface MonitorRepository extends JpaRepository<Monitor, Long> {
	
	

}
