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

package com.github.fge.msgsimple.serviceloader;

import com.github.fge.msgsimple.bundle.MessageBundle;
import com.github.fge.msgsimple.load.MessageBundleLoader;

import java.util.ServiceLoader;

/**
 * {@link ServiceLoader} implementation for this library
 *
 * @see MessageBundleFactory
 *
 * @deprecated use {@link MessageBundleLoader} instead. Will disappear in 1.0.
 */
@Deprecated
public interface MessageBundleProvider
{
    /**
     * Get a message bundle
     *
     * @return the generated bundle
     */
    MessageBundle getBundle();
}
