/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usta.tunja.web.controller;

import co.edu.usta.tunja.supermarket.persistence.ejb.TaxPriceProductFacade;
import co.edu.usta.tunja.supermarket.persistence.entity.TaxPriceProductEntity;
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
@Named(value = "taxPriceProductController")
@RequestScoped
public class TaxPriceProductController {

    @EJB
    private TaxPriceProductFacade _ejbFacade;
    private TaxPriceProductEntity _objActual;
    private Integer fkIdPriceProduct;
    private Integer fkIdTax;

    public TaxPriceProductController() {
    }

    public Integer getFkIdPriceProduct() {
        return fkIdPriceProduct;
    }

    public void setFkIdPriceProduct(Integer fkIdPriceProduct) {
        this.fkIdPriceProduct = fkIdPriceProduct;
    }

    public Integer getFkIdTax() {
        return fkIdTax;
    }

    public void setFkIdTax(Integer fkIdTax) {
        this.fkIdTax = fkIdTax;
    }

    public TaxPriceProductEntity getCampo() {
        if (this._objActual == null)
        {
            this._objActual = new TaxPriceProductEntity();

        }
        return this._objActual;

    }

    public TaxPriceProductFacade getFacade() {
        return this._ejbFacade;
    }

    public SelectItem[] getListadoCombo(String value) {

        return Forms.addObject(getFacade().listar(), value);
    }

    public List< TaxPriceProductEntity> getTaxPriceProductListado() {
        return getFacade().listar();
    }

    public String grabarTaxPriceProduct() {
        String texto, detalle;
        try
        {
            texto = "exito";
            detalle = "Exito";
            this._objActual.setFkIdPriceProduct(getFkIdPriceProduct());
            this._objActual.setFkIdTax(getFkIdTax());
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
        sesionMap.put("taxPriceProduct", _objActual);
        return "actualizar";
    }

    public String actualizarPersonPersonType() {
        String texto, detail;
        try
        {
            texto = "Actualizado con exito";
            this._objActual.setFkIdPriceProduct(getFkIdPriceProduct());
            this._objActual.setFkIdTax(getFkIdTax());;
            detail = "Actualizado";
            Map<String, Object> sesionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            _objActual.setId(((TaxPriceProductEntity) sesionMap.get("taxPriceProduct")).getId());
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

    public String deleteTaxPriceProduct(TaxPriceProductEntity taxPriceProductEntity) {
        this._objActual = taxPriceProductEntity;
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

    @FacesConverter(forClass = TaxPriceProductEntity.class, value = "TaxPriceProductConverter")
    public static class TaxPriceProductControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent componet, String value) {
            try
            {
                int id = Integer.parseInt(value);
                TaxPriceProductController controlador;
                ELContext contextoExterno = context.getELContext();
                Application contextoAplicacion = context.getApplication();
                String nombreDecoracionControlador = "taxPriceProductController";
                controlador = (TaxPriceProductController) contextoAplicacion.getELResolver().getValue(contextoExterno, null, nombreDecoracionControlador);
                return controlador.getFacade().buscar(id);
            } catch (NumberFormatException error)
            {
                return null;
            }

        }

        @Override
        public String getAsString(FacesContext context, UIComponent componet, Object value) {
            if (value instanceof TaxPriceProductEntity)
            {
                TaxPriceProductEntity obj = (TaxPriceProductEntity) value;
                return String.valueOf(obj.getId());
            }
            return null;
        }
    }

}
