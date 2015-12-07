package br.edu.univas.si.tcc.trunp.bi;

import br.edu.univas.si.tcc.trunp.dao.CompanyDAO;
import br.edu.univas.si.tcc.trunp.model.Company;


public class CompanyBi {
	
	private CompanyDAO dao;
	
	public CompanyBi() {
		this.dao = new CompanyDAO();
	}

	public boolean companyAlreadyExist(Company company) {
		return dao.companyAlreadyExist(company);
	}
	
	public void createCompany(Company company) {
		dao.createCompany(company);
	}

}
