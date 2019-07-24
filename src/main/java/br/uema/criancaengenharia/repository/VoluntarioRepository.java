package br.uema.criancaengenharia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.criancaengenharia.entity.Voluntario;

public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {
	
	

}
