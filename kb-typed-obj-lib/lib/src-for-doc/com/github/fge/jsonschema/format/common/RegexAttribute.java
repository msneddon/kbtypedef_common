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

package com.github.fge.jsonschema.format.common;

import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.format.AbstractFormatAttribute;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.github.fge.jsonschema.util.RhinoHelper;
import com.github.fge.msgsimple.bundle.MessageBundle;

/**
 * Validator for the {@code regex} format attribute.
 *
 * <p>Again, here, we do <b>not</b> use {@link java.util.regex} because it does
 * not fit the bill.</p>
 *
 * @see RhinoHelper
 */
public final class RegexAttribute
    extends AbstractFormatAttribute
{
    private static final FormatAttribute INSTANCE = new RegexAttribute();

    public static FormatAttribute getInstance()
    {
        return INSTANCE;
    }

    private RegexAttribute()
    {
        super("regex", NodeType.STRING);
    }

    @Override
    public void validate(final ProcessingReport report,
        final MessageBundle bundle, final FullData data)
        throws ProcessingException
    {
        final String value = data.getInstance().getNode().textValue();

        if (!RhinoHelper.regexIsValid(value))
            report.error(newMsg(data, bundle, "err.format.invalidRegex")
                .putArgument("value", value));
    }
}
