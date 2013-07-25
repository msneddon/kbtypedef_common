package com.github.fge.jsonschema.analyzer;

import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonschema.report.ListProcessingReport;
import com.github.fge.jsonschema.report.ProcessingReport;
import com.google.common.annotations.Beta;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

@Beta
public final class SchemaAnalysis
{
    private final Set<JsonPointer> visited;
    private final ListProcessingReport report;

    SchemaAnalysis(final Set<JsonPointer> visited,
        final ListProcessingReport report)
    {
        this.visited = ImmutableSet.copyOf(visited);
        this.report = report;
    }

    boolean isVisited(final JsonPointer ptr)
    {
        return visited.contains(ptr);
    }

    ProcessingReport getReport()
    {
        return report;
    }
}
