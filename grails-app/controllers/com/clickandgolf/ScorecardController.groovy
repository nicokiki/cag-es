package com.clickandgolf

import grails.plugins.springsecurity.Secured;

import org.springframework.dao.DataIntegrityViolationException

@Secured(['ROLE_ADMIN'])
class ScorecardController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [scorecardInstanceList: Scorecard.list(params), scorecardInstanceTotal: Scorecard.count()]
    }

    def create() {
        [scorecardInstance: new Scorecard(params)]
    }

    def save() {
        def scorecardInstance = new Scorecard(params)
        if (!scorecardInstance.save(flush: true)) {
            render(view: "create", model: [scorecardInstance: scorecardInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'scorecard.label', default: 'Scorecard'), scorecardInstance.id])
        redirect(action: "show", id: scorecardInstance.id)
    }

    def show() {
        def scorecardInstance = Scorecard.get(params.id)
        if (!scorecardInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'scorecard.label', default: 'Scorecard'), params.id])
            redirect(action: "list")
            return
        }

        [scorecardInstance: scorecardInstance]
    }

    def edit() {
        def scorecardInstance = Scorecard.get(params.id)
        if (!scorecardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'scorecard.label', default: 'Scorecard'), params.id])
            redirect(action: "list")
            return
        }

        [scorecardInstance: scorecardInstance]
    }

    def update() {
        def scorecardInstance = Scorecard.get(params.id)
        if (!scorecardInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'scorecard.label', default: 'Scorecard'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (scorecardInstance.version > version) {
                scorecardInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'scorecard.label', default: 'Scorecard')] as Object[],
                          "Another user has updated this Scorecard while you were editing")
                render(view: "edit", model: [scorecardInstance: scorecardInstance])
                return
            }
        }

        scorecardInstance.properties = params

        if (!scorecardInstance.save(flush: true)) {
            render(view: "edit", model: [scorecardInstance: scorecardInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'scorecard.label', default: 'Scorecard'), scorecardInstance.id])
        redirect(action: "show", id: scorecardInstance.id)
    }

    def delete() {
        def scorecardInstance = Scorecard.get(params.id)
        if (!scorecardInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'scorecard.label', default: 'Scorecard'), params.id])
            redirect(action: "list")
            return
        }

        try {
            scorecardInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'scorecard.label', default: 'Scorecard'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'scorecard.label', default: 'Scorecard'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
