/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validaciones;

/**
 *
 * @author Cecilia Segura
 */

import javafx.scene.control.TextField;

public class ValidarTexto extends TextField  {
    
    public ValidarTexto(){
        this.setPromptText("Entrodusca su nombre");
    
    }
    
    @Override
    public void replaceText(int i,int il,String string){
    if(string.matches("[a-zA-Z]")|| string.isEmpty()){
        super.replaceText(i, il, string);
       
    } 
     
    
    }
    
    @Override
    public void replaceSelection (String string){
    super.replaceSelection(string);
    
    }
    
    
    
    
}
