/*
 * $Id: $
 *
 * Copyright (c) 2009 Fujitsu Denmark
 * All rights reserved.
 */
package dk.fujitsu.issuecheck.ims;

import dk.fujitsu.issuecheck.Config;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
public class RallyService implements ImsService {
    private static final Logger LOGGER = Logger.getLogger(RallyService.class);
    private Map<String, Document> cache;

    public RallyService() {
        cache = new HashMap<String, Document>();
    }
    
    @Override
    public boolean isIssue(String issueId) {
        return getIssue(issueId) != null;
    }

    @Override
    public boolean isIssueOpen(String issueId) {
        return true;
    }

    private Document getIssue(String name) {
        String[] issueTypes;
        Document document;

        document = cache.get(name);

        if(document != null) {
            return document;
        }

        if (Config.get("ims.rally.issueTypes") != null) {
            issueTypes = Config.get("ims.rally.issueTypes").split(",");

            for (int i = 0; i < issueTypes.length; i++) {
                issueTypes[i] = issueTypes[i].trim();

                document = fetchIssue(issueTypes[i].trim(), name);
                if (document != null) {
                    cache.put(name, document);
                    return document;
                }
            }
        }

        return document;
    }

    private Document fetchIssue(String type, String name) {
        URL url;
        String address;
        URLConnection connection;
        SAXReader reader;
        Document document;
        String login;

        LOGGER.debug("fetching issue " + type + " " + name);

        address = Config.get("ims.rally.service") + "/" + type + "?query=(FormattedID%20=%20" + name + ")&fetch=true";
        login = "Basic " + new BASE64Encoder().encode((Config.get("ims.rally.user").trim() + ":" + Config.get("ims.rally.password")).trim().getBytes());

        LOGGER.debug("fetching issue by " + address);

        try {
            url = new URL(address);
            connection = url.openConnection();
        } catch (MalformedURLException x) {
            throw new RuntimeException(address + " is malformed parsed from ims.rally.service", x);
        } catch (IOException x) {
            throw new RuntimeException("unable to connect to " + Config.get("ims.rally.service"), x);
        }

        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Authorization", login);

        reader = new SAXReader();

        try {
            document = reader.read(connection.getInputStream());
            LOGGER.debug("rally replied " + document.asXML());
        } catch (IOException x) {
            throw new RuntimeException("error reading reply from " + Config.get("ims.rally.service"), x);
        } catch (DocumentException x) {
            throw new RuntimeException("error parsing reply from " + Config.get("ims.rally.service"), x);
        }

        if (Integer.parseInt(document.selectSingleNode("/QueryResult/TotalResultCount").getText()) == 0) {
            LOGGER.debug("rally had no matching issues for " + type + " " + name);
            document = null;
        }

        // Rally is acting a little funny, searching for DE999 will eventually also return TA999
        if (document != null && !name.equals(document.selectSingleNode("/QueryResult/Results/Object/FormattedID").getText())) {
            LOGGER.debug("rally returned incorrect issue for " + type + " " + name + " doesn't matter we just ignore it");
            document = null;
        }

        return document;
    }
}
