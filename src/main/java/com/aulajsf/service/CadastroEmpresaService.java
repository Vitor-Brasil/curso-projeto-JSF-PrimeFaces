package com.aulajsf.service;

import java.io.Serializable;

import javax.inject.Inject;

import com.aulajsf.model.entities.Empresa;
import com.aulajsf.repository.Empresas;
import com.aulajsf.util.Transacional;

public class CadastroEmpresaService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Empresas empresas;
	
	@Transacional
	public void salvar(Empresa empresa) {
		empresas.guardar(empresa);
	}
	
	@Transacional
	public void excluir(Empresa empresa) {
		empresas.remover(empresa);
	}

}
