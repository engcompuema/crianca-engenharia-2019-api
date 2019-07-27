package br.uema.criancaengenharia.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.uema.criancaengenharia.entity.Inscritos;
import br.uema.criancaengenharia.repository.InscritosRepository;
import br.uema.criancaengenharia.service.InscritosService;

@Service
public class InscritosServiceImpl implements InscritosService {
	
	@Autowired
	private InscritosRepository repository;

	@Override
	public Inscritos createOrUpdate(Inscritos inscritos) {
		return repository.save(inscritos);
	}
	
	@Override
	public void importData(MultipartFile file) {
		try {
			if (!file.isEmpty()) {
				repository.deleteAll();
				Workbook workbook = WorkbookFactory.create(file.getInputStream());

				DataFormatter dataFormatter = new DataFormatter();
				Sheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rowIterator = sheet.rowIterator();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					// IGNORAR A PRIMEIRA LINHA
					if (row.getRowNum() != 0) {

						Inscritos inscritos = new Inscritos();
						inscritos.setNome(dataFormatter.formatCellValue(row.getCell(0)));
						inscritos.setCpf(dataFormatter.formatCellValue(row.getCell(1)));
						inscritos.setEmail(dataFormatter.formatCellValue(row.getCell(2)));
						inscritos.setFuncao(dataFormatter.formatCellValue(row.getCell(3)));
						inscritos.setTipoAtividade(dataFormatter.formatCellValue(row.getCell(4)));
						inscritos.setEmailEnviado(false);
						createOrUpdate(inscritos);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Page<Inscritos> findAll(Pageable pageRequest) {
		return repository.findAll(pageRequest);
	}



	@Override
	public Page<Inscritos> findAllPresentes(Pageable pageRequest) {
		return repository.findAllByCheckinNotNull(pageRequest);
	}

	@Override
	public Optional<Inscritos> findByCpf(String cpf) {
		return repository.findByCpf(cpf);
	}

	@Override
	public Inscritos doCheckin(Long id) {
		Inscritos i = repository.findById(id).get();
		i.setCheckin(new Date());
		return repository.save(i);
		
	}

	@Override
	public Page<Inscritos> findByParameters(int page, int size, boolean email_enviado, List<String> sort) {
		List<Sort.Order> orders = new ArrayList<>();
		for (String order: sort) {
            String[] orderSplit = order.split("!");
            String property = orderSplit[0];
            
            if (orderSplit.length == 1) {
                orders.add(new Sort.Order(Direction.ASC, property));
            } else {
                Sort.Direction direction
                        = Sort.Direction.fromString(orderSplit[1]);
                orders.add(new Sort.Order(direction, property));
            }
        }
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
		

		return repository.findByEmailEnviado(email_enviado, pageable);
	}
	

}
