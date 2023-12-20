package com.ayalacabrera.denunciaservice.converter;

import org.springframework.stereotype.Component;

import com.ayalacabrera.denunciaservice.dto.DenunciaDTO;
import com.ayalacabrera.denunciaservice.entity.Denuncia;

@Component
public class DenunciaConverter extends AbstractConverter<Denuncia, DenunciaDTO>{

	@Override
	public DenunciaDTO fromEntity(Denuncia entity) {
		if(entity==null) return null;
		return DenunciaDTO.builder()
				.id(entity.getId())
				.dni(entity.getDni())
				.fecha(entity.getFecha())
				.descripcion(entity.getDescripcion())
				.direccion(entity.getDireccion())
				.build();
	}

	@Override
	public Denuncia fromDTO(DenunciaDTO dto) {
		if(dto==null) return null;
		return Denuncia.builder()
				.id(dto.getId())
				.dni(dto.getDni())
				.fecha(dto.getFecha())
				.descripcion(dto.getDescripcion())
				.direccion(dto.getDireccion())
				.build();
	}

}
