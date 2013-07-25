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

package com.github.fge.jsonschema.processors.format;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.NodeType;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.exceptions.ProcessingException;
import com.github.fge.jsonschema.format.FormatAttribute;
import com.github.fge.jsonschema.keyword.validator.KeywordValidator;
import com.github.fge.jsonschema.library.Dictionary;
import com.github.fge.jsonschema.library.Library;
import com.github.fge.jsonschema.messages.JsonSchemaValidationBundle;
import com.github.fge.jsonschema.processing.Processor;
import com.github.fge.jsonschema.processors.build.ValidatorBuilder;
import com.github.fge.jsonschema.processors.data.FullData;
import com.github.fge.jsonschema.processors.data.SchemaContext;
import com.github.fge.jsonschema.processors.data.ValidatorList;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.load.MessageBundles;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * Format attribute handler
 *
 * <p>This processor is run after {@link ValidatorBuilder} if and only if the
 * user has chosen to perform {@code format} validation (it is enabled by
 * default).</p>
 *
 * <p>It will append a specific {@link KeywordValidator} to the list of already
 * existing validators if and only if:</p>
 *
 * <ul>
 *     <li>there is a {@code format} keyword in the current schema;</li>
 *     <li>the specified format attribute is supported;</li>
 *     <li>the instance type is supported by this format attribute.</li>
 * </ul>
 *
 * <p>Note that it will warn if the format attribute is not recognized.</p>
 */
public final class FormatProcessor
    implements Processor<ValidatorList, ValidatorList>
{
    private final Map<String, FormatAttribute> attributes;
    private final MessageBundle bundle;

    public FormatProcessor(final Library library,
        final ValidationConfiguration cfg)
    {
        attributes = library.getFormatAttributes().entries();
        bundle = cfg.getValidationMessages();
    }

    @VisibleForTesting
    FormatProcessor(final Dictionary<FormatAttribute> dict)
    {
        attributes = dict.entries();
        bundle = MessageBundles.getBundle(JsonSchemaValidationBundle.class);
    }

    @Override
    public ValidatorList process(final ProcessingReport report,
        final ValidatorList input)
        throws ProcessingException
    {
        final SchemaContext context = input.getContext();
        final JsonNode node = context.getSchema().getNode().get("format");

        if (node == null)
            return input;

        final String fmt = node.textValue();
        final FormatAttribute attr = attributes.get(fmt);

        if (attr == null) {
            report.warn(input.newMessage().put("domain", "validation")
                .put("keyword", "format")
                .setMessage(bundle.getMessage("warn.format.notSupported"))
                .putArgument("attribute", fmt));
            return input;
        }

        final NodeType type = context.getInstanceType();

        if (!attr.supportedTypes().contains(type))
            return input;

        final List<KeywordValidator> validators = Lists.newArrayList(input);
        validators.add(formatValidator(attr));

        return new ValidatorList(context, validators);
    }

    private static KeywordValidator formatValidator(final FormatAttribute attr)
    {
        return new KeywordValidator()
        {
            @Override
            public void validate(
                final Processor<FullData, FullData> processor,
                final ProcessingReport report, final MessageBundle bundle,
                final FullData data)
                throws ProcessingException
            {
                attr.validate(report, bundle, data);
            }
        };
    }

    @Override
    public String toString()
    {
        return "format attribute handler";
    }
}
