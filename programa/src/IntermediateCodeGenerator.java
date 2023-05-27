package src;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IntermediateCodeGenerator {
    private StringBuilder code;
    private int tempCount = 0;
    Map<String, String> dataTypes = new HashMap<String, String>();
    private String funcionActual = "";
    private int forCount = 0;
    private int doWhileCount = 0;
    private int whileCount = 0;
    private int ifCount = 0;
    private int elifCount = 0;
    private int elseCount = 0;
    private boolean asignando = false;

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
            else if(childType.equals("init")){
                generateCode(child);
            }
            else if(childType.equals("declaraFuncion")){
                code.append(child.getValue()+":\n");
                funcionActual = child.getValue().toString();
                generateCode(child);
                this.forCount = 0;

            }
            else if(childType.equals("bloque")){
                System.out.println("bloque");
                generateCode(child);
            }
            else if(childType.equals("expresion")){
                System.out.println("expresion");
                generateCode(child);
            }
            else if(childType.equals("llamaFuncion")){
                System.out.println("llamaFuncion");
                System.out.println(child.toString());
                int cantParams = 0;
                if(child.getChildren().size()>1){
                    ArrayList<ASTNode> params = child.getChildren().get(1).getChildren();
                    int i = tempCount;
                    for (ASTNode param : params) {
                        code.append("  t"+tempCount+" = "+param.getValue()+"\n");
                        tempCount++;
                        cantParams++;
                    }
                    for(int j = i; j<tempCount;j++){
                        code.append("  param t"+j+"\n");
                    }
                }
                if(asignando){
                    code.append("  t"+tempCount+" = call "+child.getChildren().get(0).getValue()+", "+ cantParams+"\n");
                }
                else{
                    code.append("  call "+child.getChildren().get(0).getValue()+", "+ cantParams+"\n");
                }
                tempCount++;         
            }else if(childType.equals("declaraVar")){
                System.out.println("declaraVar");
                asignando = true;
                code.append("\n  "+dataTypes.get(child.getChildren().get(0).getValue())+" "+child.getValue()+"\n");
                if(child.getChildren().size()>1){
                    generateCode(child.getChildren().get(1));
                    code.append("  "+child.getValue()+" = t"+(tempCount-1)+"\n");
                }
                asignando = false;
            }
            else if(childType.equals("asignacion")){
                System.out.println("asignacion");
                asignando = true;
                generateCode(child);
                code.append("  "+child.getValue()+" = t"+(tempCount-1)+"\n");
                asignando = false;
            }
            else if(childType.equals("literal_int")){
                System.out.println("literal_int");
                code.append("  t"+tempCount+" = "+child.getChildren().get(0)+"\n");
                tempCount++;
            }
            else if(childType.equals("literal_float")){
                System.out.println("literal_float");
                code.append("  t"+tempCount+" = "+child.getChildren().get(0)+"\n");
                tempCount++;
            }
            else if(childType.equals("literal_char")){
                System.out.println("literal_char");
                code.append("  t"+tempCount+" = "+child.getChildren().get(0)+"\n");
                tempCount++;
            }
            else if(childType.equals("literal_string")){
                System.out.println("literal_string");
                code.append("  t"+tempCount+" = "+child.getChildren().get(0)+"\n");
                tempCount++;
            }
            else if(childType.equals("literal_bool")){
                System.out.println("literal_bool");
                code.append("  t"+tempCount+" = "+child.getChildren().get(0)+"\n");
                tempCount++;
            }
            else if(childType.equals("IDENTIFIER")){
                System.out.println("IDENTIFIER");
                code.append("  t"+tempCount+" = "+child.getChildren().get(0).getValue()+"\n");
                tempCount++;
            }
            else if(childType.equals("expresionBinaria")){
                System.out.println("expresionBinaria");
                System.out.println("]]] "+child.getChildren().get(2).getValue());
                ASTNode temp =  new ASTNode("");

                temp.addChild((ASTNode)child.getChildren().get(2).getValue());
                generateCode(temp);
                int i = tempCount-1;
                temp =  new ASTNode("");

                temp.addChild((ASTNode)child.getChildren().get(3).getValue());
                generateCode(temp);
                int j = tempCount;
                code.append("  t"+tempCount+" = t"+(i)+" "+child.getChildren().get(1).getValue()+" t"+(tempCount-1)+"\n");
                tempCount++;
                System.out.println("====================================");
            }
            else if(childType.equals("operadorUnario")){
                ArrayList<ASTNode> hijosOpUnario = child.getChildren();
                if(hijosOpUnario.get(0).getValue().equals("IDENTIFIER")){
                    code.append("  t"+tempCount+" = "+hijosOpUnario.get(1).getValue()+hijosOpUnario.get(2).getValue()+"\n");
                }
                else{
                    code.append("  t"+tempCount+" = "+hijosOpUnario.get(1).getValue()+((ASTNode)hijosOpUnario.get(2).getValue()).getChildren().get(0)+"\n");
                }
                tempCount++;
            }
            else if(childType.equals("estructuraControl")){
                ArrayList<ASTNode> hijosEstructuraControl = child.getChildren();
                if(hijosEstructuraControl.get(0).getValue().equals("forStm")){
                    System.out.println("_"+funcionActual+"_for"+forCount+"_init:");
                    code.append("\n_"+funcionActual+"_for"+forCount+"_init:\n");
                    ASTNode temp =  new ASTNode("");
                    temp.addChild((ASTNode)hijosEstructuraControl.get(1).getValue());
                    generateCode(temp);

                    System.out.println("_"+funcionActual+"_for"+forCount+"_eval:");
                    code.append("\n_"+funcionActual+"_for"+forCount+"_eval:\n");
                    temp =  new ASTNode("");
                    temp.addChild((ASTNode)hijosEstructuraControl.get(2).getValue());
                    generateCode(temp);

                    code.append("  if t"+(tempCount-1)+" goto _"+funcionActual+"_for"+forCount+"_body\n");
                    code.append("  goto _"+funcionActual+"_for"+forCount+"_end\n");
                    System.out.println("_"+funcionActual+"_for"+forCount+"_update:");
                    code.append("\n_"+funcionActual+"_for"+forCount+"_update:\n");
                    temp =  new ASTNode("");
                    temp.addChild((ASTNode)hijosEstructuraControl.get(3).getValue());
                    generateCode(temp);
                    code.append("  goto _"+funcionActual+"_for"+forCount+"_eval\n");


                    System.out.println("_"+funcionActual+"_for"+forCount+"_body:");
                    code.append("\n_"+funcionActual+"_for"+forCount+"_body:\n");
                    temp =  new ASTNode("");
                    temp.addChild((ASTNode)hijosEstructuraControl.get(4).getValue());
                    generateCode(temp);
                    code.append("  goto _"+funcionActual+"_for"+forCount+"_update\n");

                    System.out.println("_"+funcionActual+"_for"+forCount+"_end:");
                    code.append("\n_"+funcionActual+"_for"+forCount+"_end:\n");
                    forCount++;

                }
            
                else if(hijosEstructuraControl.get(0).getValue().equals("doWhileStm")){
                    System.out.println("_"+funcionActual+"_doWhile"+doWhileCount+"_body:");
                    code.append("\n_"+funcionActual+"_doWhile"+doWhileCount+"_body:\n");
                    ASTNode temp =  new ASTNode("");
                    temp.addChild((ASTNode)hijosEstructuraControl.get(1).getValue());
                    generateCode(temp);

                    System.out.println("_"+funcionActual+"_doWhile"+doWhileCount+"_eval:");
                    code.append("\n_"+funcionActual+"_doWhile"+doWhileCount+"_eval:\n");
                    temp =  new ASTNode("");
                    temp.addChild((ASTNode)hijosEstructuraControl.get(2).getValue());
                    generateCode(temp);
                    code.append("  if t"+(tempCount-1)+" goto _"+funcionActual+"_doWhile"+doWhileCount+"_body\n");
                    code.append("  goto _"+funcionActual+"_doWhile"+doWhileCount+"_end\n");

                    System.out.println("_"+funcionActual+"_doWhile"+doWhileCount+"_end:");
                    code.append("\n_"+funcionActual+"_doWhile"+doWhileCount+"_end:\n");
                    doWhileCount++;
                }

                else if(hijosEstructuraControl.get(0).getValue().equals("ifStm")){
                    ifCount++;
                    int ifActual = ifCount;
                    System.out.println("_"+funcionActual+"_if"+ifActual+"_eval:");
                    code.append("\n_"+funcionActual+"_if"+ifActual+"_eval:\n");
                    ASTNode temp =  new ASTNode("");
                    temp.addChild((ASTNode)hijosEstructuraControl.get(1).getValue());
                    generateCode(temp);
                    code.append("  if t"+(tempCount-1)+" goto _"+funcionActual+"_if"+ifActual+"_body\n");
                    code.append("  goto _"+funcionActual+"_if"+ifActual+"_else\n");

                    System.out.println("_"+funcionActual+"_if"+ifActual+"_body:");
                    code.append("\n_"+funcionActual+"_if"+ifActual+"_body:\n");
                    //temp =  new ASTNode("");
                    //temp.addChild((ASTNode)hijosEstructuraControl.get(2).getValue());
                    generateCode((ASTNode)hijosEstructuraControl.get(2).getValue());
                    code.append("  goto _"+funcionActual+"_if"+ifActual+"_end\n");

                    System.out.println("_"+funcionActual+"_if"+ifActual+"_else:");
                    code.append("\n_"+funcionActual+"_if"+ifActual+"_else:\n");
                    if(hijosEstructuraControl.size() == 4){
                        temp =  new ASTNode("");
                        temp.addChild((ASTNode)hijosEstructuraControl.get(3).getValue());
                        generateCode(temp);
                    }
                    code.append("  goto _"+funcionActual+"_if"+ifActual+"_end\n");

                    System.out.println("_"+funcionActual+"_if"+ifActual+"_end:");
                    code.append("\n_"+funcionActual+"_if"+ifActual+"_end:\n");
                    
                }
                else if(hijosEstructuraControl.get(0).getValue().equals("elifStm")){
                    elifCount++;
                    int elifActual = elifCount;
                    System.out.println("_"+funcionActual+"_elif"+elifActual+"_eval:");
                    code.append("\n_"+funcionActual+"_elif"+elifActual+"_eval:\n");
                    ASTNode temp =  new ASTNode("");
                    temp.addChild((ASTNode)hijosEstructuraControl.get(1).getValue());
                    generateCode(temp);
                    code.append("  if t"+(tempCount-1)+" goto _"+funcionActual+"_elif"+elifActual+"_body\n");
                    code.append("  goto _"+funcionActual+"_elif"+elifActual+"_else\n");

                    System.out.println("_"+funcionActual+"_elif"+elifActual+"_body:");
                    code.append("\n_"+funcionActual+"_elif"+elifActual+"_body:\n");
                    //temp =  new ASTNode("");
                    //temp.addChild((ASTNode)hijosEstructuraControl.get(2).getValue());
                    generateCode((ASTNode)hijosEstructuraControl.get(2).getValue());
                    code.append("  goto _"+funcionActual+"_elif"+elifActual+"_end\n");

                    System.out.println("_"+funcionActual+"_elif"+elifActual+"_else:");
                    code.append("\n_"+funcionActual+"_elif"+elifActual+"_else:\n");
                    if(hijosEstructuraControl.size() == 4){
                        temp =  new ASTNode("");
                        temp.addChild((ASTNode)hijosEstructuraControl.get(3).getValue());
                        generateCode(temp);
                    }    
                    code.append("  goto _"+funcionActual+"_elif"+elifActual+"_end\n");

                    System.out.println("_"+funcionActual+"_elif"+elifActual+"_end:");
                    code.append("\n_"+funcionActual+"_elif"+elifActual+"_end:\n");
                    
                }
                else if(hijosEstructuraControl.get(0).getValue().equals("returnStm")){
                    ASTNode temp =  new ASTNode("");
                    temp.addChild((ASTNode)hijosEstructuraControl.get(1).getValue());
                    generateCode(temp);
                    code.append("  return t"+(tempCount-1)+"\n");
                }
                
            }
            
            
        }
        return code.toString();
    }


    public String auxGenerator(ASTNode noode){
        IntermediateCodeGenerator aux = new IntermediateCodeGenerator();


        return aux.generateCode(noode);
    }
}
