package br.edu.univas.si.tcc.trunp.controller;

import java.util.ArrayList;

import br.edu.univas.si.tcc.trunp.model.Cliente;

/**
 * 
 * @author edilson
 *
 */
public class ClienteController {

	private ArrayList<Cliente> clientes;
	
	public ArrayList<Cliente> listartTodos() {
		
		clientes = new ArrayList<Cliente>();
		
		Cliente c1 = new Cliente();
		c1.setId(1);
		c1.setName("Edilson");
		
		Cliente c2 = new Cliente();
		c2.setId(2);
		c2.setName("Josy");
		
		clientes.add(c1);
		clientes.add(c2);
		
		return clientes;
		
	}
}
