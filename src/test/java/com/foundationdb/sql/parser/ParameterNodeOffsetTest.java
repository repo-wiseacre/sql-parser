package com.foundationdb.sql.parser;

import com.foundationdb.sql.StandardException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParameterNodeOffsetTest {

  private static SQLParser sqlParser;

  @BeforeClass
  public static void init() {
    sqlParser = new SQLParser();
  }

  @AfterClass
  public static void cleanup() {
    sqlParser = null;
  }

  @Test
  public void testSingleParameter() throws StandardException {
    String sql = "SELECT * FROM foo WHERE X = ?";
    int[][] expectedOffsets = new int[][] {{28, 28}};

    List<ParameterNode> parameterList = parseAndGetParamNodes(sql);

    check(expectedOffsets, parameterList);
  }

  @Test
  public void testTwoParameters() throws StandardException {
    String sql = "SELECT * FROM foo WHERE X = ? and Y = ?";
    int[][] expectedOffsets = new int[][] {{28, 28}, {38, 38}};

    List<ParameterNode> parameterList = parseAndGetParamNodes(sql);

    check(expectedOffsets, parameterList);
  }

  @Test
  public void testTwoDifferentParameters() throws StandardException {
    String sql = "SELECT * FROM foo WHERE X = $1 and Y = ?";
    int[][] expectedOffsets = new int[][] {{28, 29}, {39, 39}};

    List<ParameterNode> parameterList = parseAndGetParamNodes(sql);

    check(expectedOffsets, parameterList);
  }

  private List<ParameterNode> parseAndGetParamNodes(String sql) throws StandardException {
    sqlParser.parseStatement(sql);
    return sqlParser.getParameterList();
  }

  private void check(int[][] expectedOffsets, List<ParameterNode> parameterList) {

    assertEquals(expectedOffsets.length, parameterList.size());

    int i = 0;
    for(ParameterNode node : parameterList) {
      int beginOffset = node.getBeginOffset();
      int endOffset = node.getEndOffset();
      assertEquals("Begin offset", expectedOffsets[i][0], beginOffset);
      assertEquals("End offset", expectedOffsets[i][1], endOffset);
      ++i;
    }
  }
}
