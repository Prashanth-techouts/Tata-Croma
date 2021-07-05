/*
 * [y] hybris Platform
 *
 * Copyright (c) 2018 SAP SE or an SAP affiliate company.  All rights reserved.
 *
 * This software is the confidential and proprietary information of SAP
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SAP.
 */
package de.techouts.pocchroma.storefront.security.impl;

import de.hybris.platform.acceleratorfacades.flow.CheckoutFlowFacade;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.web.DefaultRedirectStrategy;


/**
 * A redirect strategy used in
 * {@link de.hybris.platform.acceleratorstorefrontcommons.security.StorefrontAuthenticationSuccessHandler} to handle
 * express checkout case
 */
public class DefaultCommerceRedirectStrategy extends DefaultRedirectStrategy
{

	private CheckoutFlowFacade checkoutFlowFacade;
	private String expressTargetUrl;

	@Override
	public void sendRedirect(final HttpServletRequest request, final HttpServletResponse response, final String url)
			throws IOException
	{
		String redirectUrl = url;

		if (checkoutFlowFacade.isExpressCheckoutEnabledForStore()
				&& StringUtils.isNotEmpty(request.getParameter("expressCheckoutEnabled")))
		{
			redirectUrl = getExpressTargetUrl();
		}
		super.sendRedirect(request, response, redirectUrl);
	}


	protected String getExpressTargetUrl()
	{
		return expressTargetUrl;
	}

	@Required
	public void setExpressTargetUrl(final String expressTargetUrl)
	{
		this.expressTargetUrl = expressTargetUrl;
	}

	protected CheckoutFlowFacade getCheckoutFlowFacade()
	{
		return checkoutFlowFacade;
	}

	@Required
	public void setCheckoutFlowFacade(final CheckoutFlowFacade checkoutFlowFacade)
	{
		this.checkoutFlowFacade = checkoutFlowFacade;
	}
}
