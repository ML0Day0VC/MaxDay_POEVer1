package API.Table; /**
 * @author Max Day
 * @author klaus31's
 * Created At: 2023/04/04
 */

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:https://github.com/klaus31/ascii-art-table</a>"
 * This was based off klaus31 ascii-art-table that can be accessed in the link above.
 * @author Max Day as rewritten 76% of it to be more efficient and better. This counts as almost an entire rewirte and since this is published under the MIT license (https://github.com/klaus31/ascii-art-table/blob/master/LICENSE)
 * which allows for odification and distrobution I may claim the rewirte as mine byt credit that it was his based of klaus31's
 * If there is any disagreement then comparasons to the origional and mine may be drawn
 * Changes to view the diff is here https://github.com/ML0Day0VC/MaxDay_POEVer1/commit/675ea44fee5f40b763d0fdd4c7494c9b37d8093c
 */

public class AsciiArtTable {

    /**
     * Adds padding tldr
     *
     * @param subject
     * @param length
     * @return
     */
    private static String appendToLength(final Object subject, final int length) {
        final String subjectString = subject == null ? "" : subject.toString();
        return subjectString.length() < length ? subjectString + StringUtils.repeat(' ', length - subjectString.length()) : subjectString;
    }

    /**
     * Adds padding tldr
     *
     * @param subject
     * @param length
     * @return
     */
    private static String prependToLength(final Object subject, final int length) {
        final String subjectString = subject == null ? "" : subject.toString();
        return subjectString.length() < length ? StringUtils.repeat(' ', length - subjectString.length()) + subjectString : subjectString;
    }

    private final String borderCharacters;
    private final List<Object> contentCols, headerCols, headlines;
    private final int maxColumnWidth = 80;
    private final boolean minimiseHeight = false;
    private final int padding;

    public AsciiArtTable() {
        this(1);
    }

    public AsciiArtTable(final int padding) {
        this(padding, "╔═╤╗║╟─┬╢╪╠╣│╚╧╝┼");
    } // ╔ ═ ╤ ╗ ║ ╟ ─ ┬ ╢ ╪ ╠ ╣ │ ╚ ╧ ╝ ┼

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

    /**
     * Shifts the data to the left to make the table cleaner. This can be right side but middle becomes difficult with alignment
     *
     * @param linesContents
     * @param col
     * @return
     */
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

    /**
     * Gets the length of each of the columns
     *
     * @return
     */
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

    /**
     * Generates main output. The main structure is to build the data then create an object for each line of output add the data and row and save it to the string
     *
     * @return
     */
    public String getOutput() {
        while (contentCols.size() % headerCols.size() != 0)
            contentCols.add("");
        StringBuilder result = new StringBuilder();
        if (headlines.isEmpty()) {
            result.append(row(borderCharacters.charAt(0), borderCharacters.charAt(1), borderCharacters.charAt(2), borderCharacters.charAt(3))).append(System.lineSeparator());
        } else {
            result.append(row(borderCharacters.charAt(0), borderCharacters.charAt(1), borderCharacters.charAt(1), borderCharacters.charAt(3))).append(System.lineSeparator());
            for (Object headline : headlines) {
                result.append(rowHeadline(headline.toString(), borderCharacters.charAt(4), borderCharacters.charAt(4)));
                if (headlines.indexOf(headline) == headlines.size() - 1) {
                    if (outputOfHeaderColsIsRequested()) {
                        result.append(row(borderCharacters.charAt(5), borderCharacters.charAt(6), borderCharacters.charAt(7), borderCharacters.charAt(8))).append(System.lineSeparator());
                    } else {
                        result.append(row(borderCharacters.charAt(10), borderCharacters.charAt(1), borderCharacters.charAt(2), borderCharacters.charAt(11))).append(System.lineSeparator());
                    }
                } else if (!minimiseHeight) {
                    result.append(row(borderCharacters.charAt(5), borderCharacters.charAt(6), borderCharacters.charAt(6), borderCharacters.charAt(8))).append(System.lineSeparator());
                }
            }
        }
        if (outputOfHeaderColsIsRequested()) {
            result.append(row(headerCols, borderCharacters.charAt(4), borderCharacters.charAt(12), borderCharacters.charAt(4))).append(System.lineSeparator());
            result.append(row(borderCharacters.charAt(10), borderCharacters.charAt(1), borderCharacters.charAt(9), borderCharacters.charAt(11))).append(System.lineSeparator());
        }
        int col = 0;
        while (col < contentCols.size()) {
            result.append(row(contentCols.subList(col, col + headerCols.size()), borderCharacters.charAt(4), borderCharacters.charAt(12), borderCharacters.charAt(4))).append(System.lineSeparator());
            col += headerCols.size();
            if (col == contentCols.size()) {
                result.append(row(borderCharacters.charAt(13), borderCharacters.charAt(1), borderCharacters.charAt(14), borderCharacters.charAt(15))).append(System.lineSeparator());
            } else if (!minimiseHeight) {
                result.append(row(borderCharacters.charAt(5), borderCharacters.charAt(6), borderCharacters.charAt(16), borderCharacters.charAt(8))).append(System.lineSeparator());
            }
        }
        return result.toString();
    }

    /**
     * Takes the max lengths of the columns and then adds padding(spaces on the sides) to make it look neat
     *
     * @return
     */
    private int getTableLength() {
        final int[] colWidths = getColWidths();
        int totalPadding = 2 * padding * colWidths.length;
        int totalColWidths = Arrays.stream(colWidths).sum();
        int totalColumnSeparators = colWidths.length - 1;
        int totalChars = totalPadding + totalColWidths + totalColumnSeparators;
        return totalChars + 2;
    }

    /**
     * Boolean that checks the length of the string of the header  column object to check if it's greater than 0.
     *
     * @return
     */
    private boolean outputOfHeaderColsIsRequested() {
        return headerCols.stream().anyMatch(h -> h.toString().length() > 0);
    }

    public void print(final PrintStream printStream) {
        printStream.print(getOutput());
    }

    /**
     * Table builder. Takes the specialised characters for the table and forms a string of characters to structure the table
     *
     * @param left
     * @param middle
     * @param columnSeparator
     * @param right
     * @return
     */
    private String row(final char left, final char middle, final char columnSeparator, final char right) {
        final int[] colWidths = getColWidths();
        StringBuilder result = new StringBuilder(left + "");
        int col = 0;
        while (col < headerCols.size()) {
            result.append(StringUtils.repeat(middle, padding + colWidths[col] + padding));
            col++;
            result.append(col == headerCols.size() ? right : columnSeparator);
        }
        return result.toString();
    }

    /**
     * Table builder. Takes the specialised characters for the table and forms a string with each row with data included. This overrides the method above
     *
     * @param contents
     * @param left
     * @param columnSeparator
     * @param right
     * @return
     */
    private String row(final List<Object> contents, final char left, final char columnSeparator, final char right) {
        final int[] colWidths = getColWidths();
        StringBuilder result = new StringBuilder();
        List<List<String>> linesContents = splitToMaxLength(contents, maxColumnWidth);
        for (List<String> lineContents : linesContents) {
            int col = 0;
            result.append(left);
            while (col < headerCols.size()) {
                if (alignLeft(linesContents, col)) {
                    result.append(StringUtils.repeat(' ', padding));
                    result.append(appendToLength(lineContents.get(col), padding + colWidths[col]));
                } else {
                    result.append(prependToLength(lineContents.get(col), padding + colWidths[col]));
                    result.append(StringUtils.repeat(' ', padding));
                }
                col++;
                result.append(col == headerCols.size() ? right : columnSeparator);
            }
            if (linesContents.indexOf(lineContents) != linesContents.size() - 1) {
                result.append(System.lineSeparator());
            }
        }
        return result.toString();
    }

    /**
     * Generates the header of the table with the titles
     *
     * @param headline
     * @param left
     * @param right
     * @return
     */
    private String rowHeadline(final String headline, final char left, final char right) {
        final int tableLength = getTableLength();
        final int contentWidth = tableLength - (2 * padding) - 2;
        final String[] headlineLines = headline.split("(?<=\\G.{" + contentWidth + "})");
        StringBuilder result = new StringBuilder();
        for (String headlineLine : headlineLines) {
            result.append(left).append(StringUtils.repeat(' ', padding)).append(StringUtils.rightPad(headlineLine, tableLength - padding - 2)).append(right).append(System.lineSeparator());
        }
        return result.toString();
    }

    /**
     * Makes sure each line ends with a complete word and that the input is split into a list structure where each row.
     *
     * @param subjects
     * @param maxLength
     * @return
     */
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
            while (cellContentLines.size() < result.size() + 1)
                cellContentLines.add("");
            result.add(cellContentLines);
        }
        return transpose(result);
    }

    /**
     * Method is used to flip the colum with matrix to make it easier to edit the columns in certain scenarios
     *
     * @param matrix
     * @return
     */
    private List<List<String>> transpose(List<List<String>> matrix) {
        List<List<String>> result = new ArrayList<>();
        for (int col = 0; col < matrix.get(0).size(); col++) {
            List<String> row = new ArrayList<>();
            for (List<String> strings : matrix) {
                row.add(strings.get(col));
            }
            result.add(row);
        }
        return result;
    }
}