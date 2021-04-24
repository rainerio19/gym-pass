package controller;

import javax.swing.JOptionPane;

import DB.DBFuncionario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import model.Funcionario;
import model.ValidaCpf;

public class CadastroFuncionarioController {
	@FXML
	private TextField cadNome;
	
	@FXML
	private TextField salario;

	@FXML
    private TextField cpf;
	
	@FXML
    private TextField user;

    @FXML
    private TextField senha;

    private void limparCampos() {
		cadNome.clear();
		user.clear();
		senha.clear();
		cpf.clear();
		salario.clear();
	}
    
    
    @FXML
	void btnSalvar(ActionEvent event) {
		
		try {
			if(cadNome.getText().isEmpty() || user.getText().isEmpty() || senha.getText().isEmpty() || cpf.getText().isEmpty() || salario.getText().isEmpty()) {				
				JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
			} else {
				Funcionario func = new Funcionario(cadNome.getText(), user.getText(),
						senha.getText(), cpf.getText(), Float.parseFloat(salario.getText()));
				
				DBFuncionario DBfunc = new DBFuncionario();
				
				ValidaCpf cpfValido = new ValidaCpf();
				if(cpfValido.isCPF(cpf.getText())) {
					if(DBfunc.buscaFuncionarioCpf(cpf.getText()) == null) {
						if(DBfunc.cadFuncionario(func.getNome(), func.getCpf(),func.getSalario(), func.getUsuario(), func.getSenha())) {
							
							JOptionPane.showMessageDialog(null, "Funcionario inserido com sucesso");
						}else {
							JOptionPane.showMessageDialog(null, "Funcionario nao foi inserido.");
						}
						limparCampos();
					}else {
						JOptionPane.showMessageDialog(null, "Um funcionario com esse CPF ja existe");
					}
				}else {
					JOptionPane.showMessageDialog(null, "Você precisa inserir um CPF válido.");
				}
			}
		}catch(Exception ex) {
			System.err.println(ex.getMessage());
		}
	}
    
    @FXML
    void btnVoltar(ActionEvent event) {
		application.Main.trocarTela("login");
    }
}