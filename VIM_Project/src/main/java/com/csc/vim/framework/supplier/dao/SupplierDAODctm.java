package com.csc.vim.framework.supplier.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.stereotype.Repository;

import com.csc.vim.framework.common.dao.DctmDao;
import com.csc.vim.framework.properties.DctmModelType;
import com.csc.vim.framework.supplier.model.Supplier;
import com.documentum.fc.client.IDfPersistentObject;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;

@Repository
public class SupplierDAODctm extends DctmDao<Supplier> implements ISupplierDAO {

	private static final String SUPPLIER		= "supplier";
	private static final String NAMESPACE 		= "demat";
	public static final String SUPPLIER_TYPE 	=  NAMESPACE + "_" + SUPPLIER;
	
	private static final String CPD            = "cpd";
	private static final String INDUSTRY       = "industry";
	private static final String ADDRESS         = "company_address";
	private static final String CITY           = "city";
	private static final String COUNTRY        = "country";
	private static final String EMAIL          = "email";
	private static final String POST_CODE      = "post_code";
	private static final String NAME           = "object_name";
	private static final String NUMBER         = "number";
	private static final String TAX_NUMBER     = "tax_number";
	private static final String VAT_NUMBER     = "vat_number";
	private static final String DEFAULT_IBAN   = "default_iban";
	
	/**
	 * Get all the Supplier objects link to an object provided in input
	 * 
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pParentId			r_object_id of the parent (for instance the r_object_id of an invoice if we want the its lines)
	 * @param ptransactionnal	If equals to false, the service will open/close a session
	 * @return List of supplier child of the parent in input (the parent is most likely the invoice)
	 */
	public Collection<Supplier> getChildrenOfInvoice(String pParentId) throws DfException {
		Collection<Supplier> result = new ArrayList<Supplier>();
		
		// Get the list of supplier children of this invoice (Actualla a lsit of r_object_id)
		Collection<String> listRObjectId = dctmHelper.getChild(mySession, pParentId, DctmModelType.RL_INVOICE_SUPPLIER);
		
		// build the supplier object from the r_object_id
		Iterator<String> it = listRObjectId.iterator();
		while(it.hasNext()) {
			String id = it.next();
			Supplier supplier = getById(id);
			result.add(supplier);
		}
		
		return result;
	}
	
	/**
	 * Get in Dctm the Supplier Obbject based on its r_object_id
	 * 
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pId	r_object_id  of the Supplier object
	 * @return The Supplier object in Dctm 
	 */
	public Supplier getById(String pId) throws DfException {
		
		// retrieved the supplier
		IDfPersistentObject sysObject = mySession.getObject(new DfId(pId));
		
		// Build the Java Supplier
		Supplier supplier = new Supplier();
		updateJavaBeanWithDctmBean(supplier, sysObject);
		
		return supplier;
	}
	
	@Override
	public Supplier create(Supplier pSupplier) throws DfException{
		
			IDfSysObject supplier = (IDfSysObject) mySession.newObject(SUPPLIER_TYPE);
		
			// Set the properties of the dctm object to be created
			updateDctmBeanWithJavaBean(supplier, pSupplier);
			supplier.link(pSupplier.getDctmFolder());
			
			// Create in Dctm the supplier
			supplier.save();
			
			// Update the Java supplier bean with its new r_object_id
			pSupplier.setrObjectId(supplier.getObjectId().toString());
		
		
		return pSupplier;
	}

	/**
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pJavaBean The Java Business Object to be updated with data retrieved from the repository
	 * @param pDctmBean The Dctm Object retrieved from repository
	 */
	@Override
	protected void updateJavaBeanWithDctmBean(Supplier pJavaBean,
			IDfPersistentObject pDctmBean) throws DfException {
		
		pJavaBean.setAddress(pDctmBean.getString(ADDRESS));
		pJavaBean.setCity(pDctmBean.getString(CITY));
		pJavaBean.setCountry(pDctmBean.getString(COUNTRY));
		pJavaBean.setCpd(pDctmBean.getBoolean(CPD));
		pJavaBean.setDefaultIban(pDctmBean.getString(DEFAULT_IBAN));
		pJavaBean.setEmail(pDctmBean.getString(EMAIL));
		pJavaBean.setIndustry(pDctmBean.getString(INDUSTRY));
		pJavaBean.setNumber(pDctmBean.getString(NUMBER));
		pJavaBean.setPostCode(pDctmBean.getString(POST_CODE));
		pJavaBean.setTaxNumber(pDctmBean.getString(TAX_NUMBER));
		pJavaBean.setVatNumber(pDctmBean.getString(VAT_NUMBER));
		
	}

	/**
	 * @author syongwaiman2
	 * @since 1.0
	 * @param pDctmBean The DCTM to be updated
	 * @param pJavaBean The Java Business Object that contains the value to be transfered to the dctm object
	 */
	@Override
	protected void updateDctmBeanWithJavaBean(IDfPersistentObject pDctmBean,
			Supplier pJavaBean) throws DfException {
		
		pDctmBean.setString(ADDRESS, pJavaBean.getAddress());
		pDctmBean.setString(CITY, pJavaBean.getCity());
		pDctmBean.setString(COUNTRY, pJavaBean.getCountry());
		if (pJavaBean.getCpd() != null)
			pDctmBean.setBoolean(CPD, pJavaBean.getCpd());
		pDctmBean.setString(DEFAULT_IBAN, pJavaBean.getDefaultIban());
		pDctmBean.setString(EMAIL, pJavaBean.getEmail());
		pDctmBean.setString(NAME, pJavaBean.getName());
		pDctmBean.setString(INDUSTRY, pJavaBean.getIndustry());
		pDctmBean.setString(NUMBER, pJavaBean.getNumber());
		pDctmBean.setString(POST_CODE, pJavaBean.getPostCode());
		pDctmBean.setString(TAX_NUMBER, pJavaBean.getTaxNumber());
		pDctmBean.setString(VAT_NUMBER, pJavaBean.getVatNumber());
		
	}


	


	

}
