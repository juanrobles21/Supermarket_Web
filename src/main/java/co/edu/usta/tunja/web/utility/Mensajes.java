/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usta.tunja.web.utility;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author USUARIO
 */
public class Mensajes {

    public static void error(String text, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, text, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public static void exito(String text, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, text, detail);
        FacesContext.getCurrentInstance().addMessage("SucessInfo", message);

    }
}
