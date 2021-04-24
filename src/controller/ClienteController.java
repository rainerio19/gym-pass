package controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import DB.DBCliente;
import DB.DBEndereco;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Cliente;
import model.Endereco;
import model.ValidaCpf;

public class ClienteController {
	
	@FXML
    private TextField cpf;

    @FXML
    private TextField nome;
    
    @FXML
    private TextField buscCod;

    @FXML
    private TextField nomeCad;
    
    @FXML
    private TextField bairro;
    
    @FXML
    private TextField rua;
    
    @FXML
    private TextField cidade;

    @FXML
    private TextField tel;

    @FXML
    private TextField codCli;

    @FXML
    private TableView<Cliente> tvCliente;

    @FXML
    private Label statusExcluir;
    
    @FXML
    private TextField codCliEdit;
    
    @FXML
    private TextField codCliExcluir;
    
    @FXML
    private TextField nomeEdit;
    
    @FXML
    private TextField cpfEdit;
    
    @FXML
    private TextField telEdit;
    
    @FXML
    private TextField ruaEdit;
    
    @FXML
    private TextField bairroEdit;
    
    @FXML
    private TextField cidadeEdit;
    
    @FXML
	private TableColumn<Cliente, String> tcCodeCli;
    
    @FXML
	private TableColumn<Cliente, String> tcNomeCli;
    
    @FXML
	private TableColumn<Cliente, Integer> tcTelCli;
    
    @FXML
	private TableColumn<Cliente, String> tcCpfCli;
    
    @FXML
	private TableColumn<Endereco, String> tcRuaCli;
    
    @FXML
	private TableColumn<Endereco, String> tcBairroCli;
    
    @FXML
	private TableColumn<Endereco, String> tcCidadeCli;
    
    private ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    
    private Endereco endTemp = null;
    
    private String cpfTemporario = null;
    
    private void limparCampos() {
    	/*Tela cadastrar*/
    	nomeCad.clear();
    	cpf.clear();
    	tel.clear();
    	rua.clear();
    	bairro.clear();
    	cidade.clear();
    	
    	/*Tela editar*/
    	codCliEdit.clear();
    	cpfEdit.clear();
		telEdit.clear();
		ruaEdit.clear();
		bairroEdit.clear();
		cidadeEdit.clear();
		nomeEdit.clear();
		
		/*Tela excluir*/
		codCliExcluir.clear();
	}
    
