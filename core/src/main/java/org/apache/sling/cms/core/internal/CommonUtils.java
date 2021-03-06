/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.sling.cms.core.internal;

import java.util.Optional;

import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.jackrabbit.api.JackrabbitSession;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtils {

    private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);

    private CommonUtils() {
    }

    public static final UserManager getUserManager(ResourceResolver resolver) throws RepositoryException {
        return Optional.ofNullable(resolver.adaptTo(Session.class)).map(session -> {
            UserManager userManager = null;
            if (session instanceof JackrabbitSession) {
                try {
                    userManager = ((JackrabbitSession) session).getUserManager();
                } catch (RepositoryException e) {
                    log.error("Failed to get user manager", e);
                }

            }
            return userManager;
        }).orElseThrow(() -> new RepositoryException("Failed to get user manager"));
    }
}
