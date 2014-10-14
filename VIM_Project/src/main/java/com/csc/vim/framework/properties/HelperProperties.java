package com.csc.vim.framework.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HelperProperties {

	// ===========
	// === VMD ===
	// ===========
	@Value("${val.sap.vmd.JCO_ASHOST}")
	public String val_sap_vmd_jco_ahost;

	@Value("${val.sap.vmd.JCO_SYSNR}")
	public String val_sap_vmd_jco_sysnr;

	@Value("${val.sap.vmd.JCO_CLIENT}")
	public String val_sap_vmd_jco_client;

	@Value("${val.sap.vmd.JCO_USER}")
	public String val_sap_vmd_jco_user;

	@Value("${val.sap.vmd.JCO_PASSWD}")
	public String val_sap_vmd_jco_passwd;

	@Value("${val.sap.vmd.JCO_LANG}")
	public String val_sap_vmd_jco_lang;



	// ===========
	// === CSC ===
	// ===========
	@Value("${csc.sap.JCO_ASHOST}")
	public String csc_sap_jco_ahost;

	@Value("${csc.sap.JCO_SYSNR}")
	public String csc_sap_jco_sysnr;

	@Value("${csc.sap.JCO_CLIENT}")
	public String csc_sap_jco_client;

	@Value("${csc.sap.JCO_USER}")
	public String csc_sap_jco_user;

	@Value("${csc.sap.JCO_PASSWD}")
	public String csc_sap_jco_passwd;

	@Value("${csc.sap.JCO_LANG}")
	public String csc_sap_jco_lang;

	@Value("${sap.datasoure}")
	public String sap_datasource;

	@Value("${sap.destination.datafile}")
	public String sap_destination_datafile;

	@Value("${date.format}")
	public String dateFormat;

	@Value ("${dctm_repositoryName}")
	public String dctm_repositoryName;

	@Value ("${dctm_login}")
	public String dctm_login;

	@Value ("${dctm_password}")
	public String dctm_password;

}
