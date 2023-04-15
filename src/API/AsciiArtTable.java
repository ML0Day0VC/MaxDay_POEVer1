package API; /**
 * @author Max Day
 * Created At: 2023/04/04
 */

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:https://github.com/klaus31/ascii-art-table"
 * This was based off klaus31 ascii-art-table that can be accessed in the link above.
 * @author Max Day as rewritten 76% of it to be more efficient and better. This counts as almost an entire rewirte and since this is published under the MIT license (https://github.com/klaus31/ascii-art-table/blob/master/LICENSE)
 * which allows for odification and distrobution I may claim the rewirte as mine byt credit that it was his based of klaus31's
 * If there is any disagreement then comparasons to the origional and mine may be drawn
 * Changes to view the diff is here https://github.com/ML0Day0VC/MaxDay_POEVer1/commit/675ea44fee5f40b763d0fdd4c7494c9b37d8093c
 */

public class AsciiArtTable {

    private static String appendToLength(final Object subject, final int length) {
        final String subjectString = subject == null ? "" : subject.toString();
        return subjectString.length() < length ? subjectString + StringUtils.repeat(' ', length - subjectString.length()) : subjectString;
    }

    private static String prependToLength(final Object subject, final int length) {
        final String subjectString = subject == null ? "" : subject.toString();
        return subjectString.length() < length ? StringUtils.repeat(' ', length - subjectString.length()) + subjectString : subjectString;
    }

    private String borderCharacters;
    private final List<Object> contentCols, headerCols, headlines;
    private int maxColumnWidth = 80;
    private boolean minimiseHeight = false;
    private final int padding;

    public AsciiArtTable() {
        this(1);
    }

    public AsciiArtTable(final int padding) {
        this(padding, "╔═╤╗║╟─┬╢╪╠╣│╚╧╝┼");
    } // ╔ ═ ╤ ╗ ║ ╟ ─ ┬ ╢ ╪ ╠ ╣ │ ╚ ╧ ╝ ┼
    //_ _ _
    //       |
    //       __
    //╔═╤╗║╟─┬╢╪╠╣│╚╧╝┼

    public AsciiArtTable(final int padding, final String borderCharacters) {
        this.headerCols = new ArrayList<>();
        this.contentCols = new ArrayList<>();
        this.headlines = new ArrayList<>();
        this.padding = padding;
        this.borderCharacters = borderCharacters;
    }

    public void add(final List<Object> contentCols) {
        this.contentCols.addAll(contentCols);
    }

    public void add(final Object... contentCols) {
        add(new ArrayList<>(Arrays.asList(contentCols)));
    }

    public void addHeaderCols(final List<Object> headerCols) {
        this.headerCols.addAll(headerCols);
    }

    public void addHeaderCols(final Object... headerCols) {
        addHeaderCols(new ArrayList<>(Arrays.asList(headerCols)));
    }

    public void addHeadline(final Object headline) {
        this.headlines.add(headline);
    }

    private boolean alignLeft(final List<List<String>> linesContents, final int col) {
        boolean result = false;
        if (linesContents.size() > 1) {
            for (int i = 1; i < linesContents.size(); i++) {
                if (!linesContents.get(i).get(col).trim().isEmpty()) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public void clear() {
        headerCols.clear();
        contentCols.clear();
        headlines.clear();
    }

    private int[] getColWidths() {
        int[] result = new int[headerCols.size()];
        for (int col = 0; col < headerCols.size(); col++) {
            int length = headerCols.get(col).toString().length();
            result[col] = Math.min(length, maxColumnWidth);
        }
        for (int index = 0; index < contentCols.size(); index++) {
            int col = index % headerCols.size();
            String content = contentCols.get(index) == null ? "" : contentCols.get(index).toString();
            int length = content.length();
            if (length > result[col]) {
                result[col] = Math.min(length, maxColumnWidth);
            }
        }
        return result;
    }

    public String getOutput() {
        // prepare data
        while (contentCols.size() % headerCols.size() != 0) {
            contentCols.add("");
        }
        // build header
        String result = "";
        if (headlines.isEmpty()) {
            result += row(borderCharacters.charAt(0), borderCharacters.charAt(1), borderCharacters.charAt(2), borderCharacters.charAt(3)) + System.lineSeparator();
        } else {
            result += row(borderCharacters.charAt(0), borderCharacters.charAt(1), borderCharacters.charAt(1), borderCharacters.charAt(3)) + System.lineSeparator();
            for (Object headline : headlines) {
                result += rowHeadline(headline.toString(), borderCharacters.charAt(4), borderCharacters.charAt(4));
                if (headlines.indexOf(headline) == headlines.size() - 1) {
                    if (outputOfHeaderColsIsRequested()) {
                        result += row(borderCharacters.charAt(5), borderCharacters.charAt(6), borderCharacters.charAt(7), borderCharacters.charAt(8)) + System.lineSeparator();
                    } else {
                        result += row(borderCharacters.charAt(10), borderCharacters.charAt(1), borderCharacters.charAt(2), borderCharacters.charAt(11)) + System.lineSeparator();
                    }
                } else if (!minimiseHeight) {
                    result += row(borderCharacters.charAt(5), borderCharacters.charAt(6), borderCharacters.charAt(6), borderCharacters.charAt(8)) + System.lineSeparator();
                }
            }
        }
        if (outputOfHeaderColsIsRequested()) {
            result += row(headerCols, borderCharacters.charAt(4), borderCharacters.charAt(12), borderCharacters.charAt(4)) + System.lineSeparator();
            result += row(borderCharacters.charAt(10), borderCharacters.charAt(1), borderCharacters.charAt(9), borderCharacters.charAt(11)) + System.lineSeparator();
        }
        int col = 0;
        while (col < contentCols.size()) {
            result += row(contentCols.subList(col, col + headerCols.size()), borderCharacters.charAt(4), borderCharacters.charAt(12), borderCharacters.charAt(4)) + System.lineSeparator();
            col += headerCols.size();
            if (col == contentCols.size()) {
                result += row(borderCharacters.charAt(13), borderCharacters.charAt(1), borderCharacters.charAt(14), borderCharacters.charAt(15)) + System.lineSeparator();
            } else if (!minimiseHeight) {
                result += row(borderCharacters.charAt(5), borderCharacters.charAt(6), borderCharacters.charAt(16), borderCharacters.charAt(8)) + System.lineSeparator();
            }
        }
        return result;
    }

    private int getTableLength() {
        final int[] colWidths = getColWidths();
        int totalPadding = 2 * padding * colWidths.length;
        int totalColWidths = Arrays.stream(colWidths).sum();
        int totalColumnSeparators = colWidths.length - 1;
        int totalChars = totalPadding + totalColWidths + totalColumnSeparators;
        return totalChars + 2; // add 2 for the border characters
    }

    public void minimiseHeight() {
        minimiseHeight = true;
    }

    private boolean outputOfHeaderColsIsRequested() {
        return headerCols.stream().anyMatch(h -> h.toString().length() > 0);
    }


    public void print(final PrintStream printStream) {
        printStream.print(getOutput());
    }

    private String row(final char left, final char middle, final char columnSeparator, final char right) {
        final int[] colWidths = getColWidths();
        String result = left + "";
        int col = 0;
        while (col < headerCols.size()) {
            result += StringUtils.repeat(middle, padding + colWidths[col] + padding);
            col++;
            result += col == headerCols.size() ? right : columnSeparator;
        }
        return result;
    }

    private String row(final List<Object> contents, final char left, final char columnSeparator, final char right) {
        final int[] colWidths = getColWidths();
        String result = "";
        List<List<String>> linesContents = splitToMaxLength(contents, maxColumnWidth);
        for (List<String> lineContents : linesContents) {
            int col = 0;
            result += left;
            while (col < headerCols.size()) {
                if (alignLeft(linesContents, col)) {
                    result += StringUtils.repeat(' ', padding);
                    result += appendToLength(lineContents.get(col), padding + colWidths[col]);
                } else {
                    result += prependToLength(lineContents.get(col), padding + colWidths[col]);
                    result += StringUtils.repeat(' ', padding);
                }
                col++;
                result += col == headerCols.size() ? right : columnSeparator;
            }
            if (linesContents.indexOf(lineContents) != linesContents.size() - 1) {
                result += System.lineSeparator();
            }
        }
        return result;
    }

    private String rowHeadline(final String headline, final char left, final char right) {
        final int tableLength = getTableLength();
        final int contentWidth = tableLength - (2 * padding) - 2;

        // split into headline rows
        final List<String> headlineLines = Arrays.asList(headline.split("(?<=\\G.{" + contentWidth + "})"));

        // build result
        String result = "";
        for (String headlineLine : headlineLines) {
            result += left + StringUtils.repeat(' ', padding) + StringUtils.rightPad(headlineLine, tableLength - padding - 2) + right + System.lineSeparator();
        }
        return result;
    }

    public void setBorderCharacters(final String borderCharacters) {
        this.borderCharacters = borderCharacters;
    }

    public void setMaxColumnWidth(final int maxColumnWidth) {
        this.maxColumnWidth = maxColumnWidth;
    }

    public void setNoHeaderColumns(int withColumns) {
        this.headerCols.clear();
        while (withColumns-- > 0) {
            this.headerCols.add("");
        }
    }


    private List<List<String>> splitToMaxLength(final List<Object> subjects, final int maxLength) {
        List<List<String>> result = new ArrayList<>();

        for (Object subject : subjects) {
            String content = subject.toString();
            List<String> cellContentLines = new ArrayList<>();

            while (content.length() > maxLength) {
                int index = content.substring(0, maxLength).lastIndexOf(' ');
                if (index == -1) {
                    index = maxLength;
                }
                String line = content.substring(0, index);
                cellContentLines.add(line);
                content = content.substring(index + 1);
            }

            cellContentLines.add(content);

            while (cellContentLines.size() < result.size() + 1) {
                cellContentLines.add("");
            }

            result.add(cellContentLines);
        }

        return transpose(result);
    }

    private List<List<String>> transpose(List<List<String>> matrix) {
        List<List<String>> result = new ArrayList<>();

        for (int col = 0; col < matrix.get(0).size(); col++) {
            List<String> row = new ArrayList<>();
            for (int rowIdx = 0; rowIdx < matrix.size(); rowIdx++) {
                row.add(matrix.get(rowIdx).get(col));
            }
            result.add(row);
        }
        return result;
    }

}