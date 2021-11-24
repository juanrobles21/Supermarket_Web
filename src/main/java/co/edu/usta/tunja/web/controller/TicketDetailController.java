/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usta.tunja.web.controller;

import co.edu.usta.tunja.supermarket.persistence.ejb.TicketDetailFacade;
import co.edu.usta.tunja.supermarket.persistence.entity.TicketDetailEntity;
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
@Named(value = "ticketDetailController")
@RequestScoped
public class TicketDetailController {

    @EJB
    private TicketDetailFacade _ejbFacade;
    private TicketDetailEntity _objActual;
    private Integer fkIdTicket;
    private Integer fkIdTaxPriceProduct;
    private Integer fkIdPersonAdministrator;

    public TicketDetailController() {
    }

    public Integer getFkIdTicket() {
        return fkIdTicket;
    }

    public void setFkIdTicket(Integer fkIdTicket) {
        this.fkIdTicket = fkIdTicket;
    }

    public Integer getFkIdTaxPriceProduct() {
        return fkIdTaxPriceProduct;
    }

    public void setFkIdTaxPriceProduct(Integer fkIdTaxPriceProduct) {
        this.fkIdTaxPriceProduct = fkIdTaxPriceProduct;
    }

    public Integer getFkIdPersonAdministrator() {
        return fkIdPersonAdministrator;
    }

    public void setFkIdPersonAdministrator(Integer fkIdPersonAdministrator) {
        this.fkIdPersonAdministrator = fkIdPersonAdministrator;
    }

    public TicketDetailEntity getCampo() {
        if (this._objActual == null)
        {
            this._objActual = new TicketDetailEntity();

        }
        return this._objActual;

    }

    public TicketDetailFacade getFacade() {
        return this._ejbFacade;
    }

    public SelectItem[] getListadoCombo(String value) {

        return Forms.addObject(getFacade().listar(), value);
    }

    public List<TicketDetailEntity> getTicketDetailListado() {
        return getFacade().listar();
    }

    public String grabarTicketDetail() {
        String texto, detalle;
        try
        {
            texto = "exito";
            this._objActual.setFkIdTicket(getFkIdTicket());
            this._objActual.setFkIdTaxPriceProduct(getFkIdTaxPriceProduct());
            this._objActual.setFkIdPersonAdministrator(getFkIdPersonAdministrator());
            //detalle = ResourceBundle.getBundle("/co/edu/usta/tunja/web/utility/txtsupermarket").getString(texto);
            getFacade().grabar(this._objActual);
            Mensajes.exito(texto, "Exito");
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
        sesionMap.put("ticketDetail", _objActual);
        return "actualizar";
    }

    public String actualizarTicketDetail() {
        String texto, detail;
        try
        {
            texto = "Actualizado con exito";
            this._objActual.setFkIdTicket(getFkIdTicket());
            this._objActual.setFkIdTaxPriceProduct(getFkIdTaxPriceProduct());
            this._objActual.setFkIdPersonAdministrator(getFkIdPersonAdministrator());
            detail = "Actualizado";
            Map<String, Object> sesionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            _objActual.setId(((TicketDetailEntity) sesionMap.get("ticketDetail")).getId());
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

    public String deleteTicketDetail(TicketDetailEntity ticketDetailEntity) {
        this._objActual = ticketDetailEntity;
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

    @FacesConverter(forClass = TicketDetailEntity.class, value = "TicketDetailConverter")
    public static class TicketDetailControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent componet, String value) {
            try
            {
                int id = Integer.parseInt(value);
                TicketDetailController controlador;
                ELContext contextoExterno = context.getELContext();
                Application contextoAplicacion = context.getApplication();
                String nombreDecoracionControlador = "ticketDetailController";
                controlador = (TicketDetailController) contextoAplicacion.getELResolver().getValue(contextoExterno, null, nombreDecoracionControlador);
                return controlador.getFacade().buscar(id);
            } catch (NumberFormatException error)
            {
                return null;
            }

        }

        @Override
        public String getAsString(FacesContext context, UIComponent componet, Object value) {
            if (value instanceof TicketDetailEntity)
            {
                TicketDetailEntity obj = (TicketDetailEntity) value;
                return String.valueOf(obj.getId());
            }
            return null;
        }
    }
}
