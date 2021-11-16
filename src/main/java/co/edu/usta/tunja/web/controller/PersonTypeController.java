/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usta.tunja.web.controller;

import co.edu.usta.tunja.supermarket.persistence.ejb.PersonTypeFacade;
import co.edu.usta.tunja.supermarket.persistence.entity.PersonTypeEntity;
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
@Named(value = "personTypeController")
@RequestScoped
public class PersonTypeController {

    @EJB
    private PersonTypeFacade _ejbFacade;
    private PersonTypeEntity _objActual;

    public PersonTypeController() {
    }

    public PersonTypeEntity getCampo() {
        if (this._objActual == null)
        {
            this._objActual = new PersonTypeEntity();

        }
        return this._objActual;

    }

    public PersonTypeFacade getFacade() {
        return this._ejbFacade;
    }

    public SelectItem[] getListadoCombo(String value) {

        return Forms.addObject(getFacade().listar(), value);
    }

    public List<PersonTypeEntity> getPersonTypeListado() {
        return getFacade().listar();
    }

    public String grabarPersonType() {
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

    @FacesConverter(forClass = PersonTypeEntity.class, value = "PersonTypeConverter")
    public static class PersonTypeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent componet, String value) {
            try
            {
                int id = Integer.parseInt(value);
                PersonTypeController controlador;
                ELContext contextoExterno = context.getELContext();
                Application contextoAplicacion = context.getApplication();
                String nombreDecoracionControlador = "personTypeController";
                controlador = (PersonTypeController) contextoAplicacion.getELResolver().getValue(contextoExterno, null, nombreDecoracionControlador);
                return controlador.getFacade().buscar(id);
            } catch (NumberFormatException error)
            {
                return null;
            }

        }

        @Override
        public String getAsString(FacesContext context, UIComponent componet, Object value) {
            if (value instanceof PersonTypeEntity)
            {
                PersonTypeEntity obj = (PersonTypeEntity) value;
                return String.valueOf(obj.getId());
            }
            return null;
        }
    }

}
