package com.csc.vim.framework.properties;

public class RestURIConstants {

	/*
	 * URL TO process invoice get info from SAP, book the invoice and update DCTM invoice
	 */
    public static final String PROCESS_AV2              = "/V1/SAV2";
    /*
	 * URL TO  get invoice informations from SAP
	 */
    public static final String RETRIEVE_SAP             = "/V1/SINV21";
    /*
	 * URL TO  create invoice SAP
	 */
    public static final String CREATE_INVOICE           = "/V1/SINV33";
    /*
	 * URL TO  link invoice between DCTM and SAP
	 */
    public static final String LINK_INVOICE             = "V1/SINVLK";
    /*
	 * URL TO  synchronise data invoice which status is 6 between SAP and DCTM 
	 */
    public static final String SYNCHRONISE_STATUS_SIX   = "/V1/SYNC6";
    /*
	 * URL TO  synchronise data invoice which status is 7 between SAP and DCTM 
	 */
    public static final String SYNCHRONISE_STATUS_SEVEN = "/V1/SYNC7";
    /*
	 * URL TO  synchronise data invoice which status is 8 between SAP and DCTM 
	 */
    public static final String SYNCHRONISE_STATUS_EIGHT = "/V1/SYNC8";
}
