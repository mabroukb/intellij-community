/*
 * Copyright 2000-2016 JetBrains s.r.o.
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
package com.intellij.psi.stubsHierarchy;

import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.impl.java.stubs.hierarchy.IndexTree;
import com.intellij.util.indexing.FileContent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author peter
 */
public abstract class StubHierarchyIndexer {
  public static final ExtensionPointName<StubHierarchyIndexer> EP_NAME = ExtensionPointName.create("com.intellij.hierarchy.indexer");

  public abstract int getVersion();

  /**
   * @return a list of pairs <packageName, compilation unit> for a specified file content
   */
  @Nullable
  public abstract IndexTree.Unit indexFile(@NotNull FileContent content);

  public abstract boolean handlesFile(@NotNull VirtualFile file);

}
