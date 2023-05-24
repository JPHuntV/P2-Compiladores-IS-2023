package src;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IntermediateCodeGenerator {
    private StringBuilder code;
    private int tempCount = 0;
    Map<String, String> dataTypes = new HashMap<String, String>();

    public IntermediateCodeGenerator() {
        code = new StringBuilder();
        dataTypes.put("int", "dataInt");
        dataTypes.put("float", "dataFloat");
        dataTypes.put("char", "dataChar");
        dataTypes.put("String", "dataString");
        dataTypes.put("bool", "dataBool");


    }

    public String generateCode(ASTNode root){
        ArrayList<ASTNode> children = root.getChildren();
        //System.out.println(children.toString());
        for (ASTNode child : children) {
            System.out.println("===== #="+child.getType());
            String childType = child.getType();
            if(childType.equals("programa")){
                generateCode(child);
            }
            else if(childType.equals("declaraFuncion")){
                code.append(child.getValue()+":\n");
                generateCode(child);
            }
            else if(childType.equals("bloque")){
                System.out.println("bloque");
                generateCode(child);
            }
            else if(childType.equals("expresion")){
                generateCode(child);
            }
            else if(childType.equals("llamaFuncion")){
                System.out.println("llamaFuncion");
                ArrayList<ASTNode> params = child.getChildren().get(1).getChildren();
                for (ASTNode param : params) {
                    code.append("t"+tempCount+" = "+param.getValue()+"\n");
                    tempCount++;

                }
                
            }else if(childType.equals("declaraVar")){
                System.out.println("declaraVar");
                code.append("  "+dataTypes.get(child.getChildren().get(0).getValue())+" "+child.getValue()+"\n");
                if(child.getChildren().size()>1){
                    generateCode(child.getChildren().get(1));
                    //code.append("  "+child.getValue()+" = "+res+"\n");
                }
            }else if(childType.equals("literal_int")){
                System.out.println("literal_int");
                code.append("t"+tempCount+" = "+child.getChildren().get(0)+"\n");
                tempCount++;
            }
            else if(childType.equals("expresionBinaria")){
                System.out.println("expresionBinaria");
                System.out.println("]]] "+child.getChildren().get(2).getValue());
                ASTNode temp =  new ASTNode("");
                temp.addChild((ASTNode)child.getChildren().get(2).getValue());
                generateCode(temp);
                temp =  new ASTNode("");
                temp.addChild((ASTNode)child.getChildren().get(3).getValue());
                generateCode(temp);
                code.append("t"+tempCount+" = t"+(tempCount-2)+" "+child.getChildren().get(1).getValue()+" t"+(tempCount-1)+"\n");
                tempCount++;
                System.out.println("====================================");
            }
        }
        return code.toString();
    }


    public String auxGenerator(ASTNode noode){
        IntermediateCodeGenerator aux = new IntermediateCodeGenerator();


        return aux.generateCode(noode);
    }
}
