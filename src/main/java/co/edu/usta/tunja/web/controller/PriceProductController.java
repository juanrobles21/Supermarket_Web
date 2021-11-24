/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usta.tunja.web.controller;

import co.edu.usta.tunja.supermarket.persistence.ejb.PersonFacade;
import co.edu.usta.tunja.supermarket.persistence.ejb.PriceProductFacade;
import co.edu.usta.tunja.supermarket.persistence.entity.PersonEntity;
import co.edu.usta.tunja.supermarket.persistence.entity.PriceProductEntity;
import co.edu.usta.tunja.web.utility.Forms;
import co.edu.usta.tunja.web.utility.Mensajes;
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
@Named(value = "priceProductController")
@RequestScoped
public class PriceProductController {

    @EJB
    private PriceProductFacade _ejbFacade;
    private PriceProductEntity _objActual;
    private Integer fkIdProductProvider;

    public PriceProductController() {
    }

    public Integer getFkIdProductProvider() {
        return fkIdProductProvider;
    }

    public void setFkIdProductProvider(Integer fkIdProductProvider) {
        this.fkIdProductProvider = fkIdProductProvider;
    }

    public PriceProductEntity getCampo() {
        if (this._objActual == null)
        {
            this._objActual = new PriceProductEntity();

        }
        return this._objActual;

    }

    public PriceProductFacade getFacade() {
        return this._ejbFacade;
    }

    public SelectItem[] getListadoCombo(String value) {

        return Forms.addObject(getFacade().listar(), value);
    }

    public List<PriceProductEntity> getPriceProductListado() {
        return getFacade().listar();
    }

    public String grabarPriceProduct() {
        String texto, detalle;
        try
        {
            texto = "Exito";
            this._objActual.setFkIdProductProvider(getFkIdProductProvider());
            //detalle = ResourceBundle.getBundle("/co/edu/usta/tunja/web/utility/txtsupermarket").getString(texto);
            getFacade().grabar(this._objActual);
            Mensajes.exito(texto, "Exito");
            return "listar";
        } catch (Exception e)
        {
            texto = "Error";
            e.printStackTrace();
            Mensajes.error(texto, "Error");
            return "crear";

        }
    }

    public String cargarID(Integer id) {
        _objActual = getFacade().buscar(id);
        Map<String, Object> sesionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sesionMap.put("priceProduct", _objActual);
        return "actualizar";
    }

    public String actualizarPriceProduct() {

        String texto, detail;
        try
        {
            texto = "Actualizado con exito";
            this._objActual.setFkIdProductProvider(getFkIdProductProvider());
            detail = "Actualizado";
            Map<String, Object> sesionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            _objActual.setId(((PriceProductEntity) sesionMap.get("priceProduct")).getId());
            getFacade().actualizar(_objActual);
            Mensajes.exito(texto, detail);
            return "listar";
        } catch (Exception e)
        {
            texto = "Error";
            detail = "Error";
            Mensajes.error(texto, detail);
            return "actualizar";
        }
    }

    public String deletePriceProduct(PriceProductEntity priceProductEntity) {
        this._objActual = priceProductEntity;
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

    @FacesConverter(forClass = PriceProductEntity.class, value = "PriceProductConverter")
    public static class PriceProductControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent componet, String value) {
            try
            {
                int id = Integer.parseInt(value);
                PriceProductController controlador;
                ELContext contextoExterno = context.getELContext();
                Application contextoAplicacion = context.getApplication();
                String nombreDecoracionControlador = "priceProductController";
                controlador = (PriceProductController) contextoAplicacion.getELResolver().getValue(contextoExterno, null, nombreDecoracionControlador);
                return controlador.getFacade().buscar(id);
            } catch (NumberFormatException error)
            {
                return null;
            }

        }

        @Override
        public String getAsString(FacesContext context, UIComponent componet, Object value) {
            if (value instanceof PriceProductEntity)
            {
                PriceProductEntity obj = (PriceProductEntity) value;
                return String.valueOf(obj.getId());
            }
            return null;
        }
    }

}
