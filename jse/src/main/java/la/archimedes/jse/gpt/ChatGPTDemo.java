//package la.deep.gpt;
//
//import ai.djl.util.Utils;
//import cn.hutool.core.io.IoUtil;
//import org.tensorflow.Graph;
//import org.tensorflow.Session;
//import org.tensorflow.Tensor;
//import org.tensorflow.Tensors;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.nio.file.Paths;
//import java.util.Scanner;
//
//public class ChatGPTDemo {
//
//    public static void main(String[] args) {
//        try (Graph graph = new Graph()) {
//            // 加载 ChatGPT 模型
//            String modelPath = "path/to/your/model"; // 替换为你的模型路径
//            byte[] modelBytes = Utils.toByteArray(new BufferedInputStream(new BufferedReader()));
//            graph.importGraphDef(modelBytes);
//
//            try (Session session = new Session(graph)) {
//                // 进入对话循环
//                Scanner scanner = new Scanner(System.in);
//                String inputText;
//                while (true) {
//                    // 读取用户输入
//                    System.out.print("User: ");
//                    inputText = scanner.nextLine();
//
//                    // 检查输入是否为空
//                    if (inputText.isEmpty()) {
//                        break;
//                    }
//
//                    // 创建输入 Tensor
//                    try (Tensor<String> inputTensor = Tensors.create(inputText)) {
//                        // 进行预测
//                        Tensor<?>[] outputTensors = session.runner()
//                                .feed("input", inputTensor)
//                                .fetch("output")
//                                .run()
//                                .toArray();
//
//                        // 获取输出 Tensor
//                        Tensor<String> outputTensor = outputTensors[0].expect(String.class);
//                        String modelOutput = outputTensor.data().getString();
//                        outputTensor.close();
//
//                        // 输出模型的回答
//                        System.out.println("Model: " + modelOutput);
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
