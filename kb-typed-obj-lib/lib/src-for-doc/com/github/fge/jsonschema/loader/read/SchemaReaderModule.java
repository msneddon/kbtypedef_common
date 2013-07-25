package com.github.fge.jsonschema.loader.read;

import com.github.fge.jsonschema.messages.JsonSchemaCoreMessageBundle;
import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.load.MessageBundles;
import com.google.common.annotations.Beta;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import javax.inject.Provider;

@Beta
public abstract class SchemaReaderModule
    extends AbstractModule
    implements Provider<SchemaReader>
{
    protected static final MessageBundle BUNDLE
        = MessageBundles.getBundle(JsonSchemaCoreMessageBundle.class);

    @Override
    protected final void configure()
    {
    }

    @Provides
    public final SchemaReader getReader()
    {
        return newReader();
    }

    @Override
    public final SchemaReader get()
    {
        return newReader();
    }

    protected abstract SchemaReader newReader();
}
