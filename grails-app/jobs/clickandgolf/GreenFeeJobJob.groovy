package clickandgolf

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.transaction.annotation.Transactional;


class GreenFeeJobJob implements Job {
	
	def grailsApplication
	
	public GreenFeeJobJob() { }
	
	def getTriggers() {
		return config.grails.plugin.xyz.greenFeeTriggerConfig
	}
	
    void execute(JobExecutionContext jobCtx) {
		log.info("Se esta ejecutando el job de Green Fees ...")
		
		def campoService = grailsApplication.mainContext.campoService 
		def greenFeeService = grailsApplication.mainContext.greenFeeService
		
		def campos = campoService.getCamposActivos()
		log.info("Por intentar generar los green fees de " + campos?.size + " campos ...")
		campos.each { campo ->
			log.info("Procesando el campo '" + campo?.nombre + "' ...")
			greenFeeService.createGreenFees(campo)
			log.info("Campo '" + campo?.nombre + "' procesado ...")
		}
		
		log.info("Se ejecuto el job de Green Fees ...")
    }
	
}
