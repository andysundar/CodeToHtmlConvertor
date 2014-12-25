/**
 * Copyright [2014] Anindya Bandopadhyay
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.convert.html;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.convert.Constant;

public final class HtmlFileCreator {

  public void createHtmlFile(File file) {
    String absolutePath = file.getAbsolutePath();
    int lastDot = absolutePath.lastIndexOf(".");
    int lastFileSeparator = absolutePath.lastIndexOf(File.separator) + 1;
    String fileName = absolutePath.substring(lastFileSeparator);
    String destinationDir = absolutePath.substring(0, lastFileSeparator);
    boolean isJava = fileName.endsWith("java");
    BufferedReader bufferReader = null;
    BufferedWriter bufferWriter = null;
    String htmlFile = absolutePath.substring(0, lastDot).concat(Constant.FILE_EXTENTION);

    try {

      createCssFile(destinationDir);
      bufferReader = new BufferedReader(new FileReader(file));
      String line = bufferReader.readLine();

      bufferWriter = new BufferedWriter(new FileWriter(htmlFile));
      bufferWriter.write("<html>");
      bufferWriter.write("<link rel='stylesheet' href='" + Constant.getCssFileName() + "' type='text/css'>");
      bufferWriter.write("<title>" + fileName + "</title>");
      bufferWriter.write("<body>\n<div id='code' class='codeBlock'>");
      bufferWriter.write("<div class='fileName'>" + fileName + "</div>");
      bufferWriter.write("<ol>");
      int lineNumber = 1;
      while (line != null) {
        String lineCSSStyle = "class='oddLineBorder'";
        if (lineNumber % 2 == 0) {
          lineCSSStyle = "class='evenLineBorder'";
        }
        line = line.replaceAll("&", "&amp;");
        line = line.replaceAll("<", "&lt;");
        line = line.replaceAll(">", "&gt;");
        line = line.replaceAll("\"", "&quot;");
        // line = line.replaceAll("'", "&apos;");

        if (isJava) {
          line = enhanceJavaKeyWords(line);
        }
        bufferWriter.write("<li " + lineCSSStyle + ">" + line + "</li>");
        bufferWriter.newLine();
        line = bufferReader.readLine();
        lineNumber++;
      }
      bufferWriter.write("</ol>\n</div>\n</div>\n<body>\n</html>");
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (bufferReader != null) {
          bufferReader.close();
        }
        if (bufferWriter != null) {
          bufferWriter.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void createCssFile(String destinationDir) throws FileNotFoundException, IOException {
    String cssFilePath = Constant.getWorkingDirPath().toString() + "css" + File.separator + Constant.getCssFileName();
    BufferedReader cssFileBufferReader = null;
    BufferedWriter cssFileBufferedWriter = null;
    File cssFile = new File(cssFilePath);
    try {
      if (cssFile.exists()) {
        cssFileBufferReader = new BufferedReader(new FileReader(cssFile));
        String cssFileDestination = destinationDir + File.separator + Constant.getCssFileName();
        File cssFileForDestination = new File(cssFileDestination);
        if (!cssFileForDestination.exists()) {
          cssFileBufferedWriter = new BufferedWriter(new FileWriter(cssFileForDestination));
          String line = cssFileBufferReader.readLine();
          while (line != null) {
            cssFileBufferedWriter.write(line);
            line = cssFileBufferReader.readLine();
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (cssFileBufferReader != null) {
          cssFileBufferReader.close();
        }
        if (cssFileBufferedWriter != null) {
          cssFileBufferedWriter.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private String enhanceJavaKeyWords(String line) {
    if(line.startsWith("//") || line.startsWith("/*") || line.startsWith("*/") || line.endsWith("*/")){
      return line;
    } 
    String eachWordList[] = line.split(" ");
    for (String word : eachWordList) {
      if(word.isEmpty()) continue;
      word = word.trim();
      for (String keyword : Constant.getJavaKeywordList()) {
        if(word.equals(keyword)) {
          line = line.replaceAll(keyword, "<span Class='javaKeyword'> " + keyword + "  </span>");
          break;
        }
      }
    }
    return line;
  }
}
