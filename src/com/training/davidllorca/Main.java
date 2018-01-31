package com.training.davidllorca;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static String node = "[\\(\\[\\{][^\\(\\[\\{\\)\\]\\}].+[\\)\\]\\}]\\d*";
    static String atom = "[A-Z]{1}[a-z]?\\d*";
    static String coeficient = "\\d*$";
    static String beginNode = "[\\(\\[\\{]";
    static String endNode = "[\\)\\]\\}]\\d*$";

    public static void main(String[] args) {
	// write your code here
        String fremySalt = "K4[ON(SO3)2Mg(SO3)2]2H3";

        String test = "(Mg23)";
        String test2 = "[Mg23]H(N2O)";
//        getAtoms(fremySalt);
//        System.out.println(captureNodes(fremySalt));
//        System.out.println(captureNodes(test));
//        System.out.println(captureNodes(test2));

        List<String> moleculeList = new ArrayList<>();
        String input = fremySalt;
        while(hasNodes(input)) {

            List<String> nodes = capture(node, input);
            List<String> atoms = capture(atom, input);
            String[] list = input.split(node);

            for (int i = 0; i < list.length; i++) {
                moleculeList.add(list[i]);
                if(input.contains(list[i])){
                    input = input.replaceFirst(list[i], "");
                }
            }

            if(input.matches(node)){
                int n = Integer.parseInt(capture(coeficient, input).get(0));
                System.out.println("Coeficient:" + n);
                String content = input.replaceFirst(beginNode,"");
                content = content.replaceAll(endNode, "");
                System.out.println("Content:" + content);
                input = content; // first loop
                for (int i = 1; i < n; i++) {
                    input = input.concat(content);
                }
                System.out.println("Input end while:" + input);
                System.out.println("###############################################");
            }

        }

//        moleculeList = capture(atom, "Mg22OH43");
//
//        for(String mol: moleculeList){
//            System.out.println(mol);
//        }
//
//        StringBuilder out = new StringBuilder();
//
//        String[] list = fremySalt.split(node);
//        List<String> nodes = captureNodes(fremySalt);
//
//        for (int i = 0; i < list.length; i++) {
////            System.out.println(list[i]);/
//            out.append(list[i]);
//        }
//
//
//        String biggerNode = nodes.get(0);
//        String coef = biggerNode.split("[\\(\\[\\{].+[\\)\\]\\}]")[1];
//        int lastIndex = biggerNode.lastIndexOf(coef); // remove tail
//        biggerNode = biggerNode.subSequence(0,lastIndex-1).toString();
//        biggerNode = biggerNode.replaceFirst("[\\(\\[\\{]",""); //remove head
//
//        System.out.println(biggerNode);
//
//        nodes = Collections.nCopies(Integer.parseInt(coef),biggerNode);

        ///////////////////////

    }

    private static boolean hasNodes(String input) {
        Pattern pattern = Pattern.compile(node);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public static List<String> capture(String regex, String input){
        Pattern pattern = Pattern.compile(regex);
        Matcher mtch = pattern.matcher(input);
        List<String> ips = new ArrayList<String>();
        System.out.println("Capture: " + input);
        while(mtch.find()){
            ips.add(mtch.group());
            System.out.println(mtch.group());
        }

        return ips;
    }


    static Pattern pattern = Pattern.compile(node);


    public static List<String> captureNodes(String largeText){
        Matcher mtch = pattern.matcher(largeText);
        List<String> ips = new ArrayList<String>();
        while(mtch.find()){
            ips.add(mtch.group());
        }

        return ips;
    }

    public static Map<String,Integer> getAtoms(String formula) {
        // Your code here!
        HashMap<String, Integer> map = new HashMap<>();

        List<String> out = new ArrayList<>();
        List openBrackets = Arrays.asList('[', '(', '{');
        List closeBrackets = Arrays.asList(']', ')', '}');
        List openPositions = new ArrayList();
        List closePositions = new ArrayList();

        int i = 0;
        int length = formula.length();
        while(i < length) {
            char c = formula.charAt(i);

            if(Character.isUpperCase(c)){

                if(Character.isDigit(formula.charAt(i+1))){
                    int n = Integer.parseInt(String.valueOf(formula.charAt(i+1)));
                    out.addAll(Collections.nCopies(n, String.valueOf(c)));
                    i += 2;
                    continue;
                }

                if(Character.isLowerCase(formula.charAt(i+1))){
                    String molStr = new String(new char[]{c,formula.charAt(i+1)});
                    if(Character.isDigit(formula.charAt(i+2))){
                        int n = Integer.parseInt(String.valueOf(formula.charAt(i+2)));
                        out.addAll(Collections.nCopies(n, molStr));
                        i += 3;
                    }
                }


            }

            if(openBrackets.contains(c)){
                openPositions.add(i);
                continue;
            }

            if( closeBrackets.contains(c)){
                closePositions.add(i);
            }

            if(Character.isUpperCase(c)){

                if(Character.isDigit(formula.charAt(i+1))){

                }

            }
        }
        System.out.print(openPositions);
        System.out.print(closePositions);

        if(openPositions.size() != closePositions.size()){
            throw new IllegalArgumentException();
        }


        return map;
    }

    public static class Node{
        int posStart;
        int posFinish;

    }

}
