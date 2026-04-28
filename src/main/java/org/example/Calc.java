package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.parseInt;

public class Calc {
    public static int run(String mathEx) {
        int answer = 0;
        String expr = parenthesis(mathEx, 0);
        List<String> tokens = new ArrayList<>(Arrays.asList(expr.split(" ")));
        answer = Integer.parseInt(priorityParse(tokens));
        return answer;
    }



    // 우선순위 구분
    private static String priorityParse(List<String> mathEx)
    {
        List<String> result = processMultiDiv(mathEx, 0);
        return processAddSub(result,0,result.getFirst());
    }

    // 곱하기 나누기
    private static List<String> processMultiDiv(List<String> mathEx, int idx)
    {
        if(mathEx.size() <= idx) return mathEx;
        String cur = mathEx.get(idx);
        if(cur.equals("*") || cur.equals("/")) {
            String num1 = mathEx.get(idx-1);
            String num2 = mathEx.get(idx+1);
            String result = cur.equals("*") ? multi(num1, num2) : div(num1, num2);

            mathEx.remove(idx-1);
            mathEx.remove(idx-1);
            mathEx.set(idx-1, result);
            return processMultiDiv(mathEx, idx-1);
        }
        return processMultiDiv(mathEx, idx+1);
    }

    // 더하기 빼기
    private static String processAddSub(List<String> mathEx, int idx, String accumulator)
    {
        if(mathEx.size() <= idx) return accumulator;

        String cur = mathEx.get(idx);
        String answer = accumulator;

        switch(cur)
        {
            case "+" -> answer = add(accumulator, mathEx.get(idx+1));
            case "-" -> answer = subtract(accumulator, mathEx.get(idx+1));
        }
        return processAddSub(mathEx,idx+1, answer);
    }

    // 처음 닫히는 괄호와 짝인 열린 괄호 찾기
    private static int findOpenParen(String expr, int startIdx)
    {
        int openIdx = -1;
        for(int i=startIdx; i<expr.length(); i++) {
            if (expr.charAt(i) == '(') openIdx = i;
            else if(expr.charAt(i) == ')') return openIdx;
        }
        return -1;
    }

    // 괄호 처리
    private static String parenthesis(String mathEx, int start)
    {
        int openIdx = findOpenParen(mathEx, start);
        if(openIdx == -1) return mathEx;

        //처음 닫히는 괄호의 index
        int closeIdx = mathEx.indexOf(')', openIdx);
        String subExpr = mathEx.substring(openIdx+1,closeIdx);

        //수식 토큰 구성
        List<String> subTokens = new ArrayList<>(Arrays.asList(subExpr.split(" ")));
        String result = priorityParse(subTokens);

        //처리한 부분 수식의 괄호를 제거하고 수식 생성
        String newExpr = mathEx.substring(0,openIdx) + result + mathEx.substring(closeIdx+1);

        return parenthesis(newExpr, start);
    }


    // 기본 산술 연산 메서드
    private static  String add(String num1, String num2)
    {
        int num3 = parseInt(num1) + parseInt(num2);
        String answer = String.valueOf(num3);
        return answer;
    }
    private static String subtract(String num1, String num2)
    {
        int num3 = parseInt(num1) - parseInt(num2);
        String answer = String.valueOf(num3);
        return answer;
    }
    private static String multi(String num1, String num2)
    {
        int num3 = parseInt(num1) * parseInt(num2);
        String answer = String.valueOf(num3);
        return answer;
    }
    private static String div(String num1, String num2)
    {
        if(num2.equals("0")) return num1;
        int num3 = parseInt(num1) / parseInt(num2);
        String answer = String.valueOf(num3);
        return answer;
    }
}
