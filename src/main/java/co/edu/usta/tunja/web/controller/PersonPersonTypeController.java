/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usta.tunja.web.controller;

import co.edu.usta.tunja.supermarket.persistence.ejb.PersonPersonTypeFacade;
import co.edu.usta.tunja.supermarket.persistence.entity.PersonPersonTypeEntity;
import co.edu.usta.tunja.web.utility.Forms;
import co.edu.usta.tunja.web.utility.Mensajes;
import java.util.List;
import javax.ejb.EJB;
import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.SelectItem;
import javax.inject.Named;

/**
 *
 * @author USUARIO
 */
@Named(value = "personPersonTypeController")
@RequestScoped
public class PersonPersonTypeController {

    @EJB
    private PersonPersonTypeFacade _ejbFacade;
    private PersonPersonTypeEntity _objActual;

    public PersonPersonTypeController() {
    }

    public PersonPersonTypeEntity getCampo() {
        if (this._objActual == null)
        {
            this._objActual = new PersonPersonTypeEntity();

        }
        return this._objActual;

    }
     public PersonPersonTypeFacade getFacade() {
        return this._ejbFacade;
    }

    public SelectItem[] getListadoCombo(String value) {

        return Forms.addObject(getFacade().listar(), value);
    }

    public List<PersonPersonTypeEntity> getPersonPersonTypeListado() {
        return getFacade().listar();
    }

    public String grabarPersonPersonType() {
        String texto, detalle;
        try
        {
            texto = "exito";
            //detalle = ResourceBundle.getBundle("/co/edu/usta/tunja/web/utility/txtsupermarket").getString(texto);
            getFacade().grabar(this._objActual);
            Mensajes.exito(texto, "Exito");
            return "crear";
        } catch (Exception e)
        {
            texto = "Error";
            e.printStackTrace();
            //Mensajes.error(texto, detalle);
            return "crear";

        }
    }
    //**********Interface Converter *********//

    @FacesConverter(forClass = PersonPersonTypeEntity.class, value = "PersonPersonTypeConverter")
    public static class PersonPersonTypeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent componet, String value) {
            try
            {
                int id = Integer.parseInt(value);
                PersonPersonTypeController controlador;
                ELContext contextoExterno = context.getELContext();
                Application contextoAplicacion = context.getApplication();
                String nombreDecoracionControlador = "personPersonTypeController";
                controlador = (PersonPersonTypeController) contextoAplicacion.getELResolver().getValue(contextoExterno, null, nombreDecoracionControlador);
                return controlador.getFacade().buscar(id);
            } catch (NumberFormatException error)
            {
                return null;
            }

        }

        @Override
        public String getAsString(FacesContext context, UIComponent componet, Object value) {
            if (value instanceof PersonPersonTypeEntity)
            {
                PersonPersonTypeEntity obj = (PersonPersonTypeEntity) value;
                return String.valueOf(obj.getId());
            }
            return null;
        }
    }
}
