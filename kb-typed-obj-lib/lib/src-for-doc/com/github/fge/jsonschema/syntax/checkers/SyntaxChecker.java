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

package com.github.fge.jsonschema.syntax.checkers;

import com.github.fge.jackson.NodeType;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.github.fge.jsonschema.syntax.SyntaxProcessor;
import com.github.fge.jsonschema.tree.SchemaTree;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.google.common.annotations.VisibleForTesting;

import java.util.Collection;
import java.util.EnumSet;

/**
 * Interface for a syntax checker
 *
 * <p>This is a syntax checker for one keyword. As some keywords contain
 * subschemas, a list of {@link JsonPointer} is also passed as an argument so
 * that the checker can instruct the syntax processor to also check these paths
 * after it is done.</p>
 *
 * <p>Note that when a syntax checker is called on a schema, it is guaranteed
 * that the keyword exists in the schema.</p>
 *
 * @see SyntaxProcessor
 */
public interface SyntaxChecker
{
    // FIXME: I should get rid of that -- it is used in only one place.
    @VisibleForTesting
    EnumSet<NodeType> getValidTypes();

    /**
     * Check the syntax for this keyword
     *
     * @param pointers the list of JSON Pointers to fill (see description)
     * @param  bundle the message bundle to use
     * @param report the processing report to use
     * @param tree the schema
     * @throws ProcessingException an error is detected, and the report is
     * configured to throw an exception on error.
     */
    void checkSyntax(final Collection<JsonPointer> pointers,
        final MessageBundle bundle, final ProcessingReport report,
        final SchemaTree tree)
        throws ProcessingException;
}
