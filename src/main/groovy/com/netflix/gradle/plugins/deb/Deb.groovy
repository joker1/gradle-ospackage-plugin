/*
 * Copyright 2011 the original author or authors.
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

package com.netflix.gradle.plugins.deb

import com.netflix.gradle.plugins.packaging.AbstractPackagingCopyAction
import com.netflix.gradle.plugins.packaging.SystemPackagingTask
import org.gradle.api.internal.ConventionMapping
import org.gradle.api.internal.IConventionAware
import org.freecompany.redline.header.Architecture

class Deb extends SystemPackagingTask {
    static final String DEB_EXTENSION = "deb";

    static final Map ARCH_MAP = [:]

    static {
        ARCH_MAP[Architecture.ALPHA] = 'alpha'
        ARCH_MAP[Architecture.ARM] = 'arm'
        ARCH_MAP[Architecture.I386] = 'i386'
        ARCH_MAP[Architecture.IA64] = 'ia64'
        // ARCH_MAP[Architecture.IP] = '??'
        ARCH_MAP[Architecture.M68K] = 'm68k'
        ARCH_MAP[Architecture.MIPS] = 'mips'
        ARCH_MAP[Architecture.MIPSEL] = 'mipsel'
        //ARCH_MAP[Architecture.MK68KMINT] = '??'
        ARCH_MAP[Architecture.NOARCH] = 'all'
        ARCH_MAP[Architecture.PPC] = 'powerpc'
        ARCH_MAP[Architecture.PPC64] = 'ppc64'
        //ARCH_MAP[Architecture.RS6000] = '??'
        ARCH_MAP[Architecture.S390] = 's390'
        ARCH_MAP[Architecture.S390X] = 's390x'
        //ARCH_MAP[Architecture.SH] = '??'
        ARCH_MAP[Architecture.SPARC] = 'sparc'
        ARCH_MAP[Architecture.SPARC64] = 'sparc64'
        ARCH_MAP[Architecture.X86_64] = 'amd64'
        //ARCH_MAP[Architecture.XTENSA] = '??'
    }

    Deb() {
        super()
        extension = DEB_EXTENSION
    }

    @Override
    String assembleArchiveName() {
        String name = getPackageName();
        name += getVersion() ? "_${getVersion()}" : ''
        name += getRelease() ? "-${getRelease()}" : ''
        name += getArchString() ? "_${getArchString()}" : ''
        name += getExtension() ? ".${getExtension()}" : ''
        return name;
    }

    @Override
    protected String getArchString() {
        return ARCH_MAP[arch]
    }

    @Override
    AbstractPackagingCopyAction createCopyAction() {
        return new DebCopyAction(this)
    }

    @Override
    protected void applyConventions() {
        super.applyConventions()

        // For all mappings, we're only being called if it wasn't explicitly set on the task. In which case, we'll want
        // to pull from the parentExten. And only then would we fallback on some other value.
        ConventionMapping mapping = ((IConventionAware) this).getConventionMapping()

        // Could come from extension
        mapping.map('uid', { parentExten?.getUid()?:0 })
        mapping.map('gid', { (parentExten?.getGid())?:0 })
        mapping.map('packageGroup', { parentExten?.getPackageGroup() ?: 'java' })
        mapping.map('arch', { parentExten?.getArch()?:Architecture.NOARCH})

    }
}
