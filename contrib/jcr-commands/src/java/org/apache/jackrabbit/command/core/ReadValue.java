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
package org.apache.jackrabbit.command.core;

import javax.jcr.Property;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.command.CommandHelper;

/**
 * Read the Value of the given property and store it under the given context
 * attribute.
 */
public class ReadValue implements Command
{
	/** logger */
	private static Log log = LogFactory.getLog(ReadValue.class);

	// ---------------------------- < keys >

	/** property path key */
	private String srcPathKey = "srcPath";

	/** value index key */
	private String srcIndexKey = "srcIndex";

	/** destination context attribute */
	private String destKey = "dest";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.chain.Command#execute(org.apache.commons.chain.Context)
	 */
	public boolean execute(Context ctx) throws Exception
	{
		String path = (String) ctx.get(this.srcPathKey);
		int index = 1;
		if (ctx.get(this.srcIndexKey) != null)
		{
			index = Integer.valueOf(((String) ctx.get(this.srcIndexKey)))
					.intValue();
		}

		String dest = (String) ctx.get(this.destKey);

		if (log.isDebugEnabled())
		{
			log.debug("reading value from " + path + "[" + index + "] to "
					+ dest);
		}

		Property p = (Property) CommandHelper.getItem(ctx, path);

		if (p.getDefinition().isMultiple())
		{
			ctx.put(dest, p.getValues()[index].getString());
		} else
		{
			ctx.put(dest, p.getValue().getString());
		}
		return false;
	}

	/**
	 * @return Returns the destKey.
	 */
	public String getDestKey()
	{
		return destKey;
	}

	/**
	 * @param destKey
	 *            The destKey to set.
	 */
	public void setDestKey(String destKey)
	{
		this.destKey = destKey;
	}

	/**
	 * @return Returns the srcIndexKey.
	 */
	public String getSrcIndexKey()
	{
		return srcIndexKey;
	}

	/**
	 * @param srcIndexKey
	 *            The srcIndexKey to set.
	 */
	public void setSrcIndexKey(String srcIndexKey)
	{
		this.srcIndexKey = srcIndexKey;
	}

	/**
	 * @return Returns the srcPathKey.
	 */
	public String getSrcPathKey()
	{
		return srcPathKey;
	}

	/**
	 * @param srcPathKey
	 *            The srcPathKey to set.
	 */
	public void setSrcPathKey(String srcPathKey)
	{
		this.srcPathKey = srcPathKey;
	}
}
