/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.esprit.entities;

import java.util.Random;

/**
 *
 * @author Anis
 */
public class CodeGenerator {
    
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZazertyuiopmlkjhgfdsqwxcvbn0123456789";
    private static final int CODE_LENGTH = 6;

    public static String generateCode() {
        Random random = new Random();
        String code = new String();

        for (int i = 0; i < CODE_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            code += CHARACTERS.charAt(index);
        }

        return code;
    }
}

    

