package com.csc.vim.framework.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;

@Component
public class SapHelper {

	protected final Logger logger = LoggerFactory.getLogger(SapHelper.class);
//	@Autowired
//	private DCTMHelper dctmInstance;
//
//	@Autowired
//	private DateUtils dateInstance;
//	@Autowired
//	protected HelperConstant helperConstant;
//
//	@Autowired
//	protected HelperProperties helperProperties;
	
	@Value("${sap.invoice.mm.DOC_TYPE")
	public String MM_DOC_TYPE;
	
	@Value("${sap.invoice.ind.Invoice_IND")
	public String Invoice_IND;
	
	@Value("${val.sap.JCO_ASHOST}")
	public String val_sap_jco_ahost;

	@Value("${val.sap.JCO_SYSNR}")
	public String val_sap_jco_sysnr;

	@Value("${val.sap.JCO_CLIENT}")
	public String val_sap_jco_client;

	@Value("${val.sap.JCO_USER}")
	public String val_sap_jco_user;

	@Value("${val.sap.JCO_PASSWD}")
	public String val_sap_jco_passwd;

	@Value("${val.sap.JCO_LANG}")
	public String val_sap_jco_lang;

	@Value("${val.sap.invoice.AR.ARCHIV_ID}")
	public String ARCHIV_ID;

	@Value("${sap.invoice.AR.AR_OBJECT}")
	public String AR_OBJECT;

	@Value("${sap.invoice.AR.SAP_OBJECT}")
	public String SAP_OBJECT;
	// Variables used for the SAPJCO 3.0
    static String DESTINATION_NAME1 = "ABAP_AS_WITHOUT_POOL";
    static String DESTINATION_NAME2 = "ABAP_AS_WITH_POOL";
    static
    {
        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "20.33.93.136");
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  "00");
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "800");
        connectProperties.setProperty(DestinationDataProvider.JCO_USER,   "PORFILA");
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "katana");
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   "FR");
        createDestinationDataFile(DESTINATION_NAME1, connectProperties);
//        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "3");
//        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,    "10");
        createDestinationDataFile(DESTINATION_NAME2, connectProperties);
        
    }
	
    static void createDestinationDataFile(String destinationName, Properties connectProperties)
    {
        File destCfg = new File(destinationName+".jcoDestination");
        try
        {
            FileOutputStream fos = new FileOutputStream(destCfg, false);
            connectProperties.store(fos, "");
            fos.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to create the destination files", e);
        }
    }
	
    public static JCoDestination connect() throws JCoException
    {
        JCoDestination destination = JCoDestinationManager.getDestination(DESTINATION_NAME1);
        return destination;
    }

    public static void callFunction(String functionName,Map<String,String> functionParam) throws JCoException
    {
//        JCoDestination destination = JCoDestinationManager.getDestination(DESTINATION_NAME2);
//        JCoFunction function = destination.getRepository().getFunction(functionName);
//        if (function == null)
//            throw new RuntimeException(functionName+" not found in SAP.");
//        for(Entry<String, String> entry : functionParam.entrySet()) {
//            function.getImportParameterList().setValue(entry.getKey(), entry.getValue());
//            function.getImportParameterList().ge
//        }     
//        try
//        {
//            function.execute(destination);
//        }
//        catch (AbapException e)
//        {
//            System.out.println(e.toString());
//        }
//        //Reponse renvoyée par SAP
//        //function.getExportParameterList().getString("RESPTEXT")
//    }
    }
}
