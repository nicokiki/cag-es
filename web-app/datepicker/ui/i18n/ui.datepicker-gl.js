/* Inicializaci�n en espa�ol para la extensi�n 'UI date picker' para jQuery. */
/* Traducido por Vester (xvester@gmail.com). */
jQuery(function($){
	$.datepicker.regional['gl'] = {
		closeText: 'Pechar',
		prevText: '&#x3c;Ant',
		nextText: 'Seg&#x3e;',
		currentText: 'Hoxe',
		monthNames: ['Xaneiro','Febreiro','Marzo','Abril','Maio','Xu&ntilde;o',
		'Xullo','Agosto','Setembro','Outubro','Novembro','Decembro'],
		monthNamesShort: ['Xan','Feb','Mar','Abr','Mai','Xu&ntilde;',
		'Xull','Ago','Set','Out','Nov','Dec'],
		dayNames: ['Domingo','Luns','Martes','M&eacute;rcores','Xoves','Venres','S&aacute;bado'],
		dayNamesShort: ['Dom','Lun','Mar','M&eacute;r','Xov','Ven','S&aacute;b'],
		dayNamesMin: ['Do','Lu','Ma','M&eacute;','Xo','Ve','S&aacute;'],
		dateFormat: 'dd/mm/yy', firstDay: 0,
		isRTL: false};
	$.datepicker.setDefaults($.datepicker.regional['gl']);
});