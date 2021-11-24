/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usta.tunja.web.controller;

import co.edu.usta.tunja.supermarket.persistence.ejb.ProductFacade;
import co.edu.usta.tunja.supermarket.persistence.entity.ProductEntity;
import co.edu.usta.tunja.supermarket.persistence.entity.ProductTypeEntity;
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
@Named(value = "productController")
@RequestScoped
public class ProductController {

    @EJB
    private ProductFacade _ejbFacade;
    private ProductEntity _objActual;
    private Integer fkId;

    public ProductController() {
    }

    public Integer getFkId() {
        return fkId;
    }

    public void setFkId(Integer fkId) {
        this.fkId = fkId;
    }

    public ProductEntity getCampo() {
        if (this._objActual == null)
        {
            this._objActual = new ProductEntity();

        }
        return this._objActual;

    }

    public ProductFacade getFacade() {
        return this._ejbFacade;
    }

    public SelectItem[] getListadoCombo(String value) {

        return Forms.addObject(getFacade().listar(), value);
    }

    public List<ProductEntity> getProductListado() {
        return getFacade().listar();
    }

    public String grabarProduct() {
        String texto, detalle;
        try
        {
            texto = "exito";
            this._objActual.setFkIdProductType(getFkId());
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
        sesionMap.put("product", _objActual);
        return "actualizar";
    }

    public String actualizarProduct() {

        String texto, detail;
        try
        {
            texto = "Actualizado con exito";
            this._objActual.setFkIdProductType(getFkId());
            detail = "Actualizado";
            Map<String, Object> sesionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            _objActual.setId(((ProductEntity) sesionMap.get("product")).getId());
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

    public String deleteProduct(ProductEntity productEntity) {
        this._objActual = productEntity;
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

    @FacesConverter(forClass = ProductTypeEntity.class, value = "ProductTypeConverter")
    public static class ProductTypeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent componet, String value) {
            try
            {
                int id = Integer.parseInt(value);
                ProductTypeController controlador;
                ELContext contextoExterno = context.getELContext();
                Application contextoAplicacion = context.getApplication();
                String nombreDecoracionControlador = "productTypeController";
                controlador = (ProductTypeController) contextoAplicacion.getELResolver().getValue(contextoExterno, null, nombreDecoracionControlador);
                return controlador.getFacade().buscar(id);
            } catch (NumberFormatException error)
            {
                return null;
            }

        }

        @Override
        public String getAsString(FacesContext context, UIComponent componet, Object value) {
            if (value instanceof ProductTypeEntity)
            {
                ProductTypeEntity obj = (ProductTypeEntity) value;
                return String.valueOf(obj.getId());
            }
            return null;
        }
    }

}
