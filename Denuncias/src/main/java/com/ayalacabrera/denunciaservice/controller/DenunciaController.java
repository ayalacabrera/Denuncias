package com.ayalacabrera.denunciaservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;

import com.ayalacabrera.denunciaservice.converter.DenunciaConverter;
import com.ayalacabrera.denunciaservice.dto.DenunciaDTO;
import com.ayalacabrera.denunciaservice.entity.Denuncia;
import com.ayalacabrera.denunciaservice.service.DenunciaService;
import com.ayalacabrera.denunciaservice.utils.WrapperResponse;

@RestController
@RequestMapping("/denuncias")
public class DenunciaController {

	@Autowired
	private DenunciaService service;
	@Autowired
	private DenunciaConverter converter;
	@GetMapping()
	public ResponseEntity<List<DenunciaDTO>>findAll(
			@RequestParam(value="dni", required=false,defaultValue="")String dni,
			@RequestParam(value="offset", required=false,defaultValue="0")int pageNumber,
			@RequestParam(value="limit", required=false,defaultValue="5")int pageSize
			){
		Pageable page=PageRequest.of(pageNumber, pageSize);
		List<Denuncia> denuncias;
		if(dni==null) {
			denuncias=service.findAll(page);
		}else {
			denuncias=service.findByDni(dni, page);
		}
		/*if(denuncias.isEmpty()) {
			return ResponseEntity.noContent().build();
		}*/
		List<DenunciaDTO>denunciasDTO=converter.fromEntity(denuncias);
		return new WrapperResponse(true,"success",denunciasDTO).createResponse(HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<WrapperResponse<DenunciaDTO>>findById(@PathVariable("id")int id){
		Denuncia denuncia=service.findById(id);
		/*if(denuncia==null) {
			return ResponseEntity.notFound().build();
		}*/
		DenunciaDTO denunciaDTO=converter.fromEntity(denuncia);
		return new WrapperResponse<DenunciaDTO>(true,"success",denunciaDTO).createResponse(HttpStatus.OK);
	}
	@PostMapping()
	public ResponseEntity<DenunciaDTO> create(@RequestBody DenunciaDTO denunciaDTO){
		Denuncia registro=service.save(converter.fromDTO(denunciaDTO));
		DenunciaDTO registroDTO=converter.fromEntity(registro);
		return new WrapperResponse(true,"success",registroDTO).createResponse(HttpStatus.CREATED);
	}
	@PutMapping(value="/{id}")
	public ResponseEntity<DenunciaDTO>update(@PathVariable("id")int id,@RequestBody DenunciaDTO denunciaDTO){
		Denuncia registro=service.update(converter.fromDTO(denunciaDTO));
		/*if(registro==null){
			return ResponseEntity.notFound().build();
		}*/
		DenunciaDTO registroDTO=converter.fromEntity(registro);
		return new WrapperResponse(true,"success",registroDTO).createResponse(HttpStatus.OK);
	}
	@DeleteMapping(value="/{id}")
	public ResponseEntity<DenunciaDTO>delete(@PathVariable("id")int id){
		service.delete(id);
		return new WrapperResponse(true,"success",null).createResponse(HttpStatus.OK);
	}
}
