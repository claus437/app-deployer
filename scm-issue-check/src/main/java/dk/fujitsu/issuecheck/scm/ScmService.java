/*
 * $Id: $
 *
 * Copyright (c) 2009 Fujitsu Denmark
 * All rights reserved.
 */

package dk.fujitsu.issuecheck.scm;

import java.util.List;

/**
 * @author Claus Brøndby Reimer (dencbr) / Fujitsu Denmark a|s
 * @version $Revision: $ $Date: $
 */
public interface ScmService {

    List<String> getMessages(String... arguments);
}
