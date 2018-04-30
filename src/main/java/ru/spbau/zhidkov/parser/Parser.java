package ru.spbau.zhidkov.parser;

import ru.spbau.zhidkov.environment.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for parsing input
 */
public class Parser {

    private enum PieceType {
        OUTSIDE,
        SPACES,
        INSIDE_SINGLE_QUOTES,
        INSIDE_DOUBLE_QUOTES
    }

    /**
     * Divides input by pipes and parses pieces as commands
     *
     * @param environment environment
     * @param text        input string
     * @return list of command calls divided by pipes
     */
    public List<CommandCall> process(Environment<String, String> environment, String text) {
        final List<CommandCall> commandCalls = new ArrayList<>();
        final List<List<Piece>> pieces = splitOnPieces(text);
        final List<List<Piece>> piecesWithSubstitutions = new ArrayList<>();
        for (List<Piece> list : pieces) {
            final List<Piece> newList = new ArrayList<>();
            for (Piece piece : list) {
                if (piece.getPieceType() != PieceType.INSIDE_SINGLE_QUOTES) {
                    newList.add(new Piece(piece.getPieceType(), substitute(piece.getText(), environment)));
                } else {
                    newList.add(piece);
                }
            }
            piecesWithSubstitutions.add(newList);
        }

        for (List<Piece> list : piecesWithSubstitutions) {
            final String textWithSubstitutions = list.stream()
                    .map(Piece::getText)
                    .collect(Collectors.joining());
            int eqPos = textWithSubstitutions.indexOf("=");
            if (eqPos != -1) {
                final List<String> args = new ArrayList<>();
                args.add(textWithSubstitutions.substring(0, eqPos));
                args.add(textWithSubstitutions.substring(eqPos + 1));
                commandCalls.add(new CommandCall("=", args));
            } else {
                commandCalls.add(convertToCommandCall(list));
            }
        }


        return commandCalls;
    }

    private CommandCall convertToCommandCall(List<Piece> list) {
        StringBuilder curText = new StringBuilder();
        final List<String> textPieces = new ArrayList<>();
        for (int i = 0; i < list.size() + 1; i++) {
            if (i == list.size() || list.get(i).getPieceType() == PieceType.SPACES) {
                if (!curText.toString().equals("")) {
                    textPieces.add(curText.toString());
                }
                curText = new StringBuilder();
            } else {
                curText.append(list.get(i).getText());
            }
        }
        if (textPieces.size() == 0) {
            throw new ParseException("No text between pipes");
        }
        return new CommandCall(textPieces.get(0), textPieces.subList(1, textPieces.size()));
    }

    private List<List<Piece>> splitOnPieces(String text) {
        int pos = 0;
        StringBuilder curText = new StringBuilder();
        final List<List<Piece>> res = new ArrayList<>();
        List<Piece> pieces = new ArrayList<>();
        while (pos < text.length()) {
            final char symbol = text.charAt(pos);
            if (symbol == '\'' || symbol == '\"') {
                if (!curText.toString().equals("")) {
                    pieces.add(new Piece(PieceType.OUTSIDE, curText.toString()));
                }
                curText = new StringBuilder();
                int j = pos + 1;
                while (j < text.length() && text.charAt(j) != symbol) {
                    j++;
                }
                if (j == text.length()) {
                    throw new ParseException("unmatched symbol (" + symbol + ")");
                }
                final PieceType pieceType = symbol == '\''
                        ? PieceType.INSIDE_SINGLE_QUOTES
                        : PieceType.INSIDE_DOUBLE_QUOTES;
                pieces.add(new Piece(pieceType, text.substring(pos + 1, j)));
                pos = j + 1;
            } else if (symbol == '|') {
                if (!curText.toString().equals("")) {
                    pieces.add(new Piece(PieceType.OUTSIDE, curText.toString()));
                }
                curText = new StringBuilder();
                res.add(pieces);
                pieces = new ArrayList<>();
                pos++;
            } else if (symbol == ' ') {
                if (!curText.toString().equals("")) {
                    pieces.add(new Piece(PieceType.OUTSIDE, curText.toString()));
                }
                curText = new StringBuilder();
                int j = pos;
                while (j < text.length() && text.charAt(j) == ' ') {
                    j++;
                    curText.append(" ");
                }
                pos = j;
                pieces.add(new Piece(PieceType.SPACES, curText.toString()));
                curText = new StringBuilder();
            } else {
                curText.append(symbol);
                pos++;
            }
        }
        if (!curText.toString().equals("")) {
            pieces.add(new Piece(PieceType.OUTSIDE, curText.toString()));
        }
        if (pieces.size() != 0) {
            res.add(pieces);
        }
        return res;
    }

    private String substitute(String text, Environment<String, String> environment) {
        final StringBuilder stringBuilder = new StringBuilder();
        int pos = 0;
        while (pos < text.length()) {
            final char symbol = text.charAt(pos);
            if (symbol == '$') {
                int j = pos + 1;
                while (j < text.length() && text.charAt(j) != ' ') {
                    j++;
                }
                final String variableName = text.substring(pos + 1, j);
                final String value = environment.getOrDefault(variableName, "");
                if (value == null) {
                    throw new ParseException("variable {" + variableName + "} is not in environment");
                }
                stringBuilder.append(value);
                pos = j;
            } else {
                stringBuilder.append(symbol);
                pos++;
            }
        }
        return stringBuilder.toString();
    }

    private static class Piece {
        private final PieceType pieceType;
        private final String text;

        private Piece(PieceType pieceType, String text) {
            this.pieceType = pieceType;
            this.text = text;
        }

        public PieceType getPieceType() {
            return pieceType;
        }

        public String getText() {
            return text;
        }
    }

}
