package isadora.rangovoice;

import java.util.ArrayList;

/**
 * Created by Isadora on 5/20/16.
 */
public class Receita {

    private String nome;
    private ArrayList<String> ingredientes;
    private ArrayList<String> modo_preparo;

    public Receita(String nome, ArrayList<String> ingredientes, ArrayList<String> preparo){
        this.nome = nome;
        this.ingredientes = ingredientes;
        this.modo_preparo = preparo;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public ArrayList<String> getIngredientes(){
        return this.ingredientes;
    }

    public void setIngredientes(ArrayList<String> ing){
        this.ingredientes = ing;
    }

    public ArrayList<String> getModoPreparo(){
        return this.modo_preparo;
    }

    public void setModoPreparo(ArrayList<String> preparo){
        this.modo_preparo = preparo;
    }
}
