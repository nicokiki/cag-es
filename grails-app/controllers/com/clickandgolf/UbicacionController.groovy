package com.clickandgolf

import grails.plugins.springsecurity.Secured;

import org.springframework.dao.DataIntegrityViolationException


/**
 * Solo los ADMIN pueden acceder aca.<br>
 * 
 * @author manzano
 */
@Secured(['ROLE_ADMIN'])
class UbicacionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [ubicacionInstanceList: Ubicacion.list(params), ubicacionInstanceTotal: Ubicacion.count()]
    }

    def create() {
        [ubicacionInstance: new Ubicacion(params)]
    }

    def save() {
        def ubicacionInstance = new Ubicacion(params)
        if (!ubicacionInstance.save(flush: true)) {
            render(view: "create", model: [ubicacionInstance: ubicacionInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'ubicacion.label', default: 'Ubicacion'), ubicacionInstance.id])
        redirect(action: "show", id: ubicacionInstance.id)
    }

    def show() {
        def ubicacionInstance = Ubicacion.get(params.id)
        if (!ubicacionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'ubicacion.label', default: 'Ubicacion'), params.id])
            redirect(action: "list")
            return
        }

        [ubicacionInstance: ubicacionInstance]
    }

    def edit() {
        def ubicacionInstance = Ubicacion.get(params.id)
        if (!ubicacionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ubicacion.label', default: 'Ubicacion'), params.id])
            redirect(action: "list")
            return
        }

        [ubicacionInstance: ubicacionInstance]
    }

    def update() {
        def ubicacionInstance = Ubicacion.get(params.id)
        if (!ubicacionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'ubicacion.label', default: 'Ubicacion'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (ubicacionInstance.version > version) {
                ubicacionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'ubicacion.label', default: 'Ubicacion')] as Object[],
                          "Another user has updated this Ubicacion while you were editing")
                render(view: "edit", model: [ubicacionInstance: ubicacionInstance])
                return
            }
        }

        ubicacionInstance.properties = params

        if (!ubicacionInstance.save(flush: true)) {
            render(view: "edit", model: [ubicacionInstance: ubicacionInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'ubicacion.label', default: 'Ubicacion'), ubicacionInstance.id])
        redirect(action: "show", id: ubicacionInstance.id)
    }

    def delete() {
        def ubicacionInstance = Ubicacion.get(params.id)
        if (!ubicacionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'ubicacion.label', default: 'Ubicacion'), params.id])
            redirect(action: "list")
            return
        }

        try {
            ubicacionInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'ubicacion.label', default: 'Ubicacion'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'ubicacion.label', default: 'Ubicacion'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
