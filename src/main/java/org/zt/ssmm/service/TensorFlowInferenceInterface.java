
package org.zt.ssmm.service;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
//import java.io.PrintStream;
//import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
//import java.util.List;

import java.util.List;

import javax.imageio.ImageIO;

import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.Output;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;
import org.tensorflow.types.UInt8;

/** Sample use of the TensorFlow Java API to label images using a pre-trained model. */
public class TensorFlowInferenceInterface {
   

  public static Object main(String imageFile) {
//	  String modelDir = "/Users/xiongjunjie/Documents/code_doc/python/transfer_learning/model";  
//	  byte[] graphDef = readAllBytesOrExit(Paths.get(modelDir, "tensorflow_inception_graph.pb"));
	 
	  String modelDir = "/Users/xiongjunjie/Documents/github/personal/src/main/java/org/zt/ssmm/controller";  
	  byte[] graphDef = readAllBytesOrExit(Paths.get(modelDir, "mnist-tf1.0.1.pb"));  

	  List<String> labels =
		        readAllLinesOrExit(Paths.get(modelDir, "imagenet_comp_graph_label_strings.txt"));
	    byte[] imageBytes = readAllBytesOrExit(Paths.get(imageFile));
//	    Graph g = new Graph();  
//	    g.importGraphDef(graphDef);  
//	    Session s = new Session(g);  
//	    Tensor<Float> image = constructAndExecuteGraphToNormalizeImage(imageBytes);
//	    float[] res = executeInceptionGraph(graphDef, image);
	  
//      }
//	    long[] rshape = result.shape();  
//	    int nlabels = (int) rshape[1];  
//	    int batchSize = (int) rshape[0];  
//	    float[][] logits = result.copyTo(new float[batchSize][nlabels]); 
	    
	      try (Tensor<Float> image = (Tensor<Float>) Tensor.create(imageBytes);) {
	    	  Graph g = new Graph();  
	    	  g.importGraphDef(graphDef);  
	    	  Session s = new Session(g);  
	    	   Tensor<Float> x1=constructAndExecuteGraphToNormalizeImage(imageBytes);
	    	  Tensor x2 = Tensor.create(1);
	    	  
		      Tensor<Float> result = s.runner().feed("input", getData("/Users/xiongjunjie/Documents/github/personal/src/main/java/org/zt/ssmm/controller/1.jpg")).feed("keep_prob_placeholder", x2).fetch("output").run().get(0).expect(Float.class);  
		      long[] rshape = result.shape();  
		      int nlabels = (int) rshape[1];  
		      int batchSize = (int) rshape[0];  
		      float[][] logits = (float[][]) result.copyTo(new float[batchSize][nlabels]);  
//		      int batchSize = (int) rshape[0];  
//		      float[][] logits = (float[][]) result.copyTo(new float[batchSize][nlabels]); 
//		      float[] res=  result.copyTo(new float[1][nlabels])[0];
//		        	  int bestLabelIdx = maxIndex(labelProbabilities);
//	        System.out.println(
//	            String.format("BEST MATCH: %s (%.2f%% likely)",
//	                labels.get(bestLabelIdx),
//	                labelProbabilities[bestLabelIdx] * 100f));
	        return logits;
	      }
	    
	    
//	    try (Tensor<Float> image = constructAndExecuteGraphToNormalizeImage(imageBytes);
//	    		Graph g = new Graph()) {
//		      g.importGraphDef(graphDef);
//		      try (Session s = new Session(g);
//		          Tensor<Float> result =
//		              s.runner().feed("DecodeJpeg/contents", image).fetch("pool_3/_reshape").run().get(0).expect(Float.class)) {
//		        		  final long[] rshape = result.shape();
//		        if (result.numDimensions() != 2 || rshape[0] != 1) {
//		          throw new RuntimeException(
//		              String.format(
//		                  "Expected model to produce a [1 N] shaped tensor where N is the number of labels, instead it produced one with shape %s",
//		                  Arrays.toString(rshape)));
//		        }
//		        int nlabels = (int) rshape[1];
//		        float[] res=  result.copyTo(new float[1][nlabels])[0];
//		        int bestLabelIdx = maxIndex(res)>1001?1000:maxIndex(res);
//		      System.out.println(
//		          String.format("BEST MATCH: %s (%.2f%% likely)",
//		              labels.get(bestLabelIdx),
//		              res[bestLabelIdx] * 100f));
//		      return labels.get(bestLabelIdx);		  
//		      }
//		    }
	    
			}
//	        float[] pixelArray = bitmapToFloatArray(bitmap);

  
  public static Tensor<?> getData(String path){
	  int [] pixels = null;
	  try{
          BufferedImage bimg = ImageIO.read(new File(path));
    	  Raster raster = bimg.getData();
          System.out.println("");
          int [] temp = new int[raster.getWidth()*raster.getHeight()*raster.getNumBands()];
          //方式二：通过getPixels()方式获得像素矩阵
          //此方式为沿Width方向扫描
           pixels  = raster.getPixels(0,0,raster.getWidth(),raster.getHeight(),temp);
          for (int i=0;i<pixels.length;) {
              i+=3;
          }
         
      }catch (IOException e){
          e.printStackTrace();
      }
      return  pixels;
  }
  
