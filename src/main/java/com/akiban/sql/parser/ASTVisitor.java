/**
 * END USER LICENSE AGREEMENT (“EULA”)
 *
 * READ THIS AGREEMENT CAREFULLY (date: 9/13/2011):
 * http://www.akiban.com/licensing/20110913
 *
 * BY INSTALLING OR USING ALL OR ANY PORTION OF THE SOFTWARE, YOU ARE ACCEPTING
 * ALL OF THE TERMS AND CONDITIONS OF THIS AGREEMENT. YOU AGREE THAT THIS
 * AGREEMENT IS ENFORCEABLE LIKE ANY WRITTEN AGREEMENT SIGNED BY YOU.
 *
 * IF YOU HAVE PAID A LICENSE FEE FOR USE OF THE SOFTWARE AND DO NOT AGREE TO
 * THESE TERMS, YOU MAY RETURN THE SOFTWARE FOR A FULL REFUND PROVIDED YOU (A) DO
 * NOT USE THE SOFTWARE AND (B) RETURN THE SOFTWARE WITHIN THIRTY (30) DAYS OF
 * YOUR INITIAL PURCHASE.
 *
 * IF YOU WISH TO USE THE SOFTWARE AS AN EMPLOYEE, CONTRACTOR, OR AGENT OF A
 * CORPORATION, PARTNERSHIP OR SIMILAR ENTITY, THEN YOU MUST BE AUTHORIZED TO SIGN
 * FOR AND BIND THE ENTITY IN ORDER TO ACCEPT THE TERMS OF THIS AGREEMENT. THE
 * LICENSES GRANTED UNDER THIS AGREEMENT ARE EXPRESSLY CONDITIONED UPON ACCEPTANCE
 * BY SUCH AUTHORIZED PERSONNEL.
 *
 * IF YOU HAVE ENTERED INTO A SEPARATE WRITTEN LICENSE AGREEMENT WITH AKIBAN FOR
 * USE OF THE SOFTWARE, THE TERMS AND CONDITIONS OF SUCH OTHER AGREEMENT SHALL
 * PREVAIL OVER ANY CONFLICTING TERMS OR CONDITIONS IN THIS AGREEMENT.
 */

/* The original from which this derives bore the following: */

/*

   Derby - Class org.apache.derby.iapi.sql.compile.ASTVisitor

   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to you under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

package com.akiban.sql.parser;

import com.akiban.sql.StandardException;

/**
 * <p>
 * A Visitor which handles nodes in Derby's abstract syntax trees. In addition
 * to this contract, it is expected that an ASTVisitor will have a 0-arg
 * constructor. You use an ASTVisitor like this:
 * </p>
 *
 * <blockquote><pre>
 * // initialize your visitor
 * MyASTVisitor myVisitor = new MyASTVisitor();
 * myVisitor.initializeVisitor();
 * languageConnectionContext.setASTVisitor( myVisitor );
 *
 * // then run your queries.
 * ...
 *
 * // when you're done inspecting query trees, release resources and
 * // remove your visitor
 * languageConnectionContext.setASTVisitor( null );
 * myVisitor.teardownVisitor();
 * </pre></blockquote>
 *
 */
public interface ASTVisitor extends Visitor
{
    // Compilation phases for tree handling

    public static final int AFTER_PARSE = 0;
    public static final int AFTER_BIND = 1;
    public static final int AFTER_OPTIMIZE = 2;

    /**
     * Initialize the Visitor before processing any trees. User-written code
     * calls this method before poking the Visitor into the
     * LanguageConnectionContext. For example, an
     * implementation of this method might open a trace file.
     */
    public void initializeVisitor() throws StandardException;

    /**
     * Final call to the Visitor. User-written code calls this method when it is
     * done inspecting query trees. For instance, an implementation of this method
     * might release resources, closing files it has opened.
     */
    public void teardownVisitor() throws StandardException;

    /**
     * The compiler calls this method just before walking a query tree.
     *
     * @param statementText Text used to create the tree.
     * @param phase of compilation (AFTER_PARSE, AFTER_BIND, or AFTER_OPTIMIZE).
     */
    public void begin(String statementText, int phase) throws StandardException;
        
    /**
     * The compiler calls this method when it's done walking a tree.
     *
     * @param phase of compilation (AFTER_PARSE, AFTER_BIND, or AFTER_OPTIMIZE).
     */
    public void end(int phase) throws StandardException;
        
}
