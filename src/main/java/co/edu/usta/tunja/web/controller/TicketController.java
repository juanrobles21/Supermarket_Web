/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.usta.tunja.web.controller;

import co.edu.usta.tunja.supermarket.persistence.ejb.TicketFacade;
import co.edu.usta.tunja.supermarket.persistence.entity.TicketEntity;
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
@Named(value = "ticketController")
@RequestScoped
public class TicketController {

    @EJB
    private TicketFacade _ejbFacade;
    private TicketEntity _objActual;
    private Integer fkIdPersonCustomer;
    private Integer fkIdPersonCashier;

    public TicketController() {
    }

    public Integer getFkIdPersonCustomer() {
        return fkIdPersonCustomer;
    }

    public void setFkIdPersonCustomer(Integer fkIdPersonCustomer) {
        this.fkIdPersonCustomer = fkIdPersonCustomer;
    }

    public Integer getFkIdPersonCashier() {
        return fkIdPersonCashier;
    }

    public void setFkIdPersonCashier(Integer fkIdPersonCashier) {
        this.fkIdPersonCashier = fkIdPersonCashier;
    }

    public TicketEntity getCampo() {
        if (this._objActual == null)
        {
            this._objActual = new TicketEntity();

        }
        return this._objActual;

    }

    public TicketFacade getFacade() {
        return this._ejbFacade;
    }

    public SelectItem[] getListadoCombo(String value) {

        return Forms.addObject(getFacade().listar(), value);
    }

    public List<TicketEntity> getTicketListado() {
        return getFacade().listar();
    }

    public String grabarTicket() {
        String texto, detalle;
        try
        {
            texto = "exito";
            this._objActual.setFkIdPersonCustomer(getFkIdPersonCustomer());
            this._objActual.setFkIdPersonCashier(getFkIdPersonCashier());
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
        sesionMap.put("ticket", _objActual);
        return "actualizar";
    }

    public String actualizarTicket() {
        String texto, detail;
        try
        {
            texto = "Actualizado con exito";
            this._objActual.setFkIdPersonCustomer(getFkIdPersonCustomer());
            this._objActual.setFkIdPersonCashier(getFkIdPersonCashier());
            detail = "Actualizado";
            Map<String, Object> sesionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            _objActual.setId(((TicketEntity) sesionMap.get("ticket")).getId());
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

    public String deleteTicket(TicketEntity ticketEntity) {
        this._objActual = ticketEntity;
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

    @FacesConverter(forClass = TicketEntity.class, value = "TicketConverter")
    public static class TicketControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext context, UIComponent componet, String value) {
            try
            {
                int id = Integer.parseInt(value);
                TicketController controlador;
                ELContext contextoExterno = context.getELContext();
                Application contextoAplicacion = context.getApplication();
                String nombreDecoracionControlador = "ticketController";
                controlador = (TicketController) contextoAplicacion.getELResolver().getValue(contextoExterno, null, nombreDecoracionControlador);
                return controlador.getFacade().buscar(id);
            } catch (NumberFormatException error)
            {
                return null;
            }

        }

        @Override
        public String getAsString(FacesContext context, UIComponent componet, Object value) {
            if (value instanceof TicketEntity)
            {
                TicketEntity obj = (TicketEntity) value;
                return String.valueOf(obj.getId());
            }
            return null;
        }
    }
}
