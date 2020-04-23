package com.felipe.cursomc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.felipe.cursomc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagementoComBoleto(PagamentoComBoleto pgto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pgto.setDataVencimento(cal.getTime());
	}

}
