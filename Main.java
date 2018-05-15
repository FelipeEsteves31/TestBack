import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class Main {
    
    private String strId_customer,strCpf_cnpj,strNm_customer,strIs_active,strVl_total;
    private String Concat;
    java.sql.Connection Conexao;
    java.sql.Statement Comando;
    java.sql.ResultSet rsId_customer;
    
    //Funçao responsavel pela conaxao
    
    private void Conecta(){
        try{
            DriverManager.getConnection("jdbc:ucanaccess://C:\\TestBack\\TesteBackEnd\bd_customer_account");   
        }catch(Exception Excecao){
            JOptionPane.showMessageDialog(null,"SQLException: " + Excecao.getMessage(),"Erro: carregamento do banco de dados",JOptionPane.INFORMATION_MESSAGE);
        }
        
        try{
            Conexao = DriverManager.getConnection("jdbc:odbc:bd_customer_account","","");
            Comando = Conexao.createStatement();
            
        }catch(Exception Excecao){
            JOptionPane.showMessageDialog(null,"SQLException: " + Excecao.getMessage(),"Erro: Problema ao conectar",JOptionPane.INFORMATION_MESSAGE);
        }      
    }
    
    private void InsereDados(){
	do{
	   strId_customer = (String)JOptionPane.
	   showInputDialog("Digite o ID do Cliente:");
	  }while(strId_customer == null);
        
	do{
	   strCpf_cnpj = (String)JOptionPane.showInputDialog("Digite o CPF ou CNPJ do Cliente:");
	  }while(strCpf_cnpj == null);
        
	do{
	   strNm_customer = (String)JOptionPane.showInputDialog("Digite o nome do Cliente:");
	  }while(strNm_customer == null);
        
        do{
	   strIs_active = (String)JOptionPane.showInputDialog("O Cliente esta ativo ou inativo?");
	  }while(strIs_active == null);
                
         do{
	   strVl_total = (String)JOptionPane.
	   showInputDialog("Digite o saldo total do Cliente:");
	  }while(strVl_total == null);
    } 
    
    private void Insert(){
	if ((strId_customer != null)&&(strCpf_cnpj != null)&&(strNm_customer != null)&&(strIs_active != null)&&(strVl_total != null)){
		try{
	    PreparedStatement strComandoSQL ;
	    strComandoSQL = Conexao.prepareStatement(
		"INSERT INTO tb_customer_account(id_customer,cpf_cnpj,nm_customer,is_active,vl_total)" +
		" VALUES(?,?,?,?,?)");
	    strComandoSQL.setString(1,strId_customer);                                  
	    strComandoSQL.setString(2,strCpf_cnpj);
	    strComandoSQL.setString(3,strNm_customer);
            strComandoSQL.setString(4,strIs_active);
            strComandoSQL.setString(5,strVl_total);
	    int intRegistro = strComandoSQL.executeUpdate();
            
	    if (intRegistro != 0){
	    JOptionPane.showMessageDialog(null,"Registro adicionado !",
					"Mensagem",JOptionPane.INFORMATION_MESSAGE);
		Comando.close();
		Conexao.close();
	}else{
	    JOptionPane.showMessageDialog(null,"Registro nao adicionado !",
					"Mensagem",JOptionPane.INFORMATION_MESSAGE);
		Comando.close();
		Conexao.close();
	}
    }catch (Exception Excecao){
	    JOptionPane.showMessageDialog(null,"SQLException: " +
	    Excecao.getMessage(),"Erro: Selecao de registro",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}else{
	    JOptionPane.showMessageDialog(null,"Falta Preencher Campo!",
			                "Mensagem",JOptionPane.INFORMATION_MESSAGE);
	}
    }
 
    private void Consulta(){
	do{
		strId_customer = (String)JOptionPane.showInputDialog("Digite ID do Cliente:");
	}while (strId_customer == null);

	if (strId_customer != null) {
	 try {       
		 PreparedStatement strComandoSQL ;
		 strComandoSQL = Conexao.
				prepareStatement("SELECT * FROM tb_customer_account WHERE vl_total > 560 AND id_customer BETWEEN 1500 AND 2700");
				
		 strComandoSQL.setString(1,strId_customer );
		 rsId_customer = strComandoSQL.executeQuery();
		 if (rsId_customer.next()) {
			Concat =  "ID do Cliente: " +rsId_customer.getString("id_customer") 
			+ "\nCPF ou CNPJ do Cliente: " +rsId_customer.getString("cpf_cnpj")
			+ "\nNome do Cliente: "+rsId_customer.getString("nm_customer")
                        + "\nO Cliente esta ativo ou inativo: "+rsId_customer.getString("is_active")
                        + "\nSaldo total do Cliente: "+rsId_customer.getString("vl_total");
			JOptionPane.showMessageDialog(null,Concat,"ID",JOptionPane.INFORMATION_MESSAGE);

		 }else
		  JOptionPane.showMessageDialog(null,"Registro nao encontrado !",
					"Mensagem",JOptionPane.INFORMATION_MESSAGE);
		 Comando.close();
		 Conexao.close();
	 }
	 catch (Exception Excecao) {
		   JOptionPane.showMessageDialog(null,"SQLException: " +
		   Excecao.getMessage(),"Erro: Selecao de registro",
JOptionPane.INFORMATION_MESSAGE);
	   }
         
       }
        
   }
    
    
    private void Menu(){
	String strOpcao;
	JOptionPane.showMessageDialog(null,"Programa para Cadastro de Clientes!",
	"Mensagem",JOptionPane.INFORMATION_MESSAGE);
	JOptionPane.showMessageDialog(null,"       Digite: \n\n1 - Para Cadastrar \n2 - Para Consultar \n3 - Para Sair",
	"Mensagem",JOptionPane.INFORMATION_MESSAGE);
	do
	{
	 strOpcao = (String)JOptionPane.showInputDialog("Entre com a Opção:");
	}while(strOpcao == null);

	if (Integer.parseInt(strOpcao)== 1 )
	{
		InsereDados();
		Insert();
	}
	else{
		if (Integer.parseInt(strOpcao)== 2 )
		{
			Consulta();
		}
		else
		{	
			Object Botoes[] = {"  Sim  ","  Nao  "};
			int intResposta = JOptionPane.showOptionDialog(null,
			"Deseja mesmo sair ?","Saida",JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE,null,Botoes,Botoes[1]);
			if (intResposta == 0)
			{
				System.exit(0);
			}else{
				Menu();
			}                
		   }
	    }  
    }
    
    private void Executa() {        
	Conecta();//Aqui é chamada a função responsável pela conexão
	Menu();                
    }
    
    public static void main(String[] args){
        Main main = new Main();
	main.Executa();
        
    }
    
}
