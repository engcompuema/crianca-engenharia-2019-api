package br.uema.criancaengenharia.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.uema.criancaengenharia.entity.Inscritos;

public interface InscritosRepository extends JpaRepository<Inscritos, Long> {
	
	Optional<Inscritos> findByCpf (String cpf);

	Page<Inscritos> findAllByCheckinNotNull(Pageable pageable);

	Page<Inscritos> findByEmailEnviado(boolean email_enviado, Pageable pageable);

}
