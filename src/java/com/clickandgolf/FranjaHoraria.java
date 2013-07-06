/**
 * 
 */
package com.clickandgolf;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

/**
 * @author manzano
 *
 */
public enum FranjaHoraria {
	
	FRANJA_HORARIO_CERO {
		@Override
		public void doProcessMinimum(GreenFeeCommand cmd, BigDecimal precioNuevo) {
			if (cmd.isMenorQueRangoCero(precioNuevo)) {
				cmd.precioRangoCero = precioNuevo;
			}
			
			new Comparator<String>() {

				@Override
				public int compare(String o1, String o2) {
					// TODO Auto-generated method stub
					return 0;
				}
				
				
			};
					
			
		}
		@Override
		public void setPrecio(GreenFeeCommand cmd, BigDecimal precioNuevo) {
			cmd.precioRangoCero = precioNuevo;
		}
	},
	FRANJA_HORARIO_UNO {
		@Override
		public void doProcessMinimum(GreenFeeCommand cmd, BigDecimal precioNuevo) {
			if (cmd.isMenorQueRangoUno(precioNuevo)) {
				cmd.precioRangoUno = precioNuevo;
			}
		}
		@Override
		public void setPrecio(GreenFeeCommand cmd, BigDecimal precioNuevo) {
			cmd.precioRangoUno = precioNuevo;
		}
	},
	FRANJA_HORARIO_DOS {
		@Override
		public void doProcessMinimum(GreenFeeCommand cmd, BigDecimal precioNuevo) {
			if (cmd.isMenorQueRangoDos(precioNuevo)) {
				cmd.precioRangoDos = precioNuevo;
			}
		}
		@Override
		public void setPrecio(GreenFeeCommand cmd, BigDecimal precioNuevo) {
			cmd.precioRangoDos = precioNuevo;
		}
	},
	FRANJA_HORARIO_TRES {
		@Override
		public void doProcessMinimum(GreenFeeCommand cmd, BigDecimal precioNuevo) {
			if (cmd.isMenorQueRangoTres(precioNuevo)) {
				cmd.precioRangoTres = precioNuevo;
			}
		}
		@Override
		public void setPrecio(GreenFeeCommand cmd, BigDecimal precioNuevo) {
			cmd.precioRangoTres = precioNuevo;
		}
	};


	public abstract void doProcessMinimum(GreenFeeCommand cmd, BigDecimal precioNuevo);
	public abstract void setPrecio(GreenFeeCommand cmd, BigDecimal precioNuevo);
		
	
	static FranjaHoraria fromDate(DateTime fecha) {
		int hour = fecha.getHourOfDay();
				
		if (hour < 10) {
			return FranjaHoraria.FRANJA_HORARIO_CERO;
		}
		else if (hour >= 10 && hour < 13) {
			return FranjaHoraria.FRANJA_HORARIO_UNO;
		}
		else if (hour >= 13 && hour < 16) {
			return FranjaHoraria.FRANJA_HORARIO_DOS;
		}
		else {
			return FranjaHoraria.FRANJA_HORARIO_TRES;
		}
	}
	
}
