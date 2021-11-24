/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usta.tunja.web.controller;

import co.edu.usta.tunja.supermarket.persistence.ejb.ProductProviderFacade;
import co.edu.usta.tunja.supermarket.persistence.entity.ProductProviderEntity;
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
@Named(value = "productProviderController")
@RequestScoped
public class ProductProviderController {

    @EJB
    private ProductProviderFacade _ejbFacade;
    private ProductProviderEntity _objActual;
    private Integer fkIdProduct;
    private Integer fkIdProvider;

    public ProductProviderController() {
    }

    public Integer getFkIdProduct() {
        return fkIdProduct;
    }

    public void setFkIdProduct(Integer fkIdProduct) {
        this.fkIdProduct = fkIdProduct;
    }

    public Integer getFkIdProvider() {
        return fkIdProvider;
    }

    public void setFkIdProvider(Integer fkIdProvider) {
        this.fkIdProvider = fkIdProvider;
    }

    public ProductProviderEntity getCampo() {
        if (this._objActual == null)
        {
            this._objActual = new ProductProviderEntity();

        }
        return this._objActual;

    }

    public ProductProviderFacade getFacade() {
        return this._ejbFacade;
    }

    public SelectItem[] getListadoCombo(String value) {

        return Forms.addObject(getFacade().listar(), value);
    }

    public List<ProductProviderEntity> getProductProviderListado() {
        return getFacade().listar();
    }

    public String grabarProductProvider() {
        String texto, detalle;
        try
        {
            texto = "exito";
            this._objActual.setFkIdProduct(getFkIdProduct());
            this._objActual.setFkIdProvider(getFkIdProvider());
            //detalle = ResourceBundle.getBundle("/co/edu/usta/tunja/web/utility/txtsupermarket").getString(texto);
            getFacade().grabar(this._objActual);
            Mensajes.exito(texto, "Exito");
            return "listar";
        } catch (Exception e)
        {
            texto = "Error";
            e.printStackTrace();
            //Mensajes.error(texto, detalle);
            return "crear";

        }
    }

    public String cargarID(Integer id) {
        _objActual = getFacade().buscar(id);
        Map<String, Object> sesionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        sesionMap.put("productProvider", _objActual);
        return "actualizar";
    }

    public String actualizarProductProvider() {

        String texto, detail;
        try
        {
            texto = "Actualizado con exito";
            this._objActual.setFkIdProduct(getFkIdProduct());
            this._objActual.setFkIdProvider(getFkIdProvider());
            detail = "Actualizado";
            Map<String, Object> sesionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            _objActual.setId(((ProductProviderEntity) sesionMap.get("productProvider")).getId());
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

    public String deleteProductProvider(ProductProviderEntity productProviderEntity) {
        this._objActual = productProviderEntity;
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

    @FacesConverter(forClass = ProductProviderEntity.class, value = "ProductProviderConverter")
    public static class ProductProviderControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent componet, String value) {
            try
            {
                int id = Integer.parseInt(value);
                ProductProviderController controlador;
                ELContext contextoExterno = context.getELContext();
                Application contextoAplicacion = context.getApplication();
                String nombreDecoracionControlador = "productProviderController";
                controlador = (ProductProviderController) contextoAplicacion.getELResolver().getValue(contextoExterno, null, nombreDecoracionControlador);
                return controlador.getFacade().buscar(id);
            } catch (NumberFormatException error)
            {
                return null;
            }

        }

        @Override
        public String getAsString(FacesContext context, UIComponent componet, Object value) {
            if (value instanceof ProductProviderEntity)
            {
                ProductProviderEntity obj = (ProductProviderEntity) value;
                return String.valueOf(obj.getId());
            }
            return null;
        }
    }

}
