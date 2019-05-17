/*
 * Copyright 2017-2019 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.data.processor.visitors.finders;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.data.intercept.CountInterceptor;
import io.micronaut.data.model.query.Query;
import io.micronaut.data.processor.visitors.MatchContext;
import io.micronaut.data.processor.visitors.MethodMatchContext;
import io.micronaut.inject.ast.ClassElement;
import io.micronaut.inject.ast.MethodElement;

import edu.umd.cs.findbugs.annotations.Nullable;

/**
 * Dynamic finder for support for counting.
 *
 * @author graemerocher
 * @since 1.0.0
 */
public class CountByMethod extends DynamicFinder {

    /**
     * Default constructor.
     */
    public CountByMethod() {
        super("count");
    }

    @Nullable
    @Override
    protected MethodMatchInfo buildInfo(@NonNull MethodMatchContext matchContext, ClassElement queryResultType, @Nullable Query query) {
        if (query != null) {
            query.projections().count();
            return new MethodMatchInfo(
                    matchContext.getReturnType(),
                    query,
                    CountInterceptor.class
            );
        } else {
            return new MethodMatchInfo(
                    matchContext.getReturnType(),
                    null,
                    CountInterceptor.class
            );
        }
    }

    @Override
    public boolean isMethodMatch(MethodElement methodElement, MatchContext matchContext) {
        return super.isMethodMatch(methodElement, matchContext) && TypeUtils.doesReturnNumber(methodElement);
    }
}