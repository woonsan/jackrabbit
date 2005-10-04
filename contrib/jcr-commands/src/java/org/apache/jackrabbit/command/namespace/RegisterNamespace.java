/*
 * Copyright 2004-2005 The Apache Software Foundation or its licensors,
 *                     as applicable.
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
package org.apache.jackrabbit.command.namespace;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.command.CommandHelper;

/**
 * Registers a namespace
 */
public class RegisterNamespace implements Command
{
    /** logger */
    private static Log log = LogFactory.getLog(RegisterNamespace.class);

    // ---------------------------- < keys >
    /** prefix key */
    private String prefixKey = "prefix";

    /** uri key */
    private String uriKey = "uri";

    /**
     * @inheritDoc
     */
    public boolean execute(Context ctx) throws Exception
    {
        String prefix = (String) ctx.get(this.prefixKey);
        String uri = (String) ctx.get(this.uriKey);
        if (log.isDebugEnabled())
        {
            log.debug("registering namespace uri=" + uri + " prefix=" + prefix);
        }
        CommandHelper.getSession(ctx).getWorkspace().getNamespaceRegistry()
            .registerNamespace(prefix, uri);
        return false;
    }

    /**
     * @return Returns the prefixKey.
     */
    public String getPrefixKey()
    {
        return prefixKey;
    }

    /**
     * @param prefixKey
     *            The prefixKey to set.
     */
    public void setPrefixKey(String prefixKey)
    {
        this.prefixKey = prefixKey;
    }

    /**
     * @return Returns the uriKey.
     */
    public String getUriKey()
    {
        return uriKey;
    }

    /**
     * @param uriKey
     *            The uriKey to set.
     */
    public void setUriKey(String uriKey)
    {
        this.uriKey = uriKey;
    }
}