    private void initTable() {
    	tcCodeCli.setCellValueFactory(new PropertyValueFactory<Cliente, String>("idCliente"));
    	tcNomeCli.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nome"));
    	tcTelCli.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("telefone"));
    	tcCpfCli.setCellValueFactory(new PropertyValueFactory<Cliente, String>("cpf"));
    	tcRuaCli.setCellValueFactory(new PropertyValueFactory<Endereco, String>("rua"));
    	tcBairroCli.setCellValueFactory(new PropertyValueFactory<Endereco, String>("bairro"));
    	tcCidadeCli.setCellValueFactory(new PropertyValueFactory<Endereco, String>("cidade"));
    	tvCliente.setItems(FXCollections.observableArrayList(clientes));
    }
    
    @FXML
    void btnBuscarTab(ActionEvent event) {
		Cliente cli = new Cliente();
		DBCliente DBcli = new DBCliente();
		
		try {
			cli.setIdCliente(Integer.parseInt(buscCod.getText()));
			cli = DBcli.buscaCliente(cli.getIdCliente());
			if(cli != null) {
				clientes.add(cli);
				initTable();
			}else {
				JOptionPane.showMessageDialog(null, "Cliente nao encontrado.");
			}
			
		}catch(Exception ex) {
			System.err.println(ex.getMessage());
		}
    }
    
    @FXML
    void btnBuscarEditar(ActionEvent event) {
    	Cliente cli = new Cliente();
    	DBCliente db_cli =  new DBCliente();
    	
    	try {
    		
    		if(codCliEdit.getText().isEmpty()) {
    			JOptionPane.showMessageDialog(null, "Por favor digite o código do cliente.");
    		}else {
    			cli.setIdCliente(Integer.parseInt(codCliEdit.getText()));
    			cli = db_cli.buscaCliente(cli.getIdCliente());
            	
            	if(cli != null) {
            		JOptionPane.showMessageDialog(null, "Cliente " + cli.getNome()
        			+ " encontrado, altere"
        			+ " somente os dados que deseja.");
            		
            		nomeEdit.setText(cli.getNome());
            		cpfEdit.setText(cli.getCpf());
            		cpfTemporario = cpfEdit.getText();
            		telEdit.setText(String.valueOf(cli.getTelefone()));
            		ruaEdit.setText(cli.getEndereco().getRua());
            		bairroEdit.setText(cli.getEndereco().getBairro());
            		cidadeEdit.setText(cli.getEndereco().getCidade());
            		
            		endTemp = cli.getEndereco();
            	}else {
            		JOptionPane.showMessageDialog(null, "O cliente nao foi encontrado.");
            	}
    		}
    	}catch(Exception ex) {
    		System.err.println(ex.getMessage());
    	}
    }
    
    @FXML
    void btnEditarCli(ActionEvent event) {
    	try {
    		
    		if(ruaEdit.getText().isEmpty() || bairroEdit.getText().isEmpty() ||
    				cidadeEdit.getText().isEmpty() || cpfEdit.getText().isEmpty() ||
    				nomeEdit.getText().isEmpty() || telEdit.getText().isEmpty()){
    			JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
			}else {
				DBEndereco db_end = new DBEndereco();
	    		Endereco end = new Endereco(ruaEdit.getText(), bairroEdit.getText(), cidadeEdit.getText());
	    		end.setIdEndereco(endTemp.getIdEndereco());
	    		db_end.editEndereco(end);
	    		
	    		Cliente cli = new Cliente(cpfEdit.getText(), nomeEdit.getText(),end, Integer.parseInt(telEdit.getText()));
				cli.setIdCliente(Integer.parseInt(codCliEdit.getText()));
				
				DBCliente db_cli = new DBCliente();
				
				Cliente existeCli = db_cli.buscaClienteCpf(cpfEdit.getText());
				
				ValidaCpf cpfValido = new ValidaCpf();
				
				if(cpfValido.isCPF(cpfEdit.getText())) {
				
					if(cpfTemporario.equals(cpfEdit.getText())) {
						if(db_cli.editCliente(cli.getIdCliente(), cli)) {
							JOptionPane.showMessageDialog(null, "Cliente editado com sucesso.");
							limparCampos();
						} else {
							JOptionPane.showMessageDialog(null, "Problema ao editar Cliente. Tente novamente.");
						}
					}else if(existeCli != null){
						JOptionPane.showMessageDialog(null, "Já existe um cliente com esse cpf.");
					}else {
						if(db_cli.editCliente(cli.getIdCliente(), cli)) {
							JOptionPane.showMessageDialog(null, "Cliente editado com sucesso.");
							limparCampos();
						} else {
							JOptionPane.showMessageDialog(null, "Problema ao editar Cliente. Tente novamente.");
						}
					}
				}else {
					JOptionPane.showMessageDialog(null, "Você precisa inserir um CPF válido.");
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
    }

    @FXML
    void btnExcluir(ActionEvent event) {
    	try {

        	DBCliente db_cli = new DBCliente();
        	Cliente cli = new Cliente();
        	if(!codCliExcluir.getText().isEmpty()) {    		
        		cli.setIdCliente(Integer.parseInt(codCliExcluir.getText()));
        		cli = db_cli.buscaCliente(cli.getIdCliente());
        		
        		Alert alert = new Alert(AlertType.CONFIRMATION, "Tem certeza que deseja exluir " + cli.getNome() + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        		alert.showAndWait();
        		
        		if(alert.getResult() == ButtonType.YES) {
        			if(db_cli.deleteCliente(cli.getIdCliente())) {        				
        				JOptionPane.showMessageDialog(null, "Cliente excluido com sucesso.");
        				limparCampos();
        			} else {
        				JOptionPane.showMessageDialog(null, "Ocorreu um problema durante a remocao do Cliente. Tente novamente. Certifique-se que esta inserindo uma ID valido.");
        			}
        		}
        	}else {
        		JOptionPane.showMessageDialog(null, "Insira um ID valido para remover um Cliente.");
        		codCliExcluir.clear();
        	}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ocorreu um problema durante a remocao do Cliente. Tente novamente. Certifique-se que esta inserindo uma ID valido.");
			System.err.println(e.getMessage());
			limparCampos();
		}
    }

    @FXML
    void btnSalvar(ActionEvent event) {
    	try {
    		Endereco end = new Endereco(rua.getText(), bairro.getText(), cidade.getText());
    		DBEndereco db_end = new DBEndereco();
    		db_end.cadEndereco(end);
    		end.setIdEndereco(db_end.buscaUltimoEndereco().getIdEndereco());
			
    		ValidaCpf cpfValido = new ValidaCpf();
    			
        	if(nomeCad.getText().isEmpty() || cpf.getText().isEmpty() ||
        			tel.getText().isEmpty() || rua.getText().isEmpty() ||
       				cidade.getText().isEmpty() || bairro.getText().isEmpty()){
       			JOptionPane.showMessageDialog(null, "Preencha todos os campos.");
    		}else {
   				if(cpfValido.isCPF(cpf.getText())) {
   					Cliente cli = new Cliente(cpf.getText(), nomeCad.getText(), end, Integer.parseInt(tel.getText()));
    	        	DBCliente db_cli = new DBCliente();
    	        		
    	        	Cliente existeCli = db_cli.buscaClienteCpf(cpf.getText());
    				if(existeCli == null) {
    					if (db_cli.cadCliente(cli.getNome(), cli.getCpf(), cli.getTelefone(), cli.getEndereco().getIdEndereco())) {
    						JOptionPane.showMessageDialog(null, "Cliente inserido com sucesso");
    						limparCampos();
    					} else {
    						JOptionPane.showMessageDialog(null, "Cliente nao foi inserido.");
    					}
    				}else {
    					JOptionPane.showMessageDialog(null, "Já existe um cliente com esse cpf.");
    				}	
    			}else {
    	    		JOptionPane.showMessageDialog(null, "Você precisa inserir um CPF válido.");
    	   		}
    		}
    		
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
    }
	
    @FXML 
    void btnBuscarTodosCli(ActionEvent event) {
    	ArrayList<?> todosCli = null;
    	DBCliente db_cli = new DBCliente();
    	try {
			todosCli = db_cli.buscarTodosClientes();
			if(todosCli != null) {
				for (int i = 0; i < todosCli.size(); i++) {
					clientes.add((Cliente) todosCli.get(i));
				}
				initTable();
			}else {
				JOptionPane.showMessageDialog(null, "Nenhum cliente foi encontrado.");
			}
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
    }
    
	@FXML
    void btnVoltar(ActionEvent event) {
    	application.Main.trocarTela("inicial");
    }
}
