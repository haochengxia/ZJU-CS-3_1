/*
In this file, I implement two transition effect 
*/
#include "./transition.h"

void GradualChangeTransition(cv::VideoWriter writer, cv::Mat src1, cv::Mat src2) {
	int wait = TRASITION_TIME * 3000;
	cv::Mat res;
	for (int i = 0; i < wait + 1; i++) {
		if (i % 100 == 0) {
			double weight = (i*1.0) / (wait*1.0);
			cv::addWeighted(src1, 1.0 - weight, src2, weight, 1, res);
			writer.write(res);
		}
	}
}

void RollInTransition(cv::VideoWriter writer, cv::Mat src1, cv::Mat src2) {
	int wait = TRASITION_TIME * 3000;
	for (int i = 1; i < wait + 1; i++) {
		if (i % 100 == 0) {
			// weight from 3.3% ~ 100%
			double weight = (i * 1.0) / (wait * 1.0);
			cv::Mat res = src1;
			// if cols number is less then 1, we need add it to 1 to avoid the empty src image 
			if ((int)(src1.cols * weight) == 0) { 
				cv::Mat src2Part(src2, cv::Rect(0, 0, (int)(src1.cols *  weight +1), src1.rows));
				cv::Mat imageROI = res(cv::Rect(0, 0, (int)(src1.cols *  weight +1), src1.rows));
				cv::Mat mask;
				cv::cvtColor(src2Part, mask, cv::COLOR_BGR2GRAY);
				src2Part.copyTo(imageROI, mask);
				writer.write(res);
			}
			else {
				cv::Mat src2Part(src2, cv::Rect(0, 0, (int)(src1.cols * weight), src1.rows));
				cv::Mat imageROI = res(cv::Rect(0, 0, (int)(src1.cols * weight), src1.rows));
				cv::Mat mask;
				cv::cvtColor(src2Part, mask, cv::COLOR_BGR2GRAY);
				src2Part.copyTo(imageROI, mask);
				writer.write(res);
			}
		}
	}
}