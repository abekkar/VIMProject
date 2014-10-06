package com.csc.vim.framework.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;

import com.csc.vim.framework.model.Invoice;
import com.csc.vim.framework.util.HelperConstant;
import com.csc.vim.framework.util.HelperProperties;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;

public abstract class AbstractSapDao extends GenericDao {
	
	protected static String DESTINATION_NAME1 = "ABAP_AS_WITHOUT_POOL";
	
	@Autowired
	protected HelperConstant helperConstant;

	@Autowired
	protected HelperProperties helperProperties;

//	@Autowired
//	protected HelperDfcProperties helperDfcProperties;
//
//	@Autowired
//	protected CscSapHelper cscSapDatasource;

	protected JCoDestination destination ;

	public abstract Invoice createWithPurchaseOrder(Invoice pInvoice) ;
	
	public abstract Invoice createWithoutPurchaseOrder(Invoice pInvoice) ;

	public abstract String linkDocument(Invoice pInvoiceContent);

	public abstract Invoice retrieveInvoice(Invoice pInvoice);
	
	/**
	 * Initiate a connection with SAP. The connection object is stored into the class variable: destination
	 * 
	 * @author kaoutar
	 * @throws IOException
	 * @since 1.0
	 * @throws Exception
	 */
	protected abstract JCoDestination connectToSap() throws JCoException, IOException;

	/**
	 * 
	 * @author kaoutar
	 * @since 1.0
	 * @param destinationName
	 * @param connectProperties
	 * @throws JCoException
	 * @throws IOException
	 */
	protected void createDestinationDataFile( String destinationName, Properties connectProperties) throws JCoException, IOException
	{
		File destCfg = new File(destinationName+".jcoDestination");

		FileOutputStream fos = new FileOutputStream(destCfg, true);
		connectProperties.store(fos, "Sap Param: ");
		fos.close();

		destination = JCoDestinationManager.getDestination(helperProperties.sap_destination_datafile);
	}




	
	/**
	 * Extract message from the Return table of a BAPI
	 * @author syong
	 * @since 1.0
	 * @param pResultable Return table of a BAPI
	 * @return List<String> Message Content of the pResultTable
	 */
	protected List<String> extractSapMessage(com.sap.conn.jco.JCoTable pResultable) {
		ArrayList<String> msgSap = new ArrayList<String>();

		// Get the list of message
		for (int i=0; i< pResultable.getNumRows(); i++) {
			pResultable.setRow(i);
			String msg = pResultable.getString("MESSAGE");
			msgSap.add(msg);
		}

		return msgSap;
	}
}
