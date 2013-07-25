/*
 * Copyright (c) 2013, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.fge.jsonschema.syntax.checkers.draftv3;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JacksonUtils;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.github.fge.jsonschema.syntax.checkers.SyntaxChecker;
import com.github.fge.jsonschema.syntax.checkers.helpers.SchemaMapSyntaxChecker;
import com.github.fge.jsonschema.tree.SchemaTree;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.google.common.collect.Maps;

import java.util.Map;
import java.util.SortedMap;

/**
 * Syntax checker for draft v3's {@code properties} keyword
 */
public final class DraftV3PropertiesSyntaxChecker
    extends SchemaMapSyntaxChecker
{
    private static final SyntaxChecker INSTANCE
        = new DraftV3PropertiesSyntaxChecker();

    public static SyntaxChecker getInstance()
    {
        return INSTANCE;
    }

    private DraftV3PropertiesSyntaxChecker()
    {
        super("properties");
    }

    @Override
    protected void extraChecks(final ProcessingReport report,
        final MessageBundle bundle, final SchemaTree tree)
        throws ProcessingException
    {
        final SortedMap<String, JsonNode> map = Maps.newTreeMap();
        map.putAll(JacksonUtils.asMap(tree.getNode().get(keyword)));

        String member;
        JsonNode required;
        NodeType type;

        for (final Map.Entry<String, JsonNode> entry: map.entrySet()) {
            member = entry.getKey();
            required = entry.getValue().get("required");
            if (required == null)
                continue;
            type = NodeType.getNodeType(required);
            if (type != NodeType.BOOLEAN) {
                report.error(newMsg(tree, bundle,
                    "draftv3.properties.required.incorrectType")
                    .putArgument("property", member).putArgument("found", type)
                    .put("expected", NodeType.BOOLEAN));
            }
        }
    }
}
