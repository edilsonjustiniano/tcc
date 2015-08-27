package br.edu.univas.si.tcc.trunp.controller;

import br.edu.univas.si.tcc.trunp.bi.CompanyBi;
import br.edu.univas.si.tcc.trunp.model.Company;

public class CompanyController {
	
	private CompanyBi companyBi;
	
	public CompanyController() {
		this.companyBi = new CompanyBi();
	}
	
	public boolean companyAlreadyExist(Company company) {
		return companyBi.companyAlreadyExist(company);
	}
	
	public void createCompany(Company company) {
		companyBi.createCompany(company);
	}

}
