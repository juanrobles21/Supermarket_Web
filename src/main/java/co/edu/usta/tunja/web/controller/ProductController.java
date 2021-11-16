/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usta.tunja.web.controller;

import co.edu.usta.tunja.supermarket.persistence.ejb.ProductFacade;
import co.edu.usta.tunja.supermarket.persistence.entity.ProductEntity;
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
@Named(value = "productController")
@RequestScoped
public class ProductController {

    @EJB
    private ProductFacade _ejbFacade;
    private ProductEntity _objActual;

    public ProductController() {
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

    @FacesConverter(forClass = ProductEntity.class, value = "ProductConverter")
    public static class ProductControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent componet, String value) {
            try
            {
                int id = Integer.parseInt(value);
                ProductController controlador;
                ELContext contextoExterno = context.getELContext();
                Application contextoAplicacion = context.getApplication();
                String nombreDecoracionControlador = "personController";
                controlador = (ProductController) contextoAplicacion.getELResolver().getValue(contextoExterno, null, nombreDecoracionControlador);
                return controlador.getFacade().buscar(id);
            } catch (NumberFormatException error)
            {
                return null;
            }

        }

        @Override
        public String getAsString(FacesContext context, UIComponent componet, Object value) {
            if (value instanceof ProductEntity)
            {
                ProductEntity obj = (ProductEntity) value;
                return String.valueOf(obj.getId());
            }
            return null;
        }
    }
    
}
