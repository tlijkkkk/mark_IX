package com.practice.lc.recursion.java;

import java.util.ArrayDeque;

public class Leetcode394 {
    public record State(StringBuilder sb, int repeat) {}
    public String decodeString(String s) {
        final ArrayDeque<State> stack = new ArrayDeque<>();
        StringBuilder sb = new StringBuilder();
        StringBuilder repeat = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= 'a' && s.charAt(i) <= 'z') {
                sb.append(s.charAt(i));
            } else if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                repeat.append(s.charAt(i));
            } else if (s.charAt(i) == '[') {
                final State state = new State(sb, Integer.parseInt(repeat.toString()));
                stack.addLast(state);
                sb = new StringBuilder();
                repeat = new StringBuilder();
            } else if (s.charAt(i) == ']') {
                final State state = stack.removeLast();
                for (int j = 0; j < state.repeat; j++) {
                    state.sb.append(sb);
                }
                sb = state.sb;
            }
        }

        return sb.toString();
    }
}
