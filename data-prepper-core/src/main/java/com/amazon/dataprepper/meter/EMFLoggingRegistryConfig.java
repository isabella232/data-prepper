/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package com.amazon.dataprepper.meter;

import io.micrometer.core.instrument.step.StepRegistryConfig;

public interface EMFLoggingRegistryConfig extends StepRegistryConfig {
    EMFLoggingRegistryConfig DEFAULT = k -> null;

    @Override
    default String prefix() {
        return "emf";
    }
}
