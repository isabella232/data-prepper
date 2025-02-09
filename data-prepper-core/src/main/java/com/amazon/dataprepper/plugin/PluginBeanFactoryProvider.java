/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package com.amazon.dataprepper.plugin;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import java.util.Objects;

/**
 * @since 1.3
 * <p>
 *     Used to create new instances of ApplicationContext that can be used to provide a per plugin instance isolated ApplicationContext
 *     scope. CoreApplicationContext is unavailable to sharedPluginApplicationContext and its children.
 * </p>
 * <p>pluginIsolatedApplicationContext inherits from {@link PluginBeanFactoryProvider#sharedPluginApplicationContext}</p>
 * <p>{@link PluginBeanFactoryProvider#sharedPluginApplicationContext} inherits from <i>publicContext</i></p>
 * <p><i>publicContext</i> is the root {@link ApplicationContext}</p>
 */
@Named
class PluginBeanFactoryProvider implements Provider<BeanFactory> {
    private final ApplicationContext sharedPluginApplicationContext;

    @Inject
    PluginBeanFactoryProvider(final ApplicationContext coreContext) {
        final ApplicationContext publicContext = Objects.requireNonNull(coreContext.getParent());
        sharedPluginApplicationContext = new GenericApplicationContext(publicContext);
    }

    /**
     * @since 1.3
     * Creates a new isolated application context that inherits from
     * {@link PluginBeanFactoryProvider#sharedPluginApplicationContext} then returns new context's BeanFactory.
     * {@link PluginBeanFactoryProvider#sharedPluginApplicationContext} should not be directly accessible to plugins.
     * instead, a new isolated {@link ApplicationContext} should be created.
     * @return BeanFactory A BeanFactory that inherits from {@link PluginBeanFactoryProvider#sharedPluginApplicationContext}
     */
    public BeanFactory get() {
        final GenericApplicationContext isolatedPluginApplicationContext = new GenericApplicationContext(sharedPluginApplicationContext);
        return isolatedPluginApplicationContext.getBeanFactory();
    }
}
