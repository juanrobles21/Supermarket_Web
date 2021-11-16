/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usta.tunja.web.controller;

import co.edu.usta.tunja.supermarket.persistence.ejb.ProviderFacade;
import co.edu.usta.tunja.supermarket.persistence.entity.ProviderEntity;
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
@Named(value = "providerController")
@RequestScoped
public class ProviderController {
    @EJB
    private ProviderFacade _ejbFacade;
    private ProviderEntity _objActual;

    public ProviderController() {
    }
    public ProviderEntity getCampo() {
        if (this._objActual == null)
        {
            this._objActual = new ProviderEntity();

        }
        return this._objActual;

    }

    public ProviderFacade getFacade() {
        return this._ejbFacade;
    }

    public SelectItem[] getListadoCombo(String value) {

        return Forms.addObject(getFacade().listar(), value);
    }

    public List<ProviderEntity> getProviderListado() {
        return getFacade().listar();
    }

    public String grabarProvider() {
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

    @FacesConverter(forClass = ProviderEntity.class, value = "ProviderConverter")
    public static class ProviderControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent componet, String value) {
            try
            {
                int id = Integer.parseInt(value);
                ProviderController controlador;
                ELContext contextoExterno = context.getELContext();
                Application contextoAplicacion = context.getApplication();
                String nombreDecoracionControlador = "providerController";
                controlador = (ProviderController) contextoAplicacion.getELResolver().getValue(contextoExterno, null, nombreDecoracionControlador);
                return controlador.getFacade().buscar(id);
            } catch (NumberFormatException error)
            {
                return null;
            }

        }

        @Override
        public String getAsString(FacesContext context, UIComponent componet, Object value) {
            if (value instanceof ProviderEntity)
            {
                ProviderEntity obj = (ProviderEntity) value;
                return String.valueOf(obj.getId());
            }
            return null;
        }
    }
    
}
