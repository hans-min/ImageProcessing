import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import javax.imageio.ImageIO;
public class ImageProcessing {
	public static void main(String[] args) {
		int[][] imageData = imgToTwoD("./img/orca.jpg");  // choose this image of my favorite character, or whatever you want!
		viewImageData(imageData);
    	int [][]negative = negativeColor(imageData);
  		twoDToImage(negative, "./frostnova.jpg"); // this is the new image after filter, you can name it however you like
		  // int[][] allFilters = stretchHorizontally(shrinkVertically(colorFilter(negativeColor(trimBorders(invertImage(imageData), 20)), 200, 20, 40)));

		// Painting with pixels

		//int[][] canvas = new int [500][500];
        //int[] rgba = {255, 255, 0, 255};
        //int [][]randomImg =generateRectangles(canvas, 1000);
    	//twoDToImage(randomImg, "./random_img.jpg");
	}

	// Image Processing Methods

	// 1. Trim the image (choose a length and this method will return a new trimmed image of that length of all 4 sizes)
	public static int[][] trimBorders(int[][] imageTwoD, int pixelCount) {
		// Example Method
		if (imageTwoD.length > pixelCount * 2 && imageTwoD[0].length > pixelCount * 2) {
			int[][] trimmedImg = new int[imageTwoD.length - pixelCount * 2][imageTwoD[0].length - pixelCount * 2];
			for (int i = 0; i < trimmedImg.length; i++) {
				for (int j = 0; j < trimmedImg[i].length; j++) {
					trimmedImg[i][j] = imageTwoD[i + pixelCount][j + pixelCount];
				}
			}
			return trimmedImg;
		} else {
			System.out.println("Cannot trim that many pixels from the given image.");
			return imageTwoD;
		}
	}

