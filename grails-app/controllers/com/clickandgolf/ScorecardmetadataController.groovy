package com.clickandgolf


import grails.plugins.springsecurity.Secured;

import org.springframework.dao.DataIntegrityViolationException

@Secured(['ROLE_ADMIN'])
class ScorecardmetadataController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [scorecardmetadataInstanceList: Scorecardmetadata.list(params), scorecardmetadataInstanceTotal: Scorecardmetadata.count()]
    }

    def create() {
        [scorecardmetadataInstance: new Scorecardmetadata(params)]
    }

    def save() {
        def scorecardmetadataInstance = new Scorecardmetadata(params)
        if (!scorecardmetadataInstance.save(flush: true)) {
            render(view: "create", model: [scorecardmetadataInstance: scorecardmetadataInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'scorecardmetadata.label', default: 'Scorecardmetadata'), scorecardmetadataInstance.id])
        redirect(action: "show", id: scorecardmetadataInstance.id)
    }

    def show() {
        def scorecardmetadataInstance = Scorecardmetadata.get(params.id)
        if (!scorecardmetadataInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'scorecardmetadata.label', default: 'Scorecardmetadata'), params.id])
            redirect(action: "list")
            return
        }

        [scorecardmetadataInstance: scorecardmetadataInstance]
    }

    def edit() {
        def scorecardmetadataInstance = Scorecardmetadata.get(params.id)
        if (!scorecardmetadataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'scorecardmetadata.label', default: 'Scorecardmetadata'), params.id])
            redirect(action: "list")
            return
        }

        [scorecardmetadataInstance: scorecardmetadataInstance]
    }

    def update() {
        def scorecardmetadataInstance = Scorecardmetadata.get(params.id)
        if (!scorecardmetadataInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'scorecardmetadata.label', default: 'Scorecardmetadata'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (scorecardmetadataInstance.version > version) {
                scorecardmetadataInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'scorecardmetadata.label', default: 'Scorecardmetadata')] as Object[],
                          "Another user has updated this Scorecardmetadata while you were editing")
                render(view: "edit", model: [scorecardmetadataInstance: scorecardmetadataInstance])
                return
            }
        }

        scorecardmetadataInstance.properties = params

        if (!scorecardmetadataInstance.save(flush: true)) {
            render(view: "edit", model: [scorecardmetadataInstance: scorecardmetadataInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'scorecardmetadata.label', default: 'Scorecardmetadata'), scorecardmetadataInstance.id])
        redirect(action: "show", id: scorecardmetadataInstance.id)
    }

    def delete() {
        def scorecardmetadataInstance = Scorecardmetadata.get(params.id)
        if (!scorecardmetadataInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'scorecardmetadata.label', default: 'Scorecardmetadata'), params.id])
            redirect(action: "list")
            return
        }

        try {
            scorecardmetadataInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'scorecardmetadata.label', default: 'Scorecardmetadata'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'scorecardmetadata.label', default: 'Scorecardmetadata'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
