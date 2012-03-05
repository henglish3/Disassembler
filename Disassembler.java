public class Disassembler 
{
/Users/harrisonenglish/Desktop/CS2110/HW6/Disassembler.java
    public static void main(String[] args) {

    

    //Holds instructions to test
    int[] instructions = {0x0000,0x01FF,0xF025,0xFFFF};
    for (int i:instructions) {
        //holds the instruction
        int instr;
        //holds the first three bits after the opcode
        int f3Bits;
        //holsd the second three bits after the opcode
        int s3Bits;
        
        // String to hold the code disassembled into a string
        String instruction="";

        //makes the instruction only the last 16 bits instead of int natural 32
        i = i&0x0000FFFF; 

        //separates just the Opcode
        instr = 0xf000 &i;
        
        //Test for each instruction and adds the Call of that instruction to the
        //string
        if(i==0x0000){
        	instruction = "NOP";
        }
        else if(instr ==0x1000){
            instruction = "ADD ";
        }
        else if(instr==0x5000){
        	instruction = "AND ";
        }
        else if(instr==0x0000){
        	instruction = "BR ";
        	//Test for each part of the NZP
            int n = i & 0x0800;
        	int z = i & 0x0400;
        	int p = i & 0x0200;
            if(n == 0x0800)
            	instruction += "n";
            if(z == 0x0400)
            	instruction += "z";
            if(p == 0x0200)
            	instruction += "p";
            //Adds the offset to the string
            int offset9 = i & 0x01FF;
            instruction += " " + offset9;
        }
        else if(instr==0xC000){
        	instruction = "JMP ";
        	instruction += "R"+(i&0x01C0);
        }
        else if(instr==0x4000){
        	instruction += "JSR";
        	if((i&0x0800) == 0x0800) {
        		instruction += " "+(i&0x07FF);
        	}
        	else {
        		instruction += "R R"+(i&0x01C0);
        	}
        }
        else if(instr==0x2000){
        	instruction = "LD ";
        }
        else if(instr==0xA000){
            instruction = "LDI ";
        }
        else if(instr==0x6000){
        	instruction = "LDR ";
        }
        else if(instr==0xE000){
        	instruction = "LEA ";
        }
        else if(instr==0x9000){
        	instruction = "NOT ";
        }
        else if(instr==0x3000){
        	instruction ="ST ";
        }
        else if(instr==0xB000){
        	instruction = "STI ";
        }
        else if(instr==0x7000){
        	instruction = "STR ";
        }
        else if(i==0xF025){
        	instruction = "HALT";
        }
        else {
        	instruction = "ERROR";
        }

        //Grabs the first Register either SR or DR if it is not 
        // NOP RET JSR JSRR
        if(instr !=0x0000 || instr != 0xC000 || instr != 0x4000) {
            f3Bits = i & 0x0E00;
            f3Bits= f3Bits >> 9;

         // Does the first two Registers for ADD AND LDR STR and NOT
         if(instr==0x1000 || instr==0x5000 || instr==0x6000 || instr==0x9000 || instr==0x7000){
             instruction = instruction + "R"+f3Bits+" ";
             s3Bits = i & 0x01C0;
             s3Bits = s3Bits >> 6;
             instruction = instruction + "R"+s3Bits+" ";
          }
          // Does teh First register and the Offset for LD, LDI, RET, ST, STI
          else if(instr==0x2000 || instr==0xA000 || instr==0xC000 || instr==0x3000 || instr=0xB000){
              instruction = instruction +"R"+f3Bits+" ";
              System.out.printf("%x",i);
              int offset9 = i & 0x01FF;
              instruction = instruction + offset9;
         }
         // DOes the logic for IMM5 or the 3rd REG for ADD and AND
         if(instr==0x1000 || instr==0x5000) {
             int bit4  = i & 0x0020;
             if(bit4 == 0x0020){
                 int imm5 = i & 0x001F;
                 instruction = instruction + imm5;
             }
             else {
        	     instr = i &0x0007;
         	     instruction = instruction + "R" + instr;
             }
         }
        //Computes the Offset for LDR and STR
         else if(instr==0x6000 || instr == 0x7000) {
             int offset6 = i & 0x003F;
             instruction = instruction + offset6;
         }
        }
        System.out.println(instruction);
    }
  }
}
