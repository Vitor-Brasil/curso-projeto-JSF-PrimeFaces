package com.aulajsf.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.faces.convert.Converter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.UnselectEvent;

import com.aulajsf.model.entities.Empresa;
import com.aulajsf.model.entities.RamoAtividade;
import com.aulajsf.model.enums.TipoEmpresa;
import com.aulajsf.repository.Empresas;
import com.aulajsf.repository.RamoAtividades;
import com.aulajsf.service.CadastroEmpresaService;
import com.aulajsf.util.FacesMessages;

@Named
@ViewScoped
public class GestaoEmpresasBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Empresas empresas;
	@Inject
	private RamoAtividades ramoAtividades;
	@Inject
	private CadastroEmpresaService cadastroEmpresaService;
	@Inject
	private FacesMessages messages;
	private List<Empresa> listaEmpresas;
	private String termoPesquisa;
	private Converter ramoAtividadeConverter;
	private Empresa empresa;
	
	public void prepararNovaEmpresa() {
		empresa=new Empresa();
	}
	
	private boolean jaHouvePesquisa() {
		return termoPesquisa != null && !"".equals(termoPesquisa);
	}
	
	public void salvar() {
		cadastroEmpresaService.salvar(empresa);
		atualizarRegistros();
		messages.info("Empresa salva com sucesso");
		
		PrimeFaces.current().ajax().update(Arrays.asList("frm:empresasDataTable", "frm:messages"));
		
		
	}
	
	public void pesquisar() {
		listaEmpresas = empresas.pesquisar(termoPesquisa);
		if (listaEmpresas.isEmpty()) {
			messages.info("Sua consulta não retornou registros.");
		}
	}
	
	public void todasEmpresas() {
		listaEmpresas = empresas.todas();
	}
	
	public List<RamoAtividade> completarRamoAtividade(String termo) {
		List<RamoAtividade> listaRamoAtividades = ramoAtividades.pesquisar(termo);
		ramoAtividadeConverter = new RamoAtividadeConverter(listaRamoAtividades);
		return listaRamoAtividades;
	}
	
	public List<Empresa> getListaEmpresas() {
		return listaEmpresas;
	}

	public String getTermoPesquisa() {
		return termoPesquisa;
	}

	public void setTermoPesquisa(String termoPesquisa) {
		this.termoPesquisa = termoPesquisa;
	}
	
	public TipoEmpresa[] getTiposEmpresa() {
		return TipoEmpresa.values();
	}

	public Converter getRamoAtividadeConverter() {
		return ramoAtividadeConverter;
	}

	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public boolean isEmpresaSelecionada() {
		return empresa!=null && empresa.getId()!=null;
	}
	public void onRowUnselect(UnselectEvent<Empresa> event) {
	    empresa = null;
	}
	
	public void prepararEdicao() {
		ramoAtividadeConverter = new RamoAtividadeConverter(Arrays.asList(empresa.getRamoAtividade()));
	}
	
	public void excluir() {
		cadastroEmpresaService.excluir(empresa);
		empresa=null;
		atualizarRegistros();
		messages.info("Empresa excluída com sucesso");
	}
	
	private void atualizarRegistros() {
		if (jaHouvePesquisa()) {
			pesquisar();
		}
		else {
			todasEmpresas();
		}
	}
	
	

	
}
