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
import java.io.Serializable;
import java.util.List;
import java.util.Map;
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
public class PersonPersonTypeController implements Serializable {

    @EJB
    private PersonPersonTypeFacade _ejbFacade;
    private PersonPersonTypeEntity _objActual;
    private Integer fkIdPersonType;
    private Integer fkIdPerson;

    public PersonPersonTypeController() {
    }

    public Integer getFkIdPersonType() {
        return fkIdPersonType;
    }

    public void setFkIdPersonType(Integer fkIdPersonType) {
        this.fkIdPersonType = fkIdPersonType;
    }

    public Integer getFkIdPerson() {
        return fkIdPerson;
    }

    public void setFkIdPerson(Integer fkIdPerson) {
        this.fkIdPerson = fkIdPerson;
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
            detalle = "Exito";
            this._objActual.setFkIdPersonType(getFkIdPersonType());
            this._objActual.setFkIdPerson(getFkIdPerson());
            //detalle = ResourceBundle.getBundle("/co/edu/usta/tunja/web/utility/txtsupermarket").getString(texto);
            getFacade().grabar(this._objActual);
            Mensajes.exito(texto, detalle);
            return "listar";
        } catch (Exception e)
        {
            texto = "Error";
            detalle = "Error";
            e.printStackTrace();
            Mensajes.error(texto, detalle);
            return "crear";

        }
    }

    public String cargarID(Integer id) {
        _objActual = getFacade().buscar(id);
        Map<String, Object> sesionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sesionMap.put("personPersonType", _objActual);
        return "actualizar";
    }

    public String actualizarPersonPersonType() {
        String texto, detail;
        try
        {
            texto = "Actualizado con exito";
            this._objActual.setFkIdPersonType(getFkIdPersonType());
            this._objActual.setFkIdPerson(getFkIdPerson());
            detail = "Actualizado";
            Map<String, Object> sesionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            _objActual.setId(((PersonPersonTypeEntity) sesionMap.get("personPersonType")).getId());
            getFacade().actualizar(_objActual);
            Mensajes.exito(texto, detail);
            return "lisatar";
        } catch (Exception e)
        {
            texto = "Error";
            detail = "Error";
            Mensajes.error(texto, detail);
            return "actualizar";
        }
    }

    public String deletePersonPersonType(PersonPersonTypeEntity personPersonTypeEntity) {
        this._objActual = personPersonTypeEntity;
        String text, detail;
        try
        {
            text = "Eliminado con exito";
            detail = "Eliminado";
            Mensajes.exito(text, detail);
            getFacade().borrar(this._objActual);
            return "listar";
        } catch (Exception e)
        {
            text = "No puede ser eliminado";
            detail = e.getMessage();
            Mensajes.error(text, detail);
            return "listar";
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
