package com.csc.vim.framework.controller;



/***
 * The purpose of this class if to handle cross-site problems
 * 
 * @author syongwaiman2
 *
 */
public abstract class  CorsCommon{

//	protected final Logger logger = LoggerFactory.getLogger(CorsCommon.class);
//
//	@RequestMapping(method = RequestMethod.OPTIONS)
//	public void commonOptions(HttpServletResponse theHttpServletResponse) throws IOException {
//		theHttpServletResponse.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with, my-cool-header");
//		theHttpServletResponse.addHeader("Access-Control-Max-Age", "60"); // seconds to cache preflight request --> less OPTIONS traffic
//		theHttpServletResponse.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
//		theHttpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
//	}
//
//	/**
//	 * Format the HTTP REquest to indicate that it contains XML String
//	 * @param pResponse HttpServletResponse The HTTP response to modify
//	 */
//	protected void formatReturnHttpRequestForXml(HttpServletResponse pResponse) {
//		pResponse.setContentType("application/xml");	
//		returnHttpResponse(pResponse);
//	}
//	
//	protected void returnHttpResponse(HttpServletResponse pResponse)  {
//
//		try {
//			commonOptions(pResponse);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

}
