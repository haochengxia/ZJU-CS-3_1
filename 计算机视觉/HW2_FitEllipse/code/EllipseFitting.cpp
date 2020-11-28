/*
FileName: EllipseFitting.cpp
Author: 3170102492 夏豪诚
Date：2019-12-1

requirement： 
	调用CvBox2D cvFitEllipse2( const CvArr* points )实现椭圆拟合
*/

#include <opencv2/opencv.hpp>
#include <iostream>
using namespace std;
using namespace cv;

int main(int argc, char** argv) {
	String filename;

	if (argc == 2) {
		if (argv[1] == "--help") cout << "Usage:" << endl << "EllipseFitting.exe [your input filepath]" << endl;
		else filename = argv[1];
	}
	else {
		cout << "EllipseFitting.exe --help to see the usage." << endl;
		return 0;
	}


	// read img
	Mat gray_img = imread(filename, IMREAD_GRAYSCALE);
	Mat result = imread(filename);

	
	// set a resonable threhold
	Mat binary_img = gray_img;
	double sum = 0;
	double size = 0;
	for (int row = 0; row < gray_img.rows; row++) {
		for (int col = 0; col < gray_img.cols; col++) {
			uchar& m = gray_img.at<uchar>(row, col);
			sum += m;	
			size++;
		}
	}
	int thresh;
	thresh =(int)(sum*1.25 / size);
	cout << "threshold is set to" << thresh << endl;

	// get binary img
	for (int row = 0; row < gray_img.rows; row++) {
		for (int col = 0; col < gray_img.cols; col++) {
			uchar& m = gray_img.at<uchar>(row, col);
			uchar& dest_m = binary_img.at<uchar>(row, col);
			if (m >= thresh) {
				dest_m = 255;
			}
			else dest_m = 0;
		}
	}

	// get contours
	vector<vector<Point>> contours;
	findContours(binary_img, contours, RETR_LIST, CHAIN_APPROX_NONE);

	// fitellipse and draw on the pic
	for (int i = 0; i < contours.size(); i++)
	{
		if (contours[i].size() >= 6) 
		{
			RotatedRect box = fitEllipse(contours[i]);
			ellipse(result, box, Scalar(0, 0, 255), 1, LINE_4); 
		}
	}
	imwrite("binary.jpg", binary_img);
	imwrite("result.jpg", result);
	imshow("binary", binary_img);
	imshow("FitResult", result);
	cvWaitKey(0);
	destroyAllWindows();

	return 0;
}