  private static Tensor<Float> constructAndExecuteGraphToNormalizeImage(byte[] imageBytes) {
	    try (Graph g = new Graph()) {
	      GraphBuilder b = new GraphBuilder(g);
	      // Some constants specific to the pre-trained model at:
	      // https://storage.googleapis.com/download.tensorflow.org/models/inception5h.zip
	      //
	      // - The model was trained with images scaled to 224x224 pixels.
	      // - The colors, represented as R, G, B in 1-byte each were converted to
	      //   float using (value - Mean)/Scale.
	      final int H = 224;
	      final int W = 224;
	      final float mean = 117f;
	      final float scale = 1f;

	      // Since the graph is being constructed once per execution here, we can use a constant for the
	      // input image. If the graph were to be re-used for multiple input images, a placeholder would
	      // have been more appropriate.
	      final Output<String> input = b.constant("input", imageBytes);
	      final Output<Float> output =
	          b.div(
	              b.sub(
	                  b.resizeBilinear(
	                      b.expandDims(
	                          b.cast(b.decodeJpeg(input, 3), Float.class),
	                          b.constant("make_batch", 0)),
	                      b.constant("size", new int[] {H, W})),
	                  b.constant("mean", mean)),
	              b.constant("scale", scale));
	      try (Session s = new Session(g)) {
	        return s.runner().fetch(output.op().name()).run().get(0).expect(Float.class);
	      }
	    }
	  }

	  private static float[] executeInceptionGraph(byte[] graphDef, Tensor<Float> image) {
	    try (Graph g = new Graph()) {
	      g.importGraphDef(graphDef);
	      try (Session s = new Session(g);
	          Tensor<Float> result =
	        	  s.runner().feed("DecodeJpeg/contents", image).fetch("pool_3/_reshape").run().get(0).expect(Float.class)) {	  
//	              s.runner().feed("input", image).fetch("output").run().get(0).expect(Float.class)) {
	        final long[] rshape = result.shape();
	        if (result.numDimensions() != 2 || rshape[0] != 1) {
	          throw new RuntimeException(
	              String.format(
	                  "Expected model to produce a [1 N] shaped tensor where N is the number of labels, instead it produced one with shape %s",
	                  Arrays.toString(rshape)));
	        }
	        int nlabels = (int) rshape[1];
	        return result.copyTo(new float[1][nlabels])[0];
	      }
	    }
	  }

	  private static int maxIndex(float[] probabilities) {
	    int best = 0;
	    for (int i = 1; i < probabilities.length; ++i) {
	      if (probabilities[i] > probabilities[best]) {
	        best = i;
	      }
	    }
	    return best;
	  }

	  private static byte[] readAllBytesOrExit(Path path) {
	    try {
	      return Files.readAllBytes(path);
	    } catch (IOException e) {
	      System.err.println("Failed to read [" + path + "]: " + e.getMessage());
	      System.exit(1);
	    }
	    return null;
	  }

	  private static List<String> readAllLinesOrExit(Path path) {
	    try {
	      return Files.readAllLines(path, Charset.forName("UTF-8"));
	    } catch (IOException e) {
	      System.err.println("Failed to read [" + path + "]: " + e.getMessage());
	      System.exit(0);
	    }
	    return null;
	  }

	  // In the fullness of time, equivalents of the methods of this class should be auto-generated from
	  // the OpDefs linked into libtensorflow_jni.so. That would match what is done in other languages
	  // like Python, C++ and Go.
	  static class GraphBuilder {
	    GraphBuilder(Graph g) {
	      this.g = g;
	    }

	    Output<Float> div(Output<Float> x, Output<Float> y) {
	      return binaryOp("Div", x, y);
	    }

	    <T> Output<T> sub(Output<T> x, Output<T> y) {
	      return binaryOp("Sub", x, y);
	    }

	    <T> Output<Float> resizeBilinear(Output<T> images, Output<Integer> size) {
	      return binaryOp3("ResizeBilinear", images, size);
	    }

	    <T> Output<T> expandDims(Output<T> input, Output<Integer> dim) {
	      return binaryOp3("ExpandDims", input, dim);
	    }

	    <T, U> Output<U> cast(Output<T> value, Class<U> type) {
	      DataType dtype = DataType.fromClass(type);
	      return g.opBuilder("Cast", "Cast")
	          .addInput(value)
	          .setAttr("DstT", dtype)
	          .build()
	          .<U>output(0);
	    }

	    Output<UInt8> decodeJpeg(Output<String> contents, long channels) {
	      return g.opBuilder("DecodeJpeg", "DecodeJpeg")
	          .addInput(contents)
	          .setAttr("channels", channels)
	          .build()
	          .<UInt8>output(0);
	    }

	    <T> Output<T> constant(String name, Object value, Class<T> type) {
	      try (Tensor<T> t = Tensor.<T>create(value, type)) {
	        return g.opBuilder("Const", name)
	            .setAttr("dtype", DataType.fromClass(type))
	            .setAttr("value", t)
	            .build()
	            .<T>output(0);
	      }
	    }
	    Output<String> constant(String name, byte[] value) {
	      return this.constant(name, value, String.class);
	    }

	    Output<Integer> constant(String name, int value) {
	      return this.constant(name, value, Integer.class);
	    }

	    Output<Integer> constant(String name, int[] value) {
	      return this.constant(name, value, Integer.class);
	    }

	    Output<Float> constant(String name, float value) {
	      return this.constant(name, value, Float.class);
	    }

	    private <T> Output<T> binaryOp(String type, Output<T> in1, Output<T> in2) {
	      return g.opBuilder(type, type).addInput(in1).addInput(in2).build().<T>output(0);
	    }

	    private <T, U, V> Output<T> binaryOp3(String type, Output<U> in1, Output<V> in2) {
	      return g.opBuilder(type, type).addInput(in1).addInput(in2).build().<T>output(0);
	    }
	    private Graph g;
	  }
	}
  

