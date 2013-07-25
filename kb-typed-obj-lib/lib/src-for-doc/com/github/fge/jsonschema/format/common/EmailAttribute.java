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
import com.github.fge.msgsimple.bundle.MessageBundle;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Validator for the {@code email} format attribute.
 *
 * <p><b>Important note</b>: the basis for email format validation is <a
 * href="http://tools.ietf.org/html/rfc5322">RFC 5322</a>. The RFC mandates that
 * email addresses have a domain part. However, that domain part may consist of
 * a single domain name component. As such, {@code foo@bar} is considered valid.
 * </p>
 */
public final class EmailAttribute
    extends AbstractFormatAttribute
{
    private static final FormatAttribute INSTANCE = new EmailAttribute();

    public static FormatAttribute getInstance()
    {
        return INSTANCE;
    }

    private EmailAttribute()
    {
        super("email", NodeType.STRING);
    }

    @Override
    public void validate(final ProcessingReport report,
        final MessageBundle bundle, final FullData data)
        throws ProcessingException
    {
        final String value = data.getInstance().getNode().textValue();

        try {
            new InternetAddress(value, true);
        } catch (AddressException ignored) {
            report.error(newMsg(data, bundle, "err.format.invalidEmail")
                .putArgument("value", value));
        }
    }
}
