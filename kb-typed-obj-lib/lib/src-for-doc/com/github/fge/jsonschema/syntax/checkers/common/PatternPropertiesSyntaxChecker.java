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

package com.github.fge.jsonschema.syntax.checkers.common;

import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.github.fge.jsonschema.syntax.checkers.SyntaxChecker;
import com.github.fge.jsonschema.syntax.checkers.helpers.SchemaMapSyntaxChecker;
import com.github.fge.jsonschema.tree.SchemaTree;
import com.github.fge.jsonschema.util.RhinoHelper;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Syntax checker for the {@code patternProperties} keyword
 *
 * @see RhinoHelper
 */
public final class PatternPropertiesSyntaxChecker
    extends SchemaMapSyntaxChecker
{
    private static final SyntaxChecker INSTANCE
        = new PatternPropertiesSyntaxChecker();

    public static SyntaxChecker getInstance()
    {
        return INSTANCE;
    }

    private PatternPropertiesSyntaxChecker()
    {
        super("patternProperties");
    }

    @Override
    protected void extraChecks(final ProcessingReport report,
        final MessageBundle bundle, final SchemaTree tree)
        throws ProcessingException
    {
        /*
         * Check that the member names are regexes
         */
        final Set<String> set = Sets.newHashSet(getNode(tree).fieldNames());

        for (final String s: Ordering.natural().sortedCopy(set))
            if (!RhinoHelper.regexIsValid(s))
                report.error(newMsg(tree, bundle,
                    "common.patternProperties.member.notRegex")
                    .putArgument("propertyName", s));
    }
}
