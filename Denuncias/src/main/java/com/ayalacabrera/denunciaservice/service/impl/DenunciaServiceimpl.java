package com.ayalacabrera.denunciaservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ayalacabrera.denunciaservice.entity.Denuncia;
import com.ayalacabrera.denunciaservice.exception.GeneralServiceException;
import com.ayalacabrera.denunciaservice.exception.NoDataFoundException;
import com.ayalacabrera.denunciaservice.exception.ValidateServiceException;
import com.ayalacabrera.denunciaservice.repository.DenunciaRepository;
import com.ayalacabrera.denunciaservice.service.DenunciaService;


import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class DenunciaServiceimpl implements DenunciaService {

	@Autowired
	private DenunciaRepository repository;
	@Override
	@Transactional(readOnly = true)
	public List<Denuncia> findAll(Pageable page) {
		try {
			return repository.findAll(page).toList();
		}catch(NoDataFoundException e){
			log.info(e.getMessage(),e);
			throw e;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Denuncia> findByDni(String dni, Pageable page) {
		try {
			return repository.findByDniContaining(dni,page);
		}catch(ValidateServiceException | NoDataFoundException e){
			log.info(e.getMessage(),e);
			throw e;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Denuncia findById(int id) {
		try {
			Denuncia registro =repository.findById(id).orElseThrow();
			return registro;
		}catch(ValidateServiceException | NoDataFoundException e){
			log.info(e.getMessage(),e);
			throw e;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}

	@Override
	public Denuncia save(Denuncia denuncia) {
		try {
			Denuncia registro=repository.save(denuncia);
			if(repository.findByDni(denuncia.getDni())!=null) {
				throw new ValidateServiceException("Ya existe un registro con ese numero de documento " +denuncia.getDni());
			}
			return registro;
		}catch(ValidateServiceException | NoDataFoundException e){
			log.info(e.getMessage(),e);
			throw e;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new GeneralServiceException(e.getMessage(),e);
		}
		
	}
	@Override
	public Denuncia update(Denuncia denuncia) {
		try {
			
			Denuncia registroD=repository.findByDni(denuncia.getDni());
			if(registroD!=null && registroD.getId()!=denuncia.getId()) {
				throw new ValidateServiceException("Ya existe un registro con ese numero de documento " +denuncia.getDni());
			}
			Denuncia registro=repository.findById(denuncia.getId()).orElseThrow();
			registro.setDni(denuncia.getDni());
			registro.setFecha(denuncia.getFecha());
			registro.setTitulo(denuncia.getTitulo());
			registro.setDireccion(denuncia.getDireccion());
			registro.setDescripcion(denuncia.getDescripcion());
			repository.save(registro);
			return registro;
		}catch(ValidateServiceException | NoDataFoundException e){
			log.info(e.getMessage(),e);
			throw e;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}
	
	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		try {
			Denuncia registro=repository.findById(null).orElseThrow();
			repository.delete(registro);
		}catch(ValidateServiceException | NoDataFoundException e){
			log.info(e.getMessage(),e);
			throw e;
		}catch(Exception e){
			log.error(e.getMessage(),e);
			throw new GeneralServiceException(e.getMessage(),e);
		}
	}

	
}
