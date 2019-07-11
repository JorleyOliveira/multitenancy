/*
 * Copyright 2018 onwards - Sunit Katkar (sunitkatkar@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sunitkatkar.blogspot.tenant.config;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sunitkatkar.blogspot.master.model.MasterTenant;
import com.sunitkatkar.blogspot.master.repository.MasterTenantRepository;
import com.sunitkatkar.blogspot.tenant.model.CustomUserDetails;
import com.sunitkatkar.blogspot.util.DataSourceUtil;
import com.sunitkatkar.blogspot.util.TenantContextHolder;

/**
 * This class does the job of selecting the correct database based on the tenant
 * id found by the {@link CurrentTenantIdentifierResolverImpl}
 * 
 * @author Sunit Katkar, sunitkatkar@gmail.com
 *         (https://sunitkatkar.blogspot.com/)
 * @since ver 1.0 (May 2018)
 * @version 1.0
 *
 */
@Configuration
public class DataSourceBasedMultiTenantConnectionProviderImpl
		extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

	private static final Logger LOG = LoggerFactory.getLogger(DataSourceBasedMultiTenantConnectionProviderImpl.class);

	private static final long serialVersionUID = 1L;

//	private static final String SELECT_CONTEXT = "select set_config('context.tenant' ,? , false)";
	/*
	 * 1. configurar postgresql
	 * 2. alterar apartir url 
	 * 3. configurar usuario pecci 'pecci' 
	 * 4. 
	 */

	/**
	 * Injected MasterTenantRepository to access the tenant information from the
	 * master_tenant table
	 */
	@Autowired
	private MasterTenantRepository masterTenantRepo;
	
	@Autowired
	private CurrentTenantIdentifierResolver currentTenantIdentifierResolver;

	/**
	 * Map to store the tenant ids as key and the data source as the value
	 */
	private Map<String, DataSource> dataSourcesMtApp = new TreeMap<>();

	@Override
	protected DataSource selectAnyDataSource() {
		// This method is called more than once. So check if the data source map
		// is empty. If it is then rescan master_tenant table for all tenant
		// entries.
		if (dataSourcesMtApp.isEmpty()) {
			List<MasterTenant> masterTenants = masterTenantRepo.findAll();
			LOG.info(">>>> selectAnyDataSource() -- Total tenants:" + masterTenants.size());
			for (MasterTenant masterTenant : masterTenants) {
				dataSourcesMtApp.put(masterTenant.getTenantId(),
						DataSourceUtil.createAndConfigureDataSource(masterTenant));
			}
		}
		return this.dataSourcesMtApp.values().iterator().next();
	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		// If the requested tenant id is not present check for it in the master
		// database 'master_tenant' table

		tenantIdentifier = initializeTenantIfLost(tenantIdentifier);

		if (!this.dataSourcesMtApp.containsKey(tenantIdentifier)) {
			List<MasterTenant> masterTenants = masterTenantRepo.findAll();
			LOG.info(
					">>>> selectDataSource() -- tenant:" + tenantIdentifier + " Total tenants:" + masterTenants.size());
			for (MasterTenant masterTenant : masterTenants) {
				dataSourcesMtApp.put(masterTenant.getTenantId(),
						DataSourceUtil.createAndConfigureDataSource(masterTenant));
			}
		}
		return this.dataSourcesMtApp.get(tenantIdentifier);
	}

	/**
	 * Initialize tenantId based on the logged in user if the tenant Id got lost in
	 * after form submission in a user session.
	 * 
	 * @param tenantIdentifier
	 * @return tenantIdentifier
	 */
	private String initializeTenantIfLost(String tenantIdentifier) {		
//		String tenant = TenantContextHolder.getTenant();
//        if (StringUtils.isNotBlank(tenant)) {
//        	return tenant;
//        }
//        throw new Exception("Tenant Not Defined!");
        String tenantCurrent = currentTenantIdentifierResolver.resolveCurrentTenantIdentifier();
        if (tenantIdentifier != currentTenantIdentifierResolver.resolveCurrentTenantIdentifier()) {
        	tenantIdentifier = tenantCurrent;
        }
        return tenantIdentifier;
        
//		if (TenantContextHolder.getTenant() == null) {
//
//			SecurityContext securityContext = SecurityContextHolder.getContext();
//			Authentication authentication = securityContext.getAuthentication();
//			CustomUserDetails customUserDetails = null;
//			if (authentication != null) {
//				Object principal = authentication.getPrincipal();
//				customUserDetails = principal instanceof CustomUserDetails ? (CustomUserDetails) principal : null;
//			}
//			TenantContextHolder.setTenantId(customUserDetails.getTenant());
//		}
//
//		if (tenantIdentifier != TenantContextHolder.getTenant()) {
//			tenantIdentifier = TenantContextHolder.getTenant();
//		}
//		return tenantIdentifier;
	}
}