	// 2. Return the negative version of the image
	public static int[][] negativeColor(int[][] imageTwoD) {

    int [][]s = new int [imageTwoD.length][imageTwoD[0].length];
    	for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s[i].length; j++) {
        int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
        rgba[0] = 255 -rgba[0];
        rgba[1] = 255 -rgba[1];
        rgba[2] = 255 -rgba[2];
        s[i][j] = getColorIntValFromRGBA(rgba);
      }
    }
		return s;
	}

	// 3. Return an image where the x-axis length is doubled (Stretched Horizontally)
	public static int[][] stretchHorizontally(int[][] imageTwoD) {
		int [][]s = new int [imageTwoD.length][imageTwoD[0].length*2];
    int it=0;
    for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < imageTwoD[i].length; j++) {
        it = j*2;
        s[i][it]=imageTwoD[i][j];
        s[i][it+1]=imageTwoD[i][j];
		
	    }
    }return s;
  }

  	// 4. Return an image where the y-axis length is halved ( Shrink Vertically)
	public static int[][] shrinkVertically(int[][] imageTwoD) {
		int [][]s = new int [imageTwoD.length/2][imageTwoD[0].length];
    int it=0;
    for (int i = 0; i < s[0].length; i++) {
			for (int j = 0; j < imageTwoD.length-1; j+=2) {
        s[j/2][i]=imageTwoD[j][i];
      }
    }
		return s;
	}

	// 5. Return an inverted image
	public static int[][] invertImage(int[][] imageTwoD) {
		int [][]s = new int [imageTwoD.length][imageTwoD[0].length];
    	for (int i = 0; i < s.length; i++) {
			for (int j = 0; j < s[i].length; j++) {
        s[i][j] = imageTwoD[s.length-1-i][s[0].length-1-j];
       }
      }
		return s;
	}

	// 6. You can change the color tone of the image with this method (Change the RGB)
	public static int[][] colorFilter(int[][] imageTwoD, int redChangeValue, int greenChangeValue, int blueChangeValue) {
		int [][]s = new int [imageTwoD.length][imageTwoD[0].length];
    	for (int i = 0; i < s.length; i++) {
			  for (int j = 0; j < s[i].length; j++) {
          int[] rgba = getRGBAFromPixel(imageTwoD[i][j]);
          int r = rgba[0] + redChangeValue;
          int g = rgba[1] + greenChangeValue;
          int b = rgba[2] + blueChangeValue;
          if(r>255) {
			r= 255;
			} else if(r<0) {
					r = 0;
			}
			if(g>255) {
				g = 255;
			} else if(g<0) {
				g = 0;
			}
			if(b>255) {
				b = 255;
			} else if(b<0) {
				b = 0;
			}
          rgba[0] = r;
          rgba[1] = g;
          rgba[2] =b ;
          s[i][j]=getColorIntValFromRGBA(rgba);
        }
      }
		return s;
	}


	// Painting Methods
	public static int[][] paintRandomImage(int[][] canvas) {
		Random rand = new Random();
    for (int i = 0; i < canvas.length; i++) {
			  for (int j = 0; j < canvas[i].length; j++) {
          int []rgba = new int [4];
          rgba[0]=rand.nextInt(256);
          rgba[1]=rand.nextInt(256);
          rgba[2]=rand.nextInt(256);
          rgba[3]=255;
          canvas[i][j]=getColorIntValFromRGBA(rgba);
        }
    }
		return canvas;
	}
	public static int[][] paintRectangle(int[][] canvas, int width, int height, int rowPosition, int colPosition, int color) {
		for (int i = 0; i < canvas.length; i++) {
      if(i>=rowPosition && i<=rowPosition + width) {
			  for (int j = 0; j < canvas[0].length; j++) {
           if(j>=colPosition && j<=colPosition + height) {
           canvas[i][j] = color;
        }}}
    }
		return canvas;
	}
	public static int[][] generateRectangles(int[][] canvas, int numRectangles) {
		Random rand = new Random();
    for (int i = 0; i < numRectangles; i++) {
          int w = rand.nextInt(canvas[0].length);
          int h= rand.nextInt(canvas.length);
          int r =  rand.nextInt(canvas.length - h);
          int c = rand.nextInt(canvas[0].length - w);
          int []rgba = {rand.nextInt(256),rand.nextInt(256),rand.nextInt(256),255};
          int color = getColorIntValFromRGBA(rgba);
          canvas = paintRectangle(canvas, w, h, r, c, color);
    }
		return canvas;
	}

	// Utility Methods

	// Turn the image into a 2D array of pixels so the computer can understand and alter the image
	public static int[][] imgToTwoD(String inputFileOrLink) {
		try {
			BufferedImage image = null;
			if (inputFileOrLink.substring(0, 4).toLowerCase().equals("http")) {
				URL imageUrl = new URL(inputFileOrLink);
				image = ImageIO.read(imageUrl);
				if (image == null) {
					System.out.println("Failed to get image from provided URL.");
				}
			} else {
				image = ImageIO.read(new File(inputFileOrLink));
			}
			int imgRows = image.getHeight();
			int imgCols = image.getWidth();
			int[][] pixelData = new int[imgRows][imgCols];
			for (int i = 0; i < imgRows; i++) {
				for (int j = 0; j < imgCols; j++) {
					pixelData[i][j] = image.getRGB(j, i);
				}
			}
			return pixelData;
		} catch (Exception e) {
			System.out.println("Failed to load image: " + e.getLocalizedMessage());
			return null;
		}
	}

	//Turn the 2D array of pixels into an image so the human can understand and enjoy his/her art
	public static void twoDToImage(int[][] imgData, String fileName) {
		try {
			int imgRows = imgData.length;
			int imgCols = imgData[0].length;
			BufferedImage result = new BufferedImage(imgCols, imgRows, BufferedImage.TYPE_INT_RGB);
			for (int i = 0; i < imgRows; i++) {
				for (int j = 0; j < imgCols; j++) {
					result.setRGB(j, i, imgData[i][j]);
				}
			}
			File output = new File(fileName);
			ImageIO.write(result, "jpg", output);
		} catch (Exception e) {
			System.out.println("Failed to save image: " + e.getLocalizedMessage());
		}
	}

	// get an array of 4 colors (Red, Green, Blue) from the pixel
	public static int[] getRGBAFromPixel(int pixelColorValue) {
		Color pixelColor = new Color(pixelColorValue);
		return new int[] { pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue(), pixelColor.getAlpha() };
	}
	public static int getColorIntValFromRGBA(int[] colorData) {
		if (colorData.length == 4) {
			Color color = new Color(colorData[0], colorData[1], colorData[2], colorData[3]);
			return color.getRGB();
		} else {
			System.out.println("Incorrect number of elements in RGBA array.");
			return -1;
		}
	}

	//Print out the RGBA from the top left (3x3) corner of the image
	public static void viewImageData(int[][] imageTwoD) {
		if (imageTwoD.length > 3 && imageTwoD[0].length > 3) {
			int[][] rawPixels = new int[3][3];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					rawPixels[i][j] = imageTwoD[i][j];
				}
			}
			System.out.println("Raw pixel data from the top left corner.");
			System.out.print(Arrays.deepToString(rawPixels).replace("],", "],\n") + "\n");
			int[][][] rgbPixels = new int[3][3][4];
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					rgbPixels[i][j] = getRGBAFromPixel(imageTwoD[i][j]);
				}
			}
			System.out.println();
			System.out.println("Extracted RGBA pixel data from top the left corner.");
			for (int[][] row : rgbPixels) {
				System.out.print(Arrays.deepToString(row) + System.lineSeparator());
			}
		} else {
			System.out.println("The image is not large enough to extract 9 pixels from the top left corner");
		}
	}
}