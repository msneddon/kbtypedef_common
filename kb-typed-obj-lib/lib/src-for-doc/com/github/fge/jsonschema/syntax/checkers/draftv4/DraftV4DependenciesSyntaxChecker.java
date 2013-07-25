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

package com.github.fge.jsonschema.syntax.checkers.draftv4;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.github.fge.jsonschema.syntax.checkers.SyntaxChecker;
import com.github.fge.jsonschema.syntax.checkers.helpers.DependenciesSyntaxChecker;
import com.github.fge.jsonschema.tree.SchemaTree;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.google.common.base.Equivalence;
import com.google.common.collect.Sets;

import java.util.EnumSet;
import java.util.Set;

/**
 * Syntax checker for draft v4's {@code dependencies} keyword
 */
public final class DraftV4DependenciesSyntaxChecker
    extends DependenciesSyntaxChecker
{
    private static final SyntaxChecker INSTANCE
        = new DraftV4DependenciesSyntaxChecker();

    public static SyntaxChecker getInstance()
    {
        return INSTANCE;
    }

    private DraftV4DependenciesSyntaxChecker()
    {
        super(NodeType.ARRAY);
    }

    @Override
    protected void checkDependency(final ProcessingReport report,
        final MessageBundle bundle, final String name, final SchemaTree tree)
        throws ProcessingException
    {
        final JsonNode node = getNode(tree).get(name);
        NodeType type;

        type = NodeType.getNodeType(node);

        if (type != NodeType.ARRAY) {
            report.error(newMsg(tree, bundle,
                "common.dependencies.value.incorrectType")
                .putArgument("property", name)
                .putArgument("expected", dependencyTypes)
                .putArgument("found", type));
            return;
        }

        final int size = node.size();

        if (size == 0) {
            report.error(newMsg(tree, bundle, "common.array.empty")
                .put("property", name));
            return;
        }

        final Set<Equivalence.Wrapper<JsonNode>> set = Sets.newHashSet();

        JsonNode element;
        boolean uniqueElements = true;

        for (int index = 0; index < size; index++) {
            element = node.get(index);
            type = NodeType.getNodeType(element);
            uniqueElements = set.add(EQUIVALENCE.wrap(element));
            if (type == NodeType.STRING)
                continue;
            report.error(newMsg(tree, bundle,
                "common.array.element.incorrectType")
                .put("property", name).putArgument("index", index)
                .putArgument("expected", EnumSet.of(NodeType.STRING))
                .putArgument("found", type));
        }

        if (!uniqueElements)
            report.error(newMsg(tree, bundle, "common.array.duplicateElements")
                .put("property", name));
    }
}
