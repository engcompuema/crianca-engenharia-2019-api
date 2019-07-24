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

import br.uema.criancaengenharia.entity.Monitor;
import br.uema.criancaengenharia.repository.MonitorRepository;
import br.uema.criancaengenharia.service.MonitorService;

@Service
public class MonitorServiceImpl implements MonitorService {
	
	@Autowired
	private MonitorRepository repository;

	@Override
	public Monitor createOrUpdate(Monitor Monitor) {
		return repository.save(Monitor);
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

						Monitor monitor = new Monitor();
						monitor.setNome(dataFormatter.formatCellValue(row.getCell(0)));
						monitor.setCpf(dataFormatter.formatCellValue(row.getCell(1)));
						monitor.setEmail(dataFormatter.formatCellValue(row.getCell(2)));
						monitor.setTipoAtividade(dataFormatter.formatCellValue(row.getCell(3)));
						monitor.setEmailEnviado(false);
						createOrUpdate(monitor);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Page<Monitor> findAll(Pageable pageRequest) {
		return repository.findAll(pageRequest);
	}
	

}
