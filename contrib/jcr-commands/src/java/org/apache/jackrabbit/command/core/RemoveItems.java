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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jcr.Item;
import javax.jcr.Node;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jackrabbit.command.CommandHelper;
import org.apache.jackrabbit.util.ChildrenCollectorFilter;

/**
 * Removes any item under the given node that match the given name pattern.
 */
public class RemoveItems implements Command
{
	/** logger */
	private static Log log = LogFactory.getLog(RemoveItems.class);

	// ---------------------------- < keys >
	/** path key */
	private String pathKey = "path";

	/** item pattern key */
	private String patternKey = "pattern";

	/**
	 * @inheritDoc
	 */
	public boolean execute(Context ctx) throws Exception
	{
		String pattern = (String) ctx.get(this.patternKey);
		String path = (String) ctx.get(this.pathKey);

		Node n = CommandHelper.getNode(ctx, path);

		if (log.isDebugEnabled())
		{
			log.debug("removing nodes from " + n.getPath()
					+ " that match pattern " + pattern);
		}

		List children = new ArrayList();
		ChildrenCollectorFilter collector = new ChildrenCollectorFilter(
				pattern, children, true, true, 1);
		collector.visit(n);

		Iterator items = children.iterator();

		while (items.hasNext())
		{
			Item item = (Item) items.next();
			if (item.isSame(CommandHelper.getCurrentNode(ctx))
					&& item.getDepth() > 0)
			{
				CommandHelper.setCurrentNode(ctx, item.getParent());
			}
			item.remove();
		}

		return false;
	}

	/**
	 * @return Returns the patternKey.
	 */
	public String getPatternKey()
	{
		return patternKey;
	}

	/**
	 * @param patternKey
	 *            Set the context attribute key for the pattern attribute.
	 */
	public void setPatternKey(String patternKey)
	{
		this.patternKey = patternKey;
	}

	/**
	 * @return Returns the pathKey.
	 */
	public String getPathKey()
	{
		return pathKey;
	}

	/**
	 * @param pathKey
	 *            The pathKey to set.
	 */
	public void setPathKey(String pathKey)
	{
		this.pathKey = pathKey;
	}
}
