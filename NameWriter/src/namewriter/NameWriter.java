/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package namewriter;

/**
 *
 * @author ambesht
 */
public class NameWriter {
    private static Board board = new Board();
    
    public static void main(String[] args) {
        String input = "A QUICK BROWN FOX JUMPS OVER THE LAZY DOG";
        board.drawString(input);
    }
    
}
