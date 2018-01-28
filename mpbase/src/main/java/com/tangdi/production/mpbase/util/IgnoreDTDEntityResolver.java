package com.tangdi.production.mpbase.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class IgnoreDTDEntityResolver implements EntityResolver {

	public InputSource resolveEntity(String publicId, String systemId)
			throws SAXException, IOException {
		// TODO Auto-generated method stub
		System.out.println("systemId:"+systemId);
		System.out.println("publicId:"+publicId);
		if (systemId.contains("configure.dtd"))
            return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes()));
        else
		return null;
	}

}
