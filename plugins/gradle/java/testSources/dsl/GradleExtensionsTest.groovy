// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package org.jetbrains.plugins.gradle.dsl

import groovy.transform.CompileStatic
import org.jetbrains.plugins.gradle.highlighting.GradleHighlightingBaseTest
import org.jetbrains.plugins.groovy.lang.psi.api.statements.blocks.GrClosableBlock
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.GrReferenceExpression
import org.jetbrains.plugins.groovy.lang.psi.api.statements.expressions.path.GrMethodCallExpression
import org.jetbrains.plugins.groovy.lang.psi.api.statements.typedef.members.GrMethod
import org.jetbrains.plugins.groovy.lang.resolve.api.GroovyProperty
import org.jetbrains.plugins.groovy.lang.resolve.delegatesTo.GrDelegatesToUtilKt
import org.jetbrains.plugins.groovy.util.ResolveTest
import org.junit.Test

@CompileStatic
class GradleExtensionsTest extends GradleHighlightingBaseTest implements ResolveTest {

  protected List<String> getParentCalls() {
    // todo resolve extensions also for non-root places
    return []
  }

  @Test
  void extensionsTest() {
    importProject("")
    "project level extension property"()
    "project level extension call type"()
    "project level extension closure delegate type"()
  }

  void "project level extension property"() {
    doTest("ext") {
      def ref = elementUnderCaret(GrReferenceExpression)
      assert ref.resolve() instanceof GroovyProperty
      assert ref.type.equalsToText(getExtraPropertiesExtensionFqn())
    }
  }

  void "project level extension call type"() {
    doTest("ext {}") {
      def call = elementUnderCaret(GrMethodCallExpression)
      assert call.resolveMethod() instanceof GrMethod
      assert call.type.equalsToText(getExtraPropertiesExtensionFqn())
    }
  }

  void "project level extension closure delegate type"() {
    doTest("ext {<caret>}") {
      def closure = elementUnderCaret(GrClosableBlock)
      def info = GrDelegatesToUtilKt.getDelegatesToInfo(closure)
      assert info != null
      assert info.typeToDelegate.equalsToText(getExtraPropertiesExtensionFqn())
    }
  }

  private String getExtraPropertiesExtensionFqn() {
    isGradleOlderThen_5_2() ? "org.gradle.api.internal.plugins.DefaultExtraPropertiesExtension"
                            : "org.gradle.internal.extensibility.DefaultExtraPropertiesExtension"
  }
}
