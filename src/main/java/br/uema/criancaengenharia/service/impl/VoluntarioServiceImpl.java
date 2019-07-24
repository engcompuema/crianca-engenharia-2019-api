package br.uema.criancaengenharia.service.impl;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.uema.criancaengenharia.entity.Voluntario;
import br.uema.criancaengenharia.repository.VoluntarioRepository;
import br.uema.criancaengenharia.service.VoluntarioService;

@Service
public class VoluntarioServiceImpl implements VoluntarioService {
	
	@Autowired
	private VoluntarioRepository repository;

	@Override
	public Voluntario createOrUpdate(Voluntario voluntario) {
		return repository.save(voluntario);
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

						Voluntario voluntario = new Voluntario();
						voluntario.setNome(dataFormatter.formatCellValue(row.getCell(0)));
						voluntario.setCpf(dataFormatter.formatCellValue(row.getCell(1)));
						voluntario.setEmail(dataFormatter.formatCellValue(row.getCell(2)));
						voluntario.setEmailEnviado(false);
						createOrUpdate(voluntario);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Page<Voluntario> findAll(Pageable pageRequest) {
		return repository.findAll(pageRequest);
	}
	

}
