package com.csc.vim.framework.message.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public class MessageController {

	/**
	 * Identifiant Service: DEC23
	 * Ce service prend différents éléments de la décision et les concatènes en une seule ligne
	 * Service permettant de construire une décision faite par un Approver ou Performer
	 
	 * @param refusal_delivery
	 * @param refusal_price
	 * @param refusal_amount
	 * @param refusal_reason
	 * @param refusal_price_initial
	 * @param refusal_price_current
	 * @param refusal_amount_initial
	 * @param refusal_amount_current
	 * @return Decision : XML contenant la décision du "Performer" en une ligne
	 */
	@RequestMapping(value = "/decision", method = RequestMethod.POST)
	public @ResponseBody String createDecisionMsg(@RequestBody String refusal_delivery,
			@RequestBody(required=false) String refusal_price,
			@RequestBody(required=false) String refusal_amount,
			@RequestBody(required=false) String refusal_reason,
			@RequestBody(required=false) String refusal_price_initial,
			@RequestBody(required=false) String refusal_price_current,
			@RequestBody(required=false) String refusal_amount_initial,
			@RequestBody(required=false) String refusal_amount_current
			) {
		
		StringBuilder str = new StringBuilder("Partially refused because: ");
		
		if (refusal_delivery == "1") {
			str.append("delivery, ");
		}
		if (refusal_price == "1") {
			str.append("price: " + refusal_price_current + " out of " + refusal_price_initial + ", ");
		}
		if (refusal_amount == "1") {
			str.append("amount: " + refusal_amount_current + " out of " + refusal_amount_initial + ", ");
		}
		str.append(refusal_reason);
		

        return refusal_delivery + refusal_price;
    }
}
