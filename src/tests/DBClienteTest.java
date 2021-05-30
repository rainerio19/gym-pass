package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DB.DBCliente;
import DB.DBEndereco;
import model.Cliente;
import model.Endereco;

class DBClienteTest {
	DBCliente db_cli;
	DBEndereco db_end;
	Cliente cli;
	Endereco end;
	
	//Testar Endereco primeiro
	@BeforeAll
	static void setMock() throws Exception {
		DBCliente bdMock = new DBCliente();
		Cliente cliMock = new Cliente();
		DBEndereco db_endMock = new DBEndereco();
		cliMock.setNome("Teste");
		cliMock.setTelefone(998785855);
		cliMock.setCpf("70318274426");
		Endereco endMock = new Endereco();
		endMock.setIdEndereco(db_endMock.buscaUltimoEndereco().getIdEndereco());
		cliMock.setEndereco(endMock);
		bdMock.cadCliente(cliMock.getNome(), cliMock.getCpf(), cliMock.getTelefone(), cliMock.getEndereco().getIdEndereco());
	}
	
	@BeforeEach
	void setUp() throws Exception {
		db_end = new DBEndereco();
		db_cli = new DBCliente();
		cli = new Cliente();
		cli.setNome("Test");
		cli.setTelefone(998785855);
		cli.setCpf("70318274426");
		end = new Endereco();
		end.setIdEndereco(db_end.buscaUltimoEndereco().getIdEndereco());
		cli.setEndereco(end);
		cli.setIdCliente(db_cli.buscaUltimoCliente().getIdCliente());
	}

	@Test
	void cadCli() {
		assertEquals(true, db_cli.cadCliente(cli.getNome(), cli.getCpf(), cli.getTelefone(), cli.getEndereco().getIdEndereco()));
	}
	
	@Test
	void editCli() {
		assertEquals(true, db_cli.editCliente(cli.getIdCliente(), cli));
	}
	
	@Test
	void buscaUltimoCli() {
		assertTrue(db_cli.buscaUltimoCliente() instanceof Cliente);
	}
	
	@Test
	void buscarTodosCli() {
		assertTrue(db_cli.buscarTodosClientes() instanceof ArrayList<?>);
		assertTrue(db_cli.buscarTodosClientes().size() > 0);
	}
	
	
	@Test
	void delCli() {
		assertEquals(true, db_cli.deleteCliente(cli.getIdCliente()));
	}
	
	@Test
	void buscaCli() {
		assertTrue(db_cli.buscaCliente(cli.getIdCliente()) instanceof Cliente);
	}
	
}
