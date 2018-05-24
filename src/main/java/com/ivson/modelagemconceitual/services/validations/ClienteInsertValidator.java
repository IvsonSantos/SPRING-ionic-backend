package com.ivson.modelagemconceitual.services.validations;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ivson.modelagemconceitual.dto.ClienteNewDTO;
import com.ivson.modelagemconceitual.model.enuns.TipoCliente;
import com.ivson.modelagemconceitual.resources.exceptions.FieldMessage;
import com.ivson.modelagemconceitual.services.validations.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Override
	public void initialize(ClienteInsert ann) {
		
	}
	
	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext  context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		if (objDto.getTipo() == null) {
			list.add(new FieldMessage("tipo", "Tipo não pode ser nulo"));
		}
		
		// testes aqui, inserindo erros na lista
		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod())
			&& !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF Inválido")); 
		}

		// testes aqui, inserindo erros na lista
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod())
			&& !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ Inválido")); 
		}

		// percorrer a lista de FieldMessage e adicionar para cada objeto uma lista de erro do frame
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage())
				   .addPropertyNode(e.getFieldName())
				   .addConstraintViolation();
		}
		
		return list.isEmpty();
	}

}
