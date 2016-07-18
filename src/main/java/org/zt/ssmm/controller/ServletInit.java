package org.zt.ssmm.controller;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zt.ssmm.util.Common;


public class ServletInit extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ServletInit() {
		super();
	}

	public void init() throws ServletException {
		// init application parameters


		Common.mapStationsName = new HashMap<String, String>();
		Common.mapDevicesName = new HashMap<String, String>();


		Common.runClassPath = getServletContext().getRealPath("/");
        Common.mapDevicesName = new HashMap<String, String>();

	}

}