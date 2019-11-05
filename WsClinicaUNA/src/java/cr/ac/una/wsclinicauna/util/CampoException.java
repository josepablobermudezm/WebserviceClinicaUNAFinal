/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.una.wsclinicauna.util;

/**
 *
 * @author JORDI RODRIGUEZ
 */
public class CampoException {
    
    public static String getCampo(String textoSQLException, String schemaName, String estandarAtributo, int estandar ){
        String campo = "";
        for(Integer i = 0; i<textoSQLException.length(); i++){
            if(textoSQLException.charAt(i) == '('){
                for(Integer j = i+(schemaName.length() + estandarAtributo.length()+ estandar); j < textoSQLException.length(); j++){
                    if (textoSQLException.charAt(j) == ')') break;
                    else campo += textoSQLException.charAt(j);
                }
                break;
            }
        }
        return campo;
    }
    
}
