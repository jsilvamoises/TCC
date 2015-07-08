/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.List;
import model.Dado;
import repository.Repository;


/**
 *
 * @author MOISES
 */
public class UltimosDados {
    private boolean isSavingInDatabase =false;
    private List<Dado> dados = new ArrayList<>();
    private Repository repository = new Repository();
    private static  UltimosDados instance;
    private Dado dado = new Dado();
    
    public static UltimosDados getInstance(){
        if(instance == null){
            instance = new UltimosDados(); 
            
        }
        return instance;
    }
    
    public void add(Dado d){
        //Adiciona a ultima informação vinda da porta na lista na primeira posição
        dados.add(0, d);
        //seta o objeto dado com os dados da ultima coleta
        try {
            dado = dados.get(0);
        } catch (Exception e) {
            
        }
        
        // virifica se o tamanho da lista e maior que vinte e remove o registro mais antigo
        if(dados.size()>=20){
            System.out.println("Removendo "+dados.size());
            dados.remove(20);
        }
        //Verifica se é para salvar os dados no banco
        if(true!=isSavingInDatabase){
        } else {
            repository.salvar(d);
        }
        
        
    }

    public List<Dado> getDados() {
        return dados;
    }

    public void setDados(List<Dado> dados) {
        this.dados = dados;
    }

    public boolean isIsSavingInDatabase() {
        return isSavingInDatabase;
    }

    public void setIsSavingInDatabase(boolean isSavingInDatabase) {
        this.isSavingInDatabase = isSavingInDatabase;
    }

    public Dado getDado() {
        return dado;
    }
    
    
    
    
}
