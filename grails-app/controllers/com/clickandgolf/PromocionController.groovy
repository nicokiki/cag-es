package com.clickandgolf

import grails.plugins.springsecurity.Secured

/**
 * Generado automaticamente por Grails. Es una pagina interna. No importa.<br>
 * Solo los ADMIN pueden acceder aqui.
 * Es responsabilidad del ADMIN procurar q la cantidad de promociones sean logicas! y de dar de baja las caducadas!
 * 
 * @author gonznic
 */
@Secured(['ROLE_ADMIN'])
class PromocionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = params.max ? params.int('max') : 10
		
		def crit = Promocion.createCriteria()
		def resultado = crit.list {
			fetchMode("campo", org.hibernate.FetchMode.JOIN)
		}
		
        [promocionInstanceList: resultado, promocionInstanceTotal: Promocion.list().count ]
    }

    def create() {
        [promocionInstance: new Promocion(params)]
    }

    def save() {
        def promocionInstance = new Promocion(params)
        if (!promocionInstance.save(flush: true)) {
            render(view: "create", model: [promocionInstance: promocionInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'promocion.label', default: 'Promocion'), promocionInstance.id])
        redirect(action: "show", id: promocionInstance.id)
    }

    def show() {
        def promocionInstance = Promocion.get(params.id)
        if (!promocionInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'promocion.label', default: 'Promocion'), params.id])
            redirect(action: "list")
            return
        }

        [promocionInstance: promocionInstance]
    }

    def edit() {
        def promocionInstance = Promocion.get(params.id)
        if (!promocionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'promocion.label', default: 'Promocion'), params.id])
            redirect(action: "list")
            return
        }

        [promocionInstance: promocionInstance]
    }

    def update() {
        def promocionInstance = Promocion.get(params.id)
        if (!promocionInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'promocion.label', default: 'Promocion'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (promocionInstance.version > version) {
                promocionInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'promocion.label', default: 'Promocion')] as Object[],
                          "Another user has updated this Promocion while you were editing")
                render(view: "edit", model: [promocionInstance: promocionInstance])
                return
            }
        }

        promocionInstance.properties = params

        if (!promocionInstance.save(flush: true)) {
            render(view: "edit", model: [promocionInstance: promocionInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'promocion.label', default: 'Promocion'), promocionInstance.id])
        redirect(action: "show", id: promocionInstance.id)
    }

}